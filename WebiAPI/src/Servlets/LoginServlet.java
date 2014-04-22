package Servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by les on 14/04/14.
 */
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username =(String) request.getParameter("username");
        String password =(String) request.getParameter("password");

        if(username.equals("mirko") && password.equals("morandi"))
            request.getSession().setAttribute("result","success");
        else
            request.getSession().setAttribute("result","fail");
        request.getSession().setAttribute("id","1");
        response.sendRedirect("result.jsp");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
