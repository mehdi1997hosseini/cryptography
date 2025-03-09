package ir.smarttrustco.cryptography.basic;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseServiceImpl<E extends BaseEntity<P>, P extends Number, R extends BaseRepository<E, P>> extends BaseEntityManager implements BaseService<E, P> {

    protected R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public void save(E entity) {
        repository.save(entity);
    }

    @Override
    public E findById(P id) {
        return repository.findById(id).orElseThrow(()-> new RuntimeException("Could not find entity with id: " + id));
    }

}
