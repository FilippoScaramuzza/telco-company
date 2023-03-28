package dev.filipposcaramuzza.db2_telco.services;

import dev.filipposcaramuzza.db2_telco.entities.*;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OrderService {
    @EJB(name = "dev.filipposcaramuzza.db2_telco.services/UserService")
    private UserService userService;

    @PersistenceContext(unitName = "db2_telco")
    private EntityManager em;

    public Order createOrder(Date creationDate,
                             Time creationHour,
                             Date startDate,
                             BigDecimal totalValue,
                             boolean isValid,
                             User user,
                             ServicePackage servicePackage,
                             ValidityPeriod validityPeriod,
                             List<OptionalProduct> optionalProducts) {
        Order order = new Order();
        order.setCreationDate(creationDate);
        order.setCreationHour(creationHour);
        order.setStartDate(startDate);
        order.setTotalValue(totalValue);
        order.setValid(isValid);
        order.setUser(user);
        order.setServicePackage(servicePackage);
        order.setValidityPeriod(validityPeriod);
        order.setOptionalProducts(optionalProducts);

        em.persist(order);
        em.flush();

        user.getOrders().add(order);
        if(!order.isValid()) {
            userService.addFailedPayment(order.getUser());
        }
        userService.checkForInsolvent(user);

        return order;
    }

    public Order updateOrderValidity(User user, int IDOrder, boolean isValid) {
        Order order = user.getOrders().stream().filter(o -> o.getID() == IDOrder).collect(Collectors.toList()).get(0);
        order.setValid(isValid);

        em.merge(order);

        if(!order.isValid()) {
            userService.addFailedPayment(order.getUser());
        }

        userService.checkForInsolvent(user);

        return order;
    }

    public void deleteOrder(int IDOrder) {
        Order order = em.find(Order.class, IDOrder);
        em.remove(order);
        em.flush();
    }

    public List<Order> getOrdersByUser(String username) {
        return (List<Order>) em.createNamedQuery("Order.selectOrdersByUsername").setParameter(1, username).getResultList();
    }
}
