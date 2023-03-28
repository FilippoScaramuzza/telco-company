package dev.filipposcaramuzza.db2_telco.services;

import dev.filipposcaramuzza.db2_telco.entities.Service;
import dev.filipposcaramuzza.db2_telco.entities.ValidityPeriod;
import dev.filipposcaramuzza.db2_telco.exceptions.ValidityPeriodNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.util.List;

@Stateless
public class ValidityPeriodService {
    @PersistenceContext(unitName = "db2_telco")
    private EntityManager em;

    public List<ValidityPeriod> getAllValidityPeriods() {
        return (List<ValidityPeriod>) em.createNamedQuery("ValidityPeriod.selectAll").getResultList();
    }

    public ValidityPeriod getValidityPeriod(int ID) throws ValidityPeriodNotFoundException {
        try {
            return (ValidityPeriod) em.createNamedQuery("ValidityPeriod.selectByID").setParameter(1, ID).getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            throw new ValidityPeriodNotFoundException();
        }
    }


    public ValidityPeriod createValidityPeriod(int monthsNum, BigDecimal monthlyFee) {
        ValidityPeriod validityPeriod = new ValidityPeriod();

        validityPeriod.setMonthsNum(monthsNum);
        validityPeriod.setMonthlyFee(monthlyFee);

        em.persist(validityPeriod);
        em.flush();

        return validityPeriod;
    }
}
