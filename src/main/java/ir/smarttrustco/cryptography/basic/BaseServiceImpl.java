package ir.smarttrustco.cryptography.basic;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Transactional
public class BaseServiceImpl<E extends BaseEntity<P>, P extends Number, R extends BaseRepository<E, P>> extends BaseEntityManager implements BaseService<E, P> {

    protected R repository;
    private Class<E> entityClass;

    public BaseServiceImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public void save(E entity) {
        repository.save(entity);
    }

    @Override
    public E findById(P id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Could not find entity with id: " + id));
    }

    @Override
    public Boolean softDeleteById(P id) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<E> update = cb.createCriteriaUpdate(entityClass);
        Root<E> root = update.from(entityClass);
        update.set("isDelete", Boolean.TRUE);
        update.where(cb.equal(root.get("id"), id));
        return getEntityManager().createQuery(update).executeUpdate() > 0;
    }

}
