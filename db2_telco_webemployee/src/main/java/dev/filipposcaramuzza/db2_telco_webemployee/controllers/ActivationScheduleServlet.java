package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.entities.ActivationSchedule;
import dev.filipposcaramuzza.db2_telco.services.ActivationScheduleService;
import dev.filipposcaramuzza.db2_telco.services.OptionalProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name="ActivationScheduleServlet", urlPatterns = {"/activationschedule"})
public class ActivationScheduleServlet extends HttpServlet {

    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/ActivationScheduleService")
    private ActivationScheduleService activationScheduleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ActivationSchedule> activationSchedules = activationScheduleService.getActivationSchedules();
        req.setAttribute("activationSchedules", activationSchedules);
        req.getRequestDispatcher("/activationSchedule.jsp").forward(req, resp);
    }
}
