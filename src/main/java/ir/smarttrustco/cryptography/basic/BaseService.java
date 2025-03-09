package ir.smarttrustco.cryptography.basic;

import java.util.List;

public interface BaseService<E extends BaseEntity<P> , P extends Number> {
    void save(E entity);
    E findById(P id);
}
