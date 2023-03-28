package dev.filipposcaramuzza.db2_telco.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;

/**
 * This class represent a Record in the ActivationSchedule table. When a user SUCCESSFULLY
 * buys a Service Package, a record on the ActivationSchedule table is added.
 */
@Entity
@Table(name="ActivationSchedule", schema="db2_telco")
@NamedQuery(name="ActivationSchedule.selectAll", query = "SELECT activationSchedule FROM ActivationSchedule activationSchedule")
public class ActivationSchedule implements Serializable {
    /**
     * Artificial PK. The Entity ID on the ActivationSchedule table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    /**
     * The order activation date. It can be retrieved also from the `order` attribute.
     */
    private Date activationDate;
    /**
     * The order deactivation date. It's used in order to know when to disable the
     * services and optional products chosen by the user.
     */
    private Date deactivationDate;
    /**
     * The user who did the order.
     */
    @ManyToOne
    @JoinColumn(name = "username")
    private User user;
    /**
     * The Order, containing the services and the optional products to be activated
     * (and deactivated).
     */
    @OneToOne
    @JoinColumn(name = "IDOrder")
    private Order order;

    /**
     * Void constructor.
     */
    public ActivationSchedule() {
    }

    /**
     * Construct
     * @param ID The ID of the Activation Schedule
     * @param activationDate // The order activation date
     * @param deactivationDate // The order deactivation date
     * @param user // The user who did the order
     * @param order // The order to be activated
     */
    public ActivationSchedule(int ID, Date activationDate, Date deactivationDate, User user, Order order) {
        this.ID = ID;
        this.activationDate = activationDate;
        this.deactivationDate = deactivationDate;
        this.user = user;
        this.order = order;
    }

    /**
     * Get the ID
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Set the ID
     * @param ID // The ID of the Service Package
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Get the Activation Date
     * @return The activation date
     */
    public Date getActivationDate() {
        return activationDate;
    }

    /**
     * Set the activation date
     * @param activationDate The Activation Date
     */
    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    /**
     * Get the Deactivation Date
     * @return The Deactivation Date
     */
    public Date getDeactivationDate() {
        return deactivationDate;
    }

    /**
     * Set the Deactivation Date
     * @param deactivationDate The Deactivation Date
     */
    public void setDeactivationDate(Date deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    /**
     * Get the User
     * @return the User
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the User
     * @param user The User.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the Order
     * @return The Order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Set the Order
     * @param order The Order
     */
    public void setOrder(Order order) {
        this.order = order;
    }
}
