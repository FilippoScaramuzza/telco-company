package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.entities.ActivationSchedule;
import dev.filipposcaramuzza.db2_telco.entities.User;
import dev.filipposcaramuzza.db2_telco.services.ActivationScheduleService;
import dev.filipposcaramuzza.db2_telco.services.UserService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name="InsolventUsersServlet", urlPatterns = {"/insolvents"})
public class InsolventUsersServlet extends HttpServlet {

    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/UserService")
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> insolventUsers = userService.getInsolventUsers();
        req.setAttribute("insolventUsers", insolventUsers);
        req.getRequestDispatcher("/insolvents.jsp").forward(req, resp);
    }
}
