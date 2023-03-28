package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.services.OptionalProductService;
import dev.filipposcaramuzza.db2_telco.services.ServiceService;
import dev.filipposcaramuzza.db2_telco.services.ValidityPeriodService;
import dev.filipposcaramuzza.db2_telco_webemployee.utilities.RequestType;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name="CreateServlet", urlPatterns = {"/create"})
public class CreateServlet extends HttpServlet {

    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/OptionalProductService")
    private OptionalProductService optionalProductService;
    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/ValidityPeriodService")
    private ValidityPeriodService validityPeriodService;
    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/ServiceService")
    private ServiceService serviceService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        switch (Enum.valueOf(RequestType.class, req.getParameter("requestType"))) {
            case CREATE_OPTIONAL_PRODUCT:
                optionalProductService.createOptionalProduct(req.getParameter("name"), new BigDecimal(req.getParameter("monthlyFee")));
                break;
            case CREATE_VALIDITY_PERIOD:
                validityPeriodService.createValidityPeriod(Integer.parseInt(req.getParameter("monthsNum")), new BigDecimal(req.getParameter("monthlyFee")));
                break;
            case CREATE_SERVICE:
                String serviceName = req.getParameter("serviceName");
                String[] serviceOptionNames = req.getParameterValues("names[]");
                String[] serviceOptionNums = req.getParameterValues("optionNums[]");
                String[] serviceOptionExtraFees = req.getParameterValues("optionFees[]");

                serviceService.createService(serviceName, serviceOptionNames, serviceOptionNums, serviceOptionExtraFees);

                break;
            default:
                break;
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/configure");

    }
}
