package dev.filipposcaramuzza.db2_telco.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Service", schema = "db2_telco")
@NamedQueries({
        @NamedQuery(name = "Service.selectAll", query = "SELECT service FROM Service service"),
        @NamedQuery(name = "Service.selectByID", query = "SELECT service FROM Service service WHERE service.ID = ?1"),
        @NamedQuery(name = "Service.selectDistinctNames", query = "SELECT DISTINCT service.name FROM Service service")
})
public class Service implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;

    @ManyToMany(mappedBy = "services")
    private List<ServicePackage> servicePackages = new ArrayList<>();

    @ManyToMany(
            //cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER
    )
    @JoinTable(name="Service_ServiceOption",
            joinColumns=@JoinColumn(name="IDService"),
            inverseJoinColumns=@JoinColumn(name="IDServiceOption"))
    private List<ServiceOption> serviceOptions = new ArrayList<>();

    public Service() {
    }

    public List<ServiceOption> getServiceOptions() {
        return serviceOptions;
    }

    public void setServiceOptions(List<ServiceOption> serviceOptions) {
        this.serviceOptions = serviceOptions;
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

    public List<ServicePackage> getServicePackages() {
        return servicePackages;
    }

    public void setServicePackages(List<ServicePackage> servicePackages) {
        this.servicePackages = servicePackages;
    }
}
