package ir.smarttrustco.cryptography.basic;

public interface BaseService<E extends BaseEntity<P> , P extends Number> {
    void save(E entity);
    E findById(P id);
    Boolean softDeleteById(P id );
}
