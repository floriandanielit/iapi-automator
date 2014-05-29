package iAPIEngine;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import javax.xml.bind.ValidationException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.google.gson.Gson;
/**
 * Created by les on 04/04/14.
 *
 * LANGUAGE DEFINITION
 *
 * Open url (throws exception if not valid)
 * Form iapiid (throws exception if not exists)
 * Fill inputType inputValue inputIdentifier
 * Submit inputID
 * Result iapiid
 * ResultJSON iapiid
 *
 open http://localhost:8080/web_war_exploded/formExample.jsp;form 1;fill inputType:Text mirko username;
 fill inputType:Password morandi password;submit btnSubmit;result 1;form 3;fill inputType:Text ciao name;fill inputType:Text comohestas residence;submit btn;result 1;resultJSON;
 *
 * add control for instructions order (open before everything)
 * rendere null il form quando cambio pagina
 *
 *
 */
public class Interpreter {

    private Logger _logger;
    private Gson _resultJson;
    private ResultJSON _json;
    private List<String> _commands;
    private WebClient _webClient;
    private HtmlForm  _actualForm;
    private HtmlPage  _webPage;
    private boolean   _creationFailed;
    private List<String> _supportedInputs;

    public Interpreter(String program){
        _commands = null;
        _logger = Logger.getLogger(Interpreter.class.getName());
        try
        {
            _commands = Validator.validateSource(program);
            set_creationFailed(false);
            _json = new ResultJSON();
            _resultJson = new Gson();
            _supportedInputs = new ArrayList<String>(Arrays.asList("text:","password:","reset:","image:","file:","checkbox:","radiobutton:","button:","image:","hidden:","submit"));
            _logger.log(Level.INFO,"creation successful");

        }
        catch (ValidationException ex)
        {
            _logger.log(Level.SEVERE, "Invalid source, check syntax");
            set_creationFailed(true);
        }

    }
    public Interpreter(String program, String del){
        _commands = null;
        try
        {
            _commands = Validator.validateSource(program, del);
            set_creationFailed(false);
            _json = new ResultJSON();
            _resultJson = new Gson();
            _supportedInputs = new ArrayList<String>(Arrays.asList("text:","password:","reset:","image:","file:","checkbox:","radiobutton:","button:","image:","hidden:","submit"));

        }
        catch (ValidationException ex)
        {
            _logger.log(Level.SEVERE, "Invalid source, check syntax");

            set_creationFailed(true);
        }

    }
    public String compile() throws ValidationException{

        String executionResult = "";
        Result toJson = new Result();

        for (int i = 0; i < _commands.size();i++)
        {
            String toDo = Validator.retrieveCommand(_commands.get(i));

            if("".equals(toDo))
                throw new ValidationException(String.format("Invalid Command {0}",toDo));
            CommandType type = CommandType.valueOf(toDo.toUpperCase());


            switch (type)
            {
                case OPEN:
                    open(Validator.getSecondParameter(_commands.get(i)));
                    break;
                case FORM:
                    form(Validator.getSecondParameter(_commands.get(i)));
                    toJson.setPage(_webPage.getUrl().toString());
                    break;
                case FILL:
                    final int fill_parameters = 3;
                    List<String> parameters = Validator.getParameters(_commands.get(i), fill_parameters);

                    if(Validator.hasRetrieveParameter(parameters.get(0))){
                        String retrievedParamer = retrieveParameterFromResult(parameters.get(0));
                        parameters.set(0, retrievedParamer != null?retrievedParamer:parameters.get(0));
                    }

                    fill(parameters.get(0), parameters.get(1));

                    break;
                case SUBMIT:
                    submit(Validator.getSecondParameter(_commands.get(i)));
                    break;
                case RESULT:
                    String id = Validator.getSecondParameter(_commands.get(i));
                    executionResult = result(id);
                    toJson.setiAPIid(id);
                    toJson.setResult(executionResult);
                    toJson.setLabel(getResultLabel(id));
                    _json.get_results().add(toJson);
                    toJson = new Result();
                    break;
                case RESULTJSON:
                    executionResult = _resultJson.toJson(_json);
                    break;
                default:
                    _logger.log(Level.SEVERE, "Command not supported");

            }
        }

        return executionResult;

    }

