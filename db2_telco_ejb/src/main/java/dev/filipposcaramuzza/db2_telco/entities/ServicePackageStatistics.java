package dev.filipposcaramuzza.db2_telco.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This class represent a record on the ServicePackageStatistics table.
 */
@Entity
@Table(name="ServicePackageStatistics", schema="db2_telco")
@NamedQuery(name = "ServicePackageStatistics.selectAll", query = "SELECT statistics FROM ServicePackageStatistics statistics")
public class ServicePackageStatistics implements Serializable {
    /**
     * Artificial PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    /**
     * Service Package the statistic refers to
     */
    @OneToOne
    @JoinColumn(name = "IDServicePackage")
    private ServicePackage servicePackage;
    /**
     * Total number of sales of the service package
     */
    private int numOfSales;
    /**
     * The total value of the sales of the ServicePackage
     */
    private BigDecimal totalValueOfSales;
    /**
     * The average number of optional product of all the sales of the Service Package
     */
    private float averageOptionalProducts;

    public ServicePackageStatistics() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public int getNumOfSales() {
        return numOfSales;
    }

    public void setNumOfSales(int numOfSales) {
        this.numOfSales = numOfSales;
    }

    public BigDecimal getTotalValueOfSales() {
        return totalValueOfSales;
    }

    public void setTotalValueOfSales(BigDecimal totalValueOfSales) {
        this.totalValueOfSales = totalValueOfSales;
    }

    public float getAverageOptionalProducts() {
        return averageOptionalProducts;
    }

    public void setAverageOptionalProducts(float averageOptionalProducts) {
        this.averageOptionalProducts = averageOptionalProducts;
    }
}
