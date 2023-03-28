package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.entities.*;
import dev.filipposcaramuzza.db2_telco.exceptions.OptionalProductNotFoundException;
import dev.filipposcaramuzza.db2_telco.exceptions.ServiceNotFoundException;
import dev.filipposcaramuzza.db2_telco.exceptions.ServicePackageNotFoundException;
import dev.filipposcaramuzza.db2_telco.exceptions.ValidityPeriodNotFoundException;
import dev.filipposcaramuzza.db2_telco.services.OptionalProductService;
import dev.filipposcaramuzza.db2_telco.services.ServicePackageService;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="ConfigureServlet", urlPatterns = {"/configure"})
public class ConfigureServlet extends HttpServlet {

    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/ServicePackageService")
    private ServicePackageService servicePackageService;
    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/OptionalProductService")
    private OptionalProductService optionalProductService;
    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/ValidityPeriodService")
    private ValidityPeriodService validityPeriodService;
    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/ServiceService")
    private ServiceService serviceService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Service> serviceList = serviceService.getAllServices();
        List<OptionalProduct> optionalProductList = optionalProductService.getAllOptionalProducts();
        List<ValidityPeriod> validityPeriodList = validityPeriodService.getAllValidityPeriods();
        List<String> serviceOptionList = serviceService.getAllServiceOptionNames();
        List<String> serviceListNames = serviceService.getAllServiceNames();

        if(req.getParameter("IDPackage") != null) { // here the user wants to modify the service package
            try {
                ServicePackage servicePackage = servicePackageService.getServicePackage(Integer.parseInt(req.getParameter("IDPackage")));
                req.setAttribute("servicePackageEdit", servicePackage);
            } catch (ServicePackageNotFoundException e) {
                e.printStackTrace();
                resp.sendRedirect(getServletContext().getContextPath() + "/packages");
            }
        }

        req.setAttribute("services", serviceList);
        req.setAttribute("optionalProducts", optionalProductList);
        req.setAttribute("validityPeriods", validityPeriodList);
        req.setAttribute("serviceOptionNames", serviceOptionList);
        req.setAttribute("serviceNames", serviceListNames);

        req.getRequestDispatcher("/configure.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] servicesID = req.getParameterValues("services[]"); // the id's of the selected services
        String[] optionalProductsID = req.getParameterValues("optionalProducts[]"); // the id's of the optional products selected
        String[] validityPeriodsID = req.getParameterValues("validityPeriods[]"); // the id's of the validity periods

        List<Service> services = new ArrayList<>();
        List<OptionalProduct> optionalProducts = new ArrayList<>();
        List<ValidityPeriod> validityPeriods = new ArrayList<>();

        // Get the list of the above-mentioned  services, optional products and validity periods from the string arrays
        for(String serviceID : servicesID) {
            try {
                services.add(serviceService.getServiceByID(Integer.parseInt(serviceID)));
            } catch (ServiceNotFoundException e) {
                e.printStackTrace();
            }
        }
        for(String optionalProductID : optionalProductsID) {
            try {
                optionalProducts.add(optionalProductService.getOptionalProduct(Integer.parseInt(optionalProductID)));
            } catch (OptionalProductNotFoundException e) {
                e.printStackTrace();
            }
        }
        for(String validityPeriodID : validityPeriodsID) {
            try {
                validityPeriods.add(validityPeriodService.getValidityPeriod(Integer.parseInt(validityPeriodID)));
            } catch (ValidityPeriodNotFoundException e) {
                e.printStackTrace();
            }
        }

        switch (Enum.valueOf(RequestType.class, req.getParameter("requestType"))) {
            case CREATE_SERVICE_PACKAGE:
                servicePackageService.createServicePackage(req.getParameter("servicePackageName"), services, optionalProducts, validityPeriods);
                break;
            case EDIT_SERVICE_PACKAGE:
                servicePackageService.editServicePackage(Integer.parseInt(req.getParameter("IDServicePackage")), req.getParameter("servicePackageName"), services, optionalProducts, validityPeriods);
                break;
            default:
                break;
        }



        resp.sendRedirect(getServletContext().getContextPath() + "/packages");
    }
}
