package dev.filipposcaramuzza.db2_telco_webemployee.controllers;

import dev.filipposcaramuzza.db2_telco.entities.Order;
import dev.filipposcaramuzza.db2_telco.entities.User;
import dev.filipposcaramuzza.db2_telco.services.OrderService;
import dev.filipposcaramuzza.db2_telco.services.UserService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name="OrdersServlet", urlPatterns = "/orders")
public class OrdersServlet extends HttpServlet {
    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/OrderService")
    private OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = orderService.getOrdersByUser(((User)req.getSession().getAttribute("user")).getUsername());

        req.setAttribute("orders", orders);

        req.getRequestDispatcher("/orders.jsp").forward(req, resp);
    }
}
