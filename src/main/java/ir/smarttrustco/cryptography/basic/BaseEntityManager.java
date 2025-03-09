package ir.smarttrustco.cryptography.basic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@MappedSuperclass
abstract class BaseEntityManager {

    @PersistenceContext
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
