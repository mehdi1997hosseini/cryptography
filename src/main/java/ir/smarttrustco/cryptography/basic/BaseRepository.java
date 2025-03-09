package ir.smarttrustco.cryptography.basic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E,P> extends CrudRepository<E,P> , JpaRepository<E,P> {
}
