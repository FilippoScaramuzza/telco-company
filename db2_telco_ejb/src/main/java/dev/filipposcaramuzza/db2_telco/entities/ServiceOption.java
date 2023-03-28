package dev.filipposcaramuzza.db2_telco.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="ServiceOption", schema = "db2_telco")
@NamedQuery(name="ServiceOption.selectDistinctNames", query = "SELECT DISTINCT so.name FROM ServiceOption so")
public class ServiceOption implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;
    private int optionNum;
    private BigDecimal extraOptionFee;

    @ManyToMany(mappedBy = "serviceOptions")
    private List<Service> services;

    public ServiceOption() {}

    public ServiceOption(String name, int optionNum, BigDecimal extraOptionFee) {
        this.name = name;
        this.optionNum = optionNum;
        this.extraOptionFee = extraOptionFee;
    }

    public ServiceOption(int ID, String name, int optionNum, BigDecimal extraOptionFee, List<Service> services) {
        this.ID = ID;
        this.name = name;
        this.optionNum = optionNum;
        this.extraOptionFee = extraOptionFee;
        this.services = services;
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

    public int getOptionNum() {
        return optionNum;
    }

    public void setOptionNum(int optionNum) {
        this.optionNum = optionNum;
    }

    public BigDecimal getExtraOptionFee() {
        return extraOptionFee;
    }

    public void setExtraOptionFee(BigDecimal extraOptionFee) {
        this.extraOptionFee = extraOptionFee;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
