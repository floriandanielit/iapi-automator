package iAPIEngine;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import javax.xml.bind.ValidationException;
import java.net.MalformedURLException;
import java.util.List;

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
 *
 * open http://localhost:8080/web_war_exploded/formExample.jsp;
 * form 1;
 * fill text mirko username;
 * fill password morandi password;
 * submit btnSubmit;
 * result 1;
 *
 * add control for instructions order (open before everything)
 * rendere null il form quando cambio pagina
 */
public class Interpreter {

    private List<String> _commands;
    private WebClient _webClient;
    private HtmlForm  _actualForm;
    private HtmlPage  _webPage;
    private boolean   _creationFailed;

    public Interpreter(String program){
        _commands = null;
        try
        {
            _commands = Validator.validateSource(program);
            //set_creationFailed(false);
        }
        catch (ValidationException ex)
        {
            System.out.println("Invalid source, check syntax");
            set_creationFailed(true);
        }

    }
    public Interpreter(String program, String del){
        _commands = null;
        try
        {
            _commands = Validator.validateSource(program, del);
            set_creationFailed(false);
        }
        catch (ValidationException ex)
        {
            System.out.println("Invalid source, check syntax");
            set_creationFailed(true);
        }

    }
    public String compile() throws ValidationException{

        String executionResult = "";
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
                    break;
                case FILL:
                    final int fill_parameters = 4;
                    List<String> parameters = Validator.getParameters(_commands.get(i), fill_parameters);
                    assert parameters.size() == fill_parameters;
                    fill(parameters.get(0),parameters.get(1),parameters.get(2));
                    break;
                case SUBMIT:
                    submit(Validator.getSecondParameter(_commands.get(i)));
                    break;
                case RESULT:
                    executionResult = result(Validator.getSecondParameter(_commands.get(i)));
                    break;
                default:
                    System.out.println("Command not supported");

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
            System.out.println("Error while parsing url in open function");
        }
        catch (Exception ex)
        {
            System.out.println("Error in open:\n"+ex.getMessage());
        }
        return success;
    }

    public boolean form(String iapiID){

        if(_webPage == null)
            return false;
        boolean success = false;

        try
        {
            String query = String.format("//form[contains(@class,\"iapi\") and @id = %s]",iapiID);
            _actualForm = (HtmlForm) _webPage.getByXPath(query).get(0);
            success = true;

        }
        catch (Exception ex)
        {
            System.out.println(String.format("Error while trying to find form with ID %s/n %s",iapiID,ex.getMessage()));
        }
        return success;
    }

    public boolean fill(String inputType, String inputValue, String inputID){
        if(_actualForm == null)
            return false;

        boolean success = false;

        try
        {
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
            System.out.println(ex.getMessage());
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
            System.out.println(String.format("Unable to find sumbit element with ID = %s",inputID));
        }
        catch (Exception ex)
        {
            System.out.println("Exception while submittind:\n"+ex.getMessage());
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
            result = divResult.asXml();

        }
        catch (IndexOutOfBoundsException ex)
        {
            System.out.println(String.format("Unable to find sumbit element with ID = %s",iapiID));
        }
        catch (Exception ex)
        {
            System.out.println("Exception while submittind:\n"+ex.getMessage());
        }

        return result;
    }

    public boolean is_creationFailed() {
        return _creationFailed;
    }

    private void set_creationFailed(boolean _creationFailed) {
        this._creationFailed = _creationFailed;
    }
}

