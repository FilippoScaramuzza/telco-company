package dev.filipposcaramuzza.db2_telco.services;

import dev.filipposcaramuzza.db2_telco.entities.FailureAuditing;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class FailureAuditingService {
    @PersistenceContext(unitName = "db2_telco")
    private EntityManager em;

    public List<FailureAuditing> getFailuresAuditing() {
        return (List<FailureAuditing>)em.createNamedQuery("FailureAuditing.selectAll").getResultList();
    }
}
