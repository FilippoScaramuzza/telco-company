package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.entities.ServicePackageStatistics;
import dev.filipposcaramuzza.db2_telco.entities.ServicePackageStatistics_VP;
import dev.filipposcaramuzza.db2_telco.services.ServicePackageService;
import dev.filipposcaramuzza.db2_telco.services.StatisticsService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "StatisticsServlet", urlPatterns = {"/sales"})
public class StatisticsServlet extends HttpServlet {
    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/StatisticsService")
    private StatisticsService statisticsService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ServicePackageStatistics> servicePackageStatistics = statisticsService.getServicePackgeStatistics();
        List<ServicePackageStatistics_VP> servicePackageStatistics_vps = statisticsService.getServicePackgeStatistic_VPs();
        List<HashMap<String, Integer>> bestSellerOptionalProduct = statisticsService.getBestSellerOptionalProduct();

        req.setAttribute("servicePackageStatistics", servicePackageStatistics);
        req.setAttribute("servicePackageStatistics_vps", servicePackageStatistics_vps);
        req.setAttribute("bestSellerOptionalProduct", bestSellerOptionalProduct);
        req.getRequestDispatcher("/sales.jsp").forward(req, resp);
    }
}
