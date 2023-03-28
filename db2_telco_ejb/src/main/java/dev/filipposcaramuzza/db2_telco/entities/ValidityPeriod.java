package dev.filipposcaramuzza.db2_telco.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="ValidityPeriod", schema="db2_telco")
@NamedQueries({
    @NamedQuery(name="ValidityPeriod.selectByID", query = "SELECT vp FROM ValidityPeriod vp WHERE vp.ID = ?1"),
    @NamedQuery(name="ValidityPeriod.selectAll", query = "SELECT vp FROM ValidityPeriod vp")
})

public class ValidityPeriod implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int monthsNum;
    private BigDecimal monthlyFee;

    public ValidityPeriod() {}

    public ValidityPeriod(int ID, int monthsNum, BigDecimal monthlyFee) {
        this.ID = ID;
        this.monthsNum = monthsNum;
        this.monthlyFee = monthlyFee;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMonthsNum() {
        return monthsNum;
    }

    public void setMonthsNum(int monthsNum) {
        this.monthsNum = monthsNum;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
}
