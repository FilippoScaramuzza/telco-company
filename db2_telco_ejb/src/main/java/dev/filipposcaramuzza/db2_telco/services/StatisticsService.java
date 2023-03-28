package dev.filipposcaramuzza.db2_telco.services;

import dev.filipposcaramuzza.db2_telco.entities.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Stateless
public class StatisticsService {
    @PersistenceContext(unitName = "db2_telco")
    private EntityManager em;

    public List<ServicePackageStatistics> getServicePackgeStatistics() {
        return (List<ServicePackageStatistics>)em.createNamedQuery("ServicePackageStatistics.selectAll").getResultList();
    }

    public List<ServicePackageStatistics_VP> getServicePackgeStatistic_VPs() {
        return (List<ServicePackageStatistics_VP>)em.createNamedQuery("ServicePackageStatistics_VP.selectAll").getResultList();
    }

    public List<HashMap<String, Integer>> getBestSellerOptionalProduct() {
        Query query = em.createNativeQuery("use db2_telco");
        query.executeUpdate();

        return (List<HashMap<String, Integer>>)em.createNativeQuery("SELECT `name`, COUNT(IDOptionalProduct) as counters FROM OptionalProduct LEFT JOIN Order_OptionalProduct ON ID = IDOptionalProduct GROUP BY ID, `name` HAVING counters = (SELECT MAX(counts) FROM (SELECT COUNT(IDOptionalProduct) as counts, `name` FROM OptionalProduct LEFT JOIN Order_OptionalProduct ON ID = IDOptionalProduct GROUP BY ID, `name`) as counters)").getResultList();
    }
}
