package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.entities.ServicePackage;
import dev.filipposcaramuzza.db2_telco.exceptions.ServicePackageNotFoundException;
import dev.filipposcaramuzza.db2_telco.services.ServicePackageService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="BuyPackageServlet",
            urlPatterns = "/buy")
public class BuyPackageServlet extends HttpServlet {

    @EJB(name="dev.filipposcaramuzza.db2_telco.services/ServicePackageService")
    ServicePackageService servicePackageService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("packageID") == null) {
            resp.sendRedirect(getServletContext().getContextPath() + "/packages");
        }
        else {
            try {
                ServicePackage sp = servicePackageService.getServicePackage(Integer.parseInt(req.getParameter("packageID")));
                req.setAttribute("servicePackage", sp);
                req.getRequestDispatcher("/buy.jsp").forward(req, resp);
            } catch (ServicePackageNotFoundException e) {
                e.printStackTrace();
                resp.sendRedirect(getServletContext().getContextPath() + "/packages");
            }
        }
    }
}
