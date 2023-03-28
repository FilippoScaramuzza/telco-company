package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.entities.*;
import dev.filipposcaramuzza.db2_telco.exceptions.ServicePackageNotFoundException;
import dev.filipposcaramuzza.db2_telco.services.ActivationScheduleService;
import dev.filipposcaramuzza.db2_telco.services.OrderService;
import dev.filipposcaramuzza.db2_telco.services.ServicePackageService;
import dev.filipposcaramuzza.db2_telco_webemployee.utilities.RequestType;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(
        name = "ConfirmServlet",
        urlPatterns = {"/confirm"}
)
public class ConfirmServlet extends HttpServlet {
    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/ServicePackageService")
    ServicePackageService servicePackageService;

    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/OrderService")
    OrderService orderService;

    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/ActivationScheduleService")
    ActivationScheduleService activationScheduleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/confirm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        switch (Enum.valueOf(RequestType.class, req.getParameter("requestType"))) {
            case PENDING_POST:
                try {
                    req.getSession().setAttribute("pendingServicePackage", servicePackageService.getServicePackage(Integer.parseInt(req.getParameter("servicePackageID"))));
                    req.getSession().setAttribute("pendingValidityPeriodID", req.getParameter("validityPeriodID"));
                    req.getSession().setAttribute("pendingStartingDate", req.getParameter("startingDate"));
                    List<Integer> optionalProductsIDs = new ArrayList<>();
                    String[] opIDs = req.getParameterValues("optionalProducts[]");
                    if (opIDs != null) {
                        for (String op : opIDs) {
                            optionalProductsIDs.add(Integer.parseInt(op));
                        }
                        req.getSession().setAttribute("pendingOptionalProductsIDs", optionalProductsIDs);
                    } else {
                        req.getSession().setAttribute("pendingOptionalProductsIDs", null);
                    }
                    req.getRequestDispatcher("/confirm.jsp").forward(req, resp);
                } catch (ServicePackageNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case PENDING_RETRY_POST:
                Order pendingOrder = ((User)req.getSession().getAttribute("user")).getOrders()
                        .stream()
                        .filter(o -> o.getID() == Integer.parseInt(req.getParameter("IDOrder")))
                        .collect(Collectors.toList())
                        .get(0);

                req.getSession().setAttribute("pendingServicePackage", pendingOrder.getServicePackage());
                req.getSession().setAttribute("pendingValidityPeriodID", String.valueOf(pendingOrder.getValidityPeriod().getID()));
                req.getSession().setAttribute("pendingStartingDate", pendingOrder.getStartDate().toString());
                List<Integer> optionalProductsIDs = new ArrayList<>();
                if (pendingOrder.getOptionalProducts().size() > 0) {
                    for (OptionalProduct op : pendingOrder.getOptionalProducts()) {
                        optionalProductsIDs.add(op.getID());
                    }
                    req.getSession().setAttribute("pendingOptionalProductsIDs", optionalProductsIDs);
                } else {
                    req.getSession().setAttribute("pendingOptionalProductsIDs", null);
                }

                req.setAttribute("requestType", RequestType.PENDING_RETRY_POST);
                req.setAttribute("IDOrder", req.getParameter("IDOrder"));
                req.getRequestDispatcher("/confirm.jsp").forward(req, resp);
                break;
            case CONFIRM_POST:
                HttpSession session = req.getSession();
                if (session == null) {
                    req.setAttribute("requestType", RequestType.SESSION_TIMEOUT);
                    req.getRequestDispatcher("/confirm.jsp").forward(req, resp);
                } else {
                    /* Retrieving data from session */
                    ValidityPeriod pendingValidityPeriod = ((ServicePackage) session.getAttribute("pendingServicePackage"))
                            .getValidityPeriods()
                            .stream()
                            .filter(vp -> vp.getID() == Integer.parseInt((String) session.getAttribute("pendingValidityPeriodID")))
                            .collect(Collectors.toList()).get(0);

                    List<Integer> pendingOptionalProductsIDs = (ArrayList<Integer>) session.getAttribute("pendingOptionalProductsIDs");
                    List<OptionalProduct> pendingOptionalProducts = new ArrayList<>();
                    if (pendingOptionalProductsIDs != null) {
                        pendingOptionalProducts = ((ServicePackage) session.getAttribute("pendingServicePackage")).getOptionalProducts()
                                .stream().filter(op -> pendingOptionalProductsIDs.contains(op.getID())).collect(Collectors.toList());
                    }

                    BigDecimal servicePackageTotal = pendingValidityPeriod.getMonthlyFee().multiply(BigDecimal.valueOf(pendingValidityPeriod.getMonthsNum()));

                    BigDecimal optionalServiceFeesSum = BigDecimal.ZERO;
                    for (OptionalProduct optionalProduct : pendingOptionalProducts) {
                        optionalServiceFeesSum = optionalServiceFeesSum.add(optionalProduct.getMonthlyFee().multiply(BigDecimal.valueOf(pendingValidityPeriod.getMonthsNum())));
                    }

                    servicePackageTotal = servicePackageTotal.add(optionalServiceFeesSum);


                    Order order = orderService.createOrder(Date.valueOf(LocalDate.now()),
                            Time.valueOf(LocalTime.now()),
                            Date.valueOf((String) session.getAttribute("pendingStartingDate")),
                            servicePackageTotal,
                            payment(req),
                            (User) session.getAttribute("user"),
                            (ServicePackage) session.getAttribute("pendingServicePackage"),
                            pendingValidityPeriod,
                            pendingOptionalProducts);

                    if (order.isValid()) {
                        activationScheduleService.createActivationSchedule(order);
                    }

                    req.getSession().setAttribute("pendingServicePackage", null);
                    req.getSession().setAttribute("pendingValidityPeriodID", null);
                    req.getSession().setAttribute("pendingStartingDate", null);
                    req.getSession().setAttribute("pendingOptionalProductsIDs", null);

                    resp.sendRedirect(getServletContext().getContextPath() + "/orders");
                    break;
                }
            case RETRY_POST:
                Order order = orderService.updateOrderValidity((User) req.getSession().getAttribute("user"),
                        Integer.parseInt(req.getParameter("IDOrder")),
                        payment(req));

                if(payment(req)) {
                    activationScheduleService.createActivationSchedule(order);
                }

                req.getSession().setAttribute("pendingServicePackage", null);
                req.getSession().setAttribute("pendingValidityPeriodID", null);
                req.getSession().setAttribute("pendingStartingDate", null);
                req.getSession().setAttribute("pendingOptionalProductsIDs", null);

                resp.sendRedirect(getServletContext().getContextPath() + "/orders");
                break;
            default:
                break;
        }
    }

    private boolean payment(HttpServletRequest req) {
        return req.getParameter("paymentFailure") == null;
    }
}
