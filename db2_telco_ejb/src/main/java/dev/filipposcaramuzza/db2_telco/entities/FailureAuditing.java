package dev.filipposcaramuzza.db2_telco.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * This class represent a record on the FailureAuditing table. A record on this table,
 * is associated with a user that have failed at least 3 payments. After the row insertion,
 * the amount of failed payments get updated at each failed payment.
 */
@Entity
@Table(name="FailureAuditing", schema="db2_telco")
@NamedQuery(name="FailureAuditing.selectAll", query = "SELECT failureAuditing FROM FailureAuditing failureAuditing")
public class FailureAuditing implements Serializable {
    /**
     * Artificial PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    /**
     * Last Payment Failure Date
     */
    private Date lastFailureDate;
    /**
     * Last Payment Failure Time
     */
    private Time lastFailureTime;
    /**
     * The user who failed the payment on an order
     */
    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    public FailureAuditing() {
    }

    public FailureAuditing(int ID, Date lastFailureDate, Time lastFailureTime, User user) {
        this.ID = ID;
        this.lastFailureDate = lastFailureDate;
        this.lastFailureTime = lastFailureTime;
        this.user = user;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getLastFailureDate() {
        return lastFailureDate;
    }

    public void setLastFailureDate(Date lastFailureDate) {
        this.lastFailureDate = lastFailureDate;
    }

    public Time getLastFailureTime() {
        return lastFailureTime;
    }

    public void setLastFailureTime(Time lastFailureTime) {
        this.lastFailureTime = lastFailureTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
