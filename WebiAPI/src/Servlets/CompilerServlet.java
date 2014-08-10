package Servlets;

import iAPIEngine.Interpreter;
import iAPIEngine.Result;
import iAPIEngine.ResultJSON;
import iAPIEngine.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;


/**
 * Created by les on 15/04/14.
 */
public class CompilerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger= Logger.getLogger(getClass().getName());

        String text = (String) request.getParameter("program");
        System.out.println(text);
        //String del =  (String) request.getParameter("delimiter");
        Interpreter interpreter;
        String result = "";
        interpreter = new Interpreter(text);
        logger.log(Level.SEVERE, "post working");
        if(interpreter.is_creationFailed())
            System.out.println("Failed to compile program");
        else
        {
            try
            {
                interpreter.setLogger(logger);
                result = interpreter.compile();
            }
            catch (Exception ex)
            {
                logger.log(Level.SEVERE, ex.getMessage());
            }
        }
        Gson gson = new Gson();
        ResultJSON json = gson.fromJson(result, ResultJSON.class);
        request.getSession().setAttribute("results",json.get_results());

        response.sendRedirect("risultati.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger= Logger.getLogger(getClass().getName());
        logger.log(Level.SEVERE, "get passed");
        doPost(request, response);
    }
}
