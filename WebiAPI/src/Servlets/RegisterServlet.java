package Servlets;

/**
 * Created by les on 14/04/14.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name =(String) request.getParameter("realName");
        String home =(String) request.getParameter("home");
        String state =(String) request.getParameter("single");


        request.getSession().setAttribute("id","1");
        request.getSession().setAttribute("result",String.format("%s %s %s",name,home,state));
        response.sendRedirect("printLastResult.jsp");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
