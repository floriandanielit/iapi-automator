package iAPIEngine;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by les on 04/04/14.
 * regex for open       (open)[ ](ht|f)tp(s?)://\\S+       (open)[ ](ht|f)tp(s?)://\S+
 * regex for form       (form)[ ]\\S+                      (form)[ ]\S+
 * regex for fill       (fill)[ ](\\S+)[ ](\\S+)[ ](\\S+)  (fill)[ ](\S+)[ ](\S+)[ ](\S+)
 * regex for submit     (submit)[ ]\S+                     (submit)[ ]\\S+

 */
public class Validator {

    /**
     *
     * @param source The whole program sequence
     * @return A list of Strings containings all the commands
     * @throws ValidationException If there is a command that does not match one of the language regular expressions
     */

    public static List<String> validateSource(String source) throws ValidationException
    {
        return validateSource(source, ";");
    }
    public static List<String> validateSource(String source, String del) throws ValidationException
    {
        List<String> results = null;
        String[] splittedElements = source.split(del);
        results = new ArrayList<String>();

        for(String element:splittedElements)
        {
            if(isValidCommand(element))
                results.add(element);
            else
                throw new ValidationException(String.format("{0} is not a valid command!",element));
        }
        return results;

    }
    public static boolean isValidCommand(String command)
    {
        Pattern pattern = Pattern.compile("(((form|submit|result)[ ]\\S+)|((open)[ ](ht|f)tp(s?)://\\S+)|((fill)[ ](\\S+)[ ](\\S+)[ ](\\S+))|resultJSON)");
        boolean matched = false;

        try {
            Matcher matcher = pattern.matcher(command);
            matched = matcher.matches();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return matched;
    }
    public static String retrieveCommand(String code)
    {
        String[] results = code.split(" ");

        if(results.length > 0)
            return results[0];
        else
            return "";
    }
    public static String getSecondParameter(String code)
    {
        String[] results = code.split(" ");

        if(results.length >= 2)
            return results[1];
        else
            return "";
    }
    public static List<String> getParameters(String code, int numberOfParameters)
    {
        List<String> commands = new ArrayList<String>(Arrays.asList(code.split(" ")));

        assert numberOfParameters <= commands.size();

        if(commands.size() >= 2)
            return commands.subList(1, numberOfParameters);
        else
            return null;
    }
}
