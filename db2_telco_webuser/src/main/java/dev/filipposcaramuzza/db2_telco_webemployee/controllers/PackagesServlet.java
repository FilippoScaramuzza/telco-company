package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.entities.ServicePackage;
import dev.filipposcaramuzza.db2_telco.services.ServicePackageService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name="PackagesServlet", urlPatterns = {"/packages"})
public class PackagesServlet extends HttpServlet {
    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/ServicePackageService")
    private ServicePackageService servicePackageService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ServicePackage> result = servicePackageService.getServicePackages();
        req.setAttribute("result", result);
        req.getRequestDispatcher("packages.jsp").forward(req, resp);
    }
}
