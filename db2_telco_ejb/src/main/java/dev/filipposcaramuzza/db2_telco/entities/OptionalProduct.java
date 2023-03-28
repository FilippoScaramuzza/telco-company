package dev.filipposcaramuzza.db2_telco.entities;

import jakarta.inject.Named;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="OptionalProduct", schema="db2_telco")
@NamedQueries({
    @NamedQuery(name="OptionalProduct.selectAll", query = "SELECT op FROM OptionalProduct op"),
    @NamedQuery(name="OptionalProduct.selectByID", query = "SELECT op FROM OptionalProduct op WHERE op.ID = ?1")
})
public class OptionalProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;
    private BigDecimal monthlyFee;

    @ManyToMany(mappedBy = "optionalProducts")
    private List<Order> orders;

    public OptionalProduct() {}

    public OptionalProduct(int ID, String name, BigDecimal monthlyFee) {
        this.ID = ID;
        this.name = name;
        this.monthlyFee = monthlyFee;
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

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
}
