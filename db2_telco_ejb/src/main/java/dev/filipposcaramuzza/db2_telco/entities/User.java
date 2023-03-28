package dev.filipposcaramuzza.db2_telco.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "User", schema = "db2_telco")
@NamedQueries({
        @NamedQuery(name = "User.checkCredentials", query = "SELECT user FROM User user WHERE user.username = ?1 AND user.password = ?2"),
        @NamedQuery(name = "User.selectInsolventUsers", query = "SELECT user FROM User user WHERE user.isInsolvent = true ")
        })
public class User implements Serializable {
    @Id
    private String username;
    private String email;
    private String password;
    private String name;
    private String surname;
    private boolean isAdmin;
    private boolean isInsolvent;
    private int failedPayments;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @OrderBy("ID DESC")
    private List<Order> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.MERGE)
    private FailureAuditing failureAuditing;

    public User() {}

    public User(String username, String email, String password, String name, String surname, boolean isAdmin, boolean isInsolvent, int failedPayments, List<Order> orders) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.isAdmin = isAdmin;
        this.isInsolvent = isInsolvent;
        this.failedPayments = failedPayments;
        this.orders = orders;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isInsolvent() {
        return isInsolvent;
    }

    public void setInsolvent(boolean insolvent) {
        isInsolvent = insolvent;
    }

    public int getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(int failedPayments) {
        this.failedPayments = failedPayments;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public FailureAuditing getFailureAuditing() {
        return failureAuditing;
    }

    public void setFailureAuditing(FailureAuditing failureAuditing) {
        this.failureAuditing = failureAuditing;
    }
}
