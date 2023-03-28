package dev.filipposcaramuzza.db2_telco.services;

import dev.filipposcaramuzza.db2_telco.entities.FailureAuditing;
import dev.filipposcaramuzza.db2_telco.entities.Order;
import dev.filipposcaramuzza.db2_telco.entities.User;
import dev.filipposcaramuzza.db2_telco.exceptions.UserAlreadyRegistered;
import dev.filipposcaramuzza.db2_telco.exceptions.UserNotFoundException;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "db2_telco")
    private EntityManager em;

    public User getUserFromCredentials(String username, String password) throws UserNotFoundException {
        User user;

        try {
            user = em.createNamedQuery("User.checkCredentials", User.class)
                    .setParameter(1, username)
                    .setParameter(2, password)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            throw new UserNotFoundException();
        }

        return user;
    }

    public void addUser(String username, String email, String password, String name, String surname, Boolean isAdmin, Boolean isInsolvent) throws UserAlreadyRegistered {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setAdmin(isAdmin);
        user.setInsolvent(isInsolvent);
        try {
            em.persist(user);
            em.flush();
        } catch (EntityExistsException e) {
            throw new UserAlreadyRegistered();
        }
    }

    public void checkForInsolvent(User user) {
        user.setInsolvent(user.getOrders().stream().anyMatch(order -> !order.isValid()));
        em.merge(user);
        em.flush();
    }

    public void addFailedPayment(User user) {
        user.setFailedPayments(user.getFailedPayments() + 1);
        if(user.getFailedPayments()==3) {
            FailureAuditing failureAuditing = new FailureAuditing();
            failureAuditing.setUser(user);
            failureAuditing.setLastFailureDate(Date.valueOf(LocalDate.now()));
            failureAuditing.setLastFailureTime(Time.valueOf(LocalTime.now()));
            em.persist(failureAuditing);
            user.setFailureAuditing(failureAuditing);
        }
        else if(user.getFailedPayments() > 3) {
            user.getFailureAuditing().setLastFailureDate(Date.valueOf(LocalDate.now()));
            user.getFailureAuditing().setLastFailureTime(Time.valueOf(LocalTime.now()));
            em.merge(user);
        }
    }

    public List<User> getInsolventUsers() {
        return (List<User>) em.createNamedQuery("User.selectInsolventUsers").getResultList();
    }
}
