package dev.filipposcaramuzza.db2_telco.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ServicePackage", schema = "db2_telco")
@NamedQueries({
        @NamedQuery(name = "ServicePackage.selectAll", query = "SELECT sp FROM ServicePackage sp"),
        @NamedQuery(name = "ServicePackage.selectByID", query = "SELECT sp FROM ServicePackage sp WHERE sp.ID = ?1")
})
public class ServicePackage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;

    @ManyToMany(
            cascade = {CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @JoinTable(name="ServicePackage_Service",
            joinColumns=@JoinColumn(name="IDServicePackage"),
            inverseJoinColumns=@JoinColumn(name="IDService"))
    private List<Service> services = new ArrayList<>();

    @ManyToMany(
            cascade = {CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @JoinTable(name="ServicePackage_ValidityPeriod",
            joinColumns = @JoinColumn(name="IDServicePackage"),
            inverseJoinColumns = @JoinColumn(name="IDValidityPeriod"))
    private List<ValidityPeriod> validityPeriods = new ArrayList<>();

    @ManyToMany(
            cascade = {CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @JoinTable(name="ServicePackage_OptionalProduct",
            joinColumns = @JoinColumn(name="IDServicePackage"),
            inverseJoinColumns = @JoinColumn(name="IDOptionalProduct"))
    private List<OptionalProduct> optionalProducts = new ArrayList<>();

    public ServicePackage() {}

    public ServicePackage(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<ValidityPeriod> getValidityPeriods() {
        return validityPeriods;
    }

    public void setValidityPeriods(List<ValidityPeriod> validityPeriods) {
        this.validityPeriods = validityPeriods;
    }

    public List<OptionalProduct> getOptionalProducts() {
        return optionalProducts;
    }

    public void setOptionalProducts(List<OptionalProduct> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }
}
