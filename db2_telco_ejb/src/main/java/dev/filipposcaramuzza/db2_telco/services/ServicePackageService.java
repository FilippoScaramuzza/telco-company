package dev.filipposcaramuzza.db2_telco.services;

import dev.filipposcaramuzza.db2_telco.entities.OptionalProduct;
import dev.filipposcaramuzza.db2_telco.entities.Service;
import dev.filipposcaramuzza.db2_telco.entities.ServicePackage;
import dev.filipposcaramuzza.db2_telco.entities.ValidityPeriod;
import dev.filipposcaramuzza.db2_telco.exceptions.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ValidationException;

import java.util.List;
import java.util.Optional;

@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "db2_telco")
    private EntityManager em;

    public List<ServicePackage> getServicePackages() {
        return em.createNamedQuery("ServicePackage.selectAll").getResultList();
    }

    public ServicePackage getServicePackage(int ID) throws ServicePackageNotFoundException {
        try {
            return (ServicePackage) em.createNamedQuery("ServicePackage.selectByID").setParameter(1, ID).getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            throw new ServicePackageNotFoundException();
        }
    }

    public void createServicePackage(String servicePackageName, List<Service> services, List<OptionalProduct> optionalProducts, List<ValidityPeriod> validityPeriods) {
        ServicePackage servicePackage = new ServicePackage();

        servicePackage.setName(servicePackageName);
        servicePackage.setServices(services);
        servicePackage.setOptionalProducts(optionalProducts);
        servicePackage.setValidityPeriods(validityPeriods);

        em.persist(servicePackage);
        em.flush();
    }

    public void editServicePackage(int ID, String servicePackageName, List<Service> services, List<OptionalProduct> optionalProducts, List<ValidityPeriod> validityPeriods) {

        ServicePackage servicePackage = em.find(ServicePackage.class, ID);
        servicePackage.setName(servicePackageName);
        servicePackage.setServices(services);
        servicePackage.setOptionalProducts(optionalProducts);
        servicePackage.setValidityPeriods(validityPeriods);

        em.merge(servicePackage);
        em.flush();
    }
}
