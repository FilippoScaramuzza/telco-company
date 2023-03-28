package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.entities.User;
import dev.filipposcaramuzza.db2_telco.exceptions.UserNotFoundException;
import dev.filipposcaramuzza.db2_telco.services.UserService;
import dev.filipposcaramuzza.db2_telco_webemployee.utilities.PasswordManager;
import dev.filipposcaramuzza.db2_telco_webemployee.utilities.RequestType;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "LoginServlet",
        urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/UserService")
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = null;

        try {
            user = userService.getUserFromCredentials(req.getParameter("username"), PasswordManager.getSHAPassword(req.getParameter("password")));
            req.setAttribute("user", user);
            req.getSession().setAttribute("user", user);

            if(req.getParameter("loginNeeded") != null) {
                System.out.println(req.getParameter("loginNeeded"));
                req.setAttribute("requestType", RequestType.SUCCESSFUL_LOGIN_TO_BUY);
            }
            else {
                req.setAttribute("requestType", RequestType.SUCCESSFUL_LOGIN);
            }
        }
        catch (UserNotFoundException e) {
            e.printStackTrace();
            req.setAttribute("requestType", RequestType.USER_NOT_FOUND);
        }

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
