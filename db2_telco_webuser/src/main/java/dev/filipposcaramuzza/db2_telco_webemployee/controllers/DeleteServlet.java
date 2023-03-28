package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.entities.Order;
import dev.filipposcaramuzza.db2_telco.entities.User;
import dev.filipposcaramuzza.db2_telco.services.OrderService;
import dev.filipposcaramuzza.db2_telco.services.ServicePackageService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name="DeleteServlet", urlPatterns = "/delete")
public class DeleteServlet extends HttpServlet {

    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/OrderService")
    OrderService orderService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int IDOrder = Integer.parseInt(req.getParameter("IDOrder"));

        orderService.deleteOrder(IDOrder);
        ((User)req.getSession().getAttribute("user")).getOrders()
                .remove(((User)req.getSession().getAttribute("user")).getOrders()
                        .stream().filter(o -> o.getID() == IDOrder).collect(Collectors.toList()).get(0));

        resp.sendRedirect(getServletContext().getContextPath() + "/orders");

    }
}