    public boolean open(String url){

        boolean success = false;

        try
        {
            _webClient = new WebClient(BrowserVersion.CHROME);
            _webClient.getOptions().setJavaScriptEnabled(false); // Only for testing purposes, must be fixed in future.
            _webPage = _webClient.getPage(url);
            success  = true;
        }
        catch (MalformedURLException ex)
        {
            _logger.log(Level.SEVERE, "Error while parsing url in open function");
        }
        catch (Exception ex)
        {
            _logger.log(Level.SEVERE, "Error in open:\n" + ex.getMessage());
        }
        return success;
    }

    public boolean form(String iapiID){

        if(_webPage == null)
            return false;
        boolean success = false;

        try
        {
            String query = String.format("//form[contains(@class,\"h-iapi\") and @id = %s]",iapiID);
            _actualForm = (HtmlForm) _webPage.getByXPath(query).get(0);
            success = true;

        }
        catch (Exception ex)
        {
            _logger.log(Level.SEVERE,String.format("Error while trying to find form with ID %s/n %s",iapiID,ex.getMessage()));
        }
        return success;
    }
    public boolean fill(String inputValue, String inputID){

        String var = getInputType(inputID);
        return fill(var, inputValue, inputID);

    }
    public boolean fill(String inputTypeIAPI, String inputValue, String inputID){
        if(_actualForm == null)
            return false;

        boolean success = false;

        try
        {
            String inputType = getInputTypeFromNotation(inputTypeIAPI);
            InputType type = InputType.valueOf(inputType.toUpperCase());

            switch (type)
            {
                case HIDDEN:
                    HtmlHiddenInput inputHidden = (HtmlHiddenInput)_actualForm.getByXPath(String.format(".//input[@type = \"%s\" and @id = \"%s\"]",inputType.toLowerCase().toString(),inputID)).get(0);
                    inputHidden.setValueAttribute(inputValue);
                    success = true;
                    break;
                case IMAGE:
                    HtmlImageInput inputImage = (HtmlImageInput)_actualForm.getByXPath(String.format(".//input[@type = \"%s\" and @id = \"%s\"]",inputType.toLowerCase().toString(),inputID)).get(0);
                    inputImage.setValueAttribute(inputValue);
                    success = true;
                    break;
                case FILE:
                    HtmlFileInput inputFile = (HtmlFileInput)_actualForm.getByXPath(String.format(".//input[@type = \"%s\" and @id = \"%s\"]",inputType.toLowerCase().toString(),inputID)).get(0);
                    inputFile.setValueAttribute(inputValue);
                    success = true;
                    break;
                case TEXT:
                    HtmlTextInput inputText = (HtmlTextInput)_actualForm.getByXPath(String.format(".//input[@type = \"%s\" and @id = \"%s\"]",inputType.toLowerCase().toString(),inputID)).get(0);
                    inputText.setValueAttribute(inputValue);
                    success = true;
                    break;
                case PASSWORD:
                    HtmlPasswordInput inputPassword = (HtmlPasswordInput)_actualForm.getByXPath(String.format(".//input[@type = \"%s\" and @id = \"%s\"]",inputType.toLowerCase().toString(),inputID)).get(0);
                    inputPassword.setValueAttribute(inputValue);
                    success = true;
                    break;
                case RADIOBUTTON:
                    HtmlRadioButtonInput inputRadiobutton = (HtmlRadioButtonInput)_actualForm.getByXPath(String.format(".//input[@type = \"%s\" and @id = \"%s\"]",inputType.toLowerCase().toString(),inputID)).get(0);
                    inputRadiobutton.setValueAttribute(inputValue);
                    success = true;
                    break;
                case RESET:
                    HtmlResetInput inputReset = (HtmlResetInput)_actualForm.getByXPath(String.format(".//input[@type = \"%s\" and @id = \"%s\"]",inputType.toLowerCase().toString(),inputID)).get(0);
                    inputReset.setValueAttribute(inputValue);
                    success = true;
                    break;
                case BUTTON:
                    HtmlButtonInput inputButton = (HtmlButtonInput)_actualForm.getByXPath(String.format(".//input[@type = \"%s\" and @id = \"%s\"]",inputType.toLowerCase().toString(),inputID)).get(0);
                    inputButton.setValueAttribute(inputValue);
                    success = true;
                    break;
                case CHECKBOX:
                    HtmlCheckBoxInput inputCheckbox = (HtmlCheckBoxInput)_actualForm.getByXPath(String.format(".//input[@type = \"%s\" and @id = \"%s\"]",inputType.toLowerCase().toString(),inputID)).get(0);
                    inputCheckbox.setValueAttribute(inputValue);
                    success = true;
                    break;


            }
        }
        catch (Exception ex)
        {
            _logger.log(Level.SEVERE, "Error in fill:\n" + ex.getMessage());
        }
        return success;

    }
    public boolean submit(String inputID){

        if(_actualForm == null || _webPage == null)
            return false;

        boolean success = false;

        try
        {
            HtmlSubmitInput submitInput = (HtmlSubmitInput) _actualForm.getByXPath(String.format(".//input[@type = \"submit\" and @id = \"%s\"]",inputID)).get(0);
            HtmlPage newPage = submitInput.click();

            if(newPage != null)
            {
                success = true;
                _webPage = newPage;
            }

        }
        catch (IndexOutOfBoundsException ex)
        {
            _logger.log(Level.SEVERE,String.format("Unable to find sumbit element with ID = %s",inputID));

        }
        catch (Exception ex)
        {
            _logger.log(Level.SEVERE, "Error in submit:\n" + ex.getMessage());

        }

        return success;
    }

