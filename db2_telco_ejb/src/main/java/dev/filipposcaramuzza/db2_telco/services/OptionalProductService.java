package dev.filipposcaramuzza.db2_telco.services;

import dev.filipposcaramuzza.db2_telco.entities.OptionalProduct;
import dev.filipposcaramuzza.db2_telco.exceptions.OptionalProductNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;

@Stateless
public class OptionalProductService {

    @PersistenceContext(unitName = "db2_telco")
    private EntityManager em;

    public OptionalProduct getOptionalProduct(int ID) throws OptionalProductNotFoundException {
        try {
            return (OptionalProduct) em.createNamedQuery("OptionalProduct.selectByID").setParameter(1, ID).getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            throw new OptionalProductNotFoundException();
        }
    }

    public List<OptionalProduct> getAllOptionalProducts() {
        return (List<OptionalProduct>) em.createNamedQuery("OptionalProduct.selectAll").getResultList();
    }

    public OptionalProduct createOptionalProduct(String name, BigDecimal monthlyFee) {
        OptionalProduct optionalProduct = new OptionalProduct();

        optionalProduct.setName(name);
        optionalProduct.setMonthlyFee(monthlyFee);
        em.persist(optionalProduct);
        em.flush();

        return optionalProduct;
    }
}
