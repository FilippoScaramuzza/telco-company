package dev.filipposcaramuzza.db2_telco.services;

import dev.filipposcaramuzza.db2_telco.entities.ActivationSchedule;
import dev.filipposcaramuzza.db2_telco.entities.Order;
import dev.filipposcaramuzza.db2_telco.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class ActivationScheduleService {
    @PersistenceContext(unitName = "db2_telco")
    private EntityManager em;

    public List<ActivationSchedule> getActivationSchedules() {
        return (List<ActivationSchedule>)em.createNamedQuery("ActivationSchedule.selectAll").getResultList();
    }

    public ActivationSchedule createActivationSchedule(Order order) {

        LocalDate endingDate = order.getStartDate().toLocalDate();
        endingDate = endingDate.plusMonths(order.getValidityPeriod().getMonthsNum());

        ActivationSchedule activationSchedule = new ActivationSchedule();
        activationSchedule.setOrder(order);
        activationSchedule.setUser(order.getUser());
        activationSchedule.setActivationDate(order.getStartDate());
        activationSchedule.setDeactivationDate(Date.valueOf(endingDate));

        em.persist(activationSchedule);

        return activationSchedule;
    }
}
