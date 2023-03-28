package dev.filipposcaramuzza.db2_telco.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="Order", schema = "db2_telco")
@NamedQuery(name="Order.selectOrdersByUsername", query = "SELECT o FROM Order o WHERE o.user.username = ?1 ORDER BY o.ID DESC")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private Date creationDate;
    private Time creationHour;
    private Date startDate;
    private BigDecimal totalValue;
    private boolean isValid;

    @ManyToOne
    @JoinColumn(name="username")
    private User user;

    @ManyToOne
    @JoinColumn(name="IDServicePackage")
    private ServicePackage servicePackage;

    @ManyToOne
    @JoinColumn(name="IDValidityPeriod")
    private ValidityPeriod validityPeriod;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE}
    )
    @JoinTable(name="Order_OptionalProduct",
            joinColumns=@JoinColumn(name="IDOrder"),
            inverseJoinColumns=@JoinColumn(name="IDOptionalProduct"))
    private List<OptionalProduct> optionalProducts;

    public Order() {}

    public Order(int ID, Date creationDate, Time creationHour, Date startDate, BigDecimal totalValue, boolean isValid, User user, ServicePackage servicePackage, ValidityPeriod validityPeriod, List<OptionalProduct> optionalProducts) {
        this.ID = ID;
        this.creationDate = creationDate;
        this.creationHour = creationHour;
        this.startDate = startDate;
        this.totalValue = totalValue;
        this.isValid = isValid;
        this.user = user;
        this.servicePackage = servicePackage;
        this.validityPeriod = validityPeriod;
        this.optionalProducts = optionalProducts;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Time getCreationHour() {
        return creationHour;
    }

    public void setCreationHour(Time creationHour) {
        this.creationHour = creationHour;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public List<OptionalProduct> getOptionalProducts() {
        return optionalProducts;
    }

    public void setOptionalProducts(List<OptionalProduct> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }
}
