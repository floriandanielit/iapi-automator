package Servlets;

import iAPIEngine.Interpreter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by les on 15/04/14.
 */
public class CompilerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger= Logger.getLogger(getClass().getName());

        String text = (String) request.getParameter("program");
        String del =  (String) request.getParameter("delimiter");
        Interpreter interpreter;
        String result = "";
        logger.log(Level.SEVERE, "Starting");

        if(del.equals(""))
            interpreter = new Interpreter(text);
        else
            interpreter = new Interpreter(text,del);

        if(interpreter.is_creationFailed())
            System.out.println("Failed to compile program");
        else
        {
            try
            {
                result = interpreter.compile();
            }
            catch (Exception ex)
            {
                logger.log(Level.SEVERE, ex.getMessage());
            }
        }

        request.getSession().setAttribute("result",result);

        response.sendRedirect("test.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
