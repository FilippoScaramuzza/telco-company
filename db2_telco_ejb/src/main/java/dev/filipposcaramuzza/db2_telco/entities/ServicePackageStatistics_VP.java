package dev.filipposcaramuzza.db2_telco.entities;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * This class represents a record on the ServicePackageStatistics_VP table. A record on this table
 * is the count of sales per service package AND validity period.
 */
@Entity
@Table(name = "ServicePackageStatistics_VP", schema = "db2_telco")
@NamedQuery(name = "ServicePackageStatistics_VP.selectAll", query = "SELECT statisticsvp FROM ServicePackageStatistics_VP statisticsvp")
public class ServicePackageStatistics_VP implements Serializable {
    @Id
    private int ID;
    @OneToOne
    @JoinColumn(name = "IDServicePackage")
    private ServicePackage servicePackage;
    @OneToOne
    @JoinColumn(name = "IDValidityPeriod")
    private ValidityPeriod validityPeriod;

    private int numOfSales;

    public ServicePackageStatistics_VP() {
    }

    public ServicePackageStatistics_VP(int ID, ServicePackage servicePackage, ValidityPeriod validityPeriod, int numOfSales) {
        this.ID = ID;
        this.servicePackage = servicePackage;
        this.validityPeriod = validityPeriod;
        this.numOfSales = numOfSales;
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

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public int getNumOfSales() {
        return numOfSales;
    }

    public void setNumOfSales(int numOfSales) {
        this.numOfSales = numOfSales;
    }
}
