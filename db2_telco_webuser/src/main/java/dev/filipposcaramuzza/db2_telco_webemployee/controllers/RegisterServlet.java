package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.exceptions.UserAlreadyRegistered;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(name="RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/UserService")
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            userService.addUser(req.getParameter("username"),
                    req.getParameter("email"),
                    PasswordManager.getSHAPassword(req.getParameter("password")),
                    req.getParameter("name"),
                    req.getParameter("surname"),
                    false,
                    false);
            req.setAttribute("requestType", RequestType.USER_SUCCESSFULLY_REGISTERED);
        } catch (UserAlreadyRegistered e) {
            e.printStackTrace();
            req.setAttribute("requestType", RequestType.USER_ALREADY_REGISTERED);
        }

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