    public String result(String iapiID)
    {
        if( _webPage == null)
            return null;

        String result = null;

        try
        {
            HtmlDivision divResult = (HtmlDivision) _webPage.getByXPath(String.format(".//div[@id = \"%s\"]",iapiID)).get(0);
            result = divResult.asText();

        }
        catch (IndexOutOfBoundsException ex)
        {
            _logger.log(Level.SEVERE,String.format("Unable to find element with ID = %s",iapiID));

        }
        catch (Exception ex)
        {
            _logger.log(Level.SEVERE,"Exception while searching result:\n"+ex.getMessage());
        }

        return result;
    }
    private String getInputTypeFromNotation(String iapiNotatedType)
    {
        String[] results = iapiNotatedType.split(" ");
        String notation = null;

        for(String element: results){
            if(_supportedInputs.contains(element))
                notation = element;
        }
        String[] parts = iapiNotatedType.split(":");

        assert parts.length > 1;

        return parts[0].replace("i-","");

    }

    private String getInputType(String iapiID ){

        DomAttr result = (DomAttr) _actualForm.getByXPath(String.format(".//input[@id = \"%s\"]/@class",iapiID)).get(0);
        return  result.getNodeValue();

    }
    private String getResultLabel(String iapiID){
        DomAttr result = (DomAttr) _webPage.getByXPath(String.format(".//div[@id = \"%s\"]/@class",iapiID)).get(0);

        String[] unparsedLabels = result.getNodeValue().split(" ");
        String label = null;

        for(String htmlClass : unparsedLabels){
            if(htmlClass.contains("i-result:"))
                label = htmlClass;
        }

        return label.split(":")[1];

    }
    private String retrieveParameterFromResult(String retrieveCommand){
        String[] parts = retrieveCommand.split(" ");

        assert parts.length > 1;

        String label =  parts[1].replace(")","");

        for(Result res:_json.get_results())
        {
            if(res.getLabel().equals(label))
                return res.getResult();
        }

        _logger.log(Level.SEVERE, String.format("Json result for label %s was not found!",label));
        return null;
    }

    public void setLogger(Logger logger){
        _logger = logger;
    }
    public boolean is_creationFailed() {
        return _creationFailed;
    }

    private void set_creationFailed(boolean _creationFailed) {
        this._creationFailed = _creationFailed;
    }
}


