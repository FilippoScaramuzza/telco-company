package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.entities.FailureAuditing;
import dev.filipposcaramuzza.db2_telco.services.FailureAuditingService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name="FailureAuditingServlet", urlPatterns = "/failureauditing")
public class FailureAuditingServlet extends HttpServlet {
    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/FailureAuditingService")
    private FailureAuditingService failureAuditingService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FailureAuditing> failuresAuditing = failureAuditingService.getFailuresAuditing();

        req.setAttribute("failuresAuditing", failuresAuditing);
        req.getRequestDispatcher("/failureAuditing.jsp").forward(req, resp);
    }
}
