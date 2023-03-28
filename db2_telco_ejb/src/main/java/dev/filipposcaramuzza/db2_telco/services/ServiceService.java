package dev.filipposcaramuzza.db2_telco.services;

import dev.filipposcaramuzza.db2_telco.entities.Service;
import dev.filipposcaramuzza.db2_telco.entities.ServiceOption;
import dev.filipposcaramuzza.db2_telco.exceptions.ServiceNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ServiceService {
    @PersistenceContext(unitName = "db2_telco")
    private EntityManager em;

    public Service getServiceByID(int ID) throws ServiceNotFoundException {
        try {
            return (Service) em.createNamedQuery("Service.selectByID").setParameter(1, ID).getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            throw new ServiceNotFoundException();
        }
    }

    public List<Service> getAllServices() {
        return (List<Service>) em.createNamedQuery("Service.selectAll").getResultList();
    }

    public List<String> getAllServiceOptionNames() {
        return (List<String>) em.createNamedQuery("ServiceOption.selectDistinctNames").getResultList();
    }

    public List<String> getAllServiceNames() {
        return (List<String>) em.createNamedQuery("Service.selectDistinctNames").getResultList();
    }

    public Service createService(String serviceName, String[] serviceOptionNames, String[] serviceOptionNums, String[] serviceOptionExtraFees) {

        List<ServiceOption> serviceOptionList = new ArrayList<>();
        for(int i = 0; i < serviceOptionNames.length; i++) {
            serviceOptionList.add(new ServiceOption(serviceOptionNames[i], Integer.parseInt(serviceOptionNums[i]), new BigDecimal(serviceOptionExtraFees[i])));
        }

        Service service = new Service();
        service.setName(serviceName);
        service.setServiceOptions(serviceOptionList);
        em.merge(service);
        em.flush();

        return service;
    }
}
