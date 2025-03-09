package ir.smarttrustco.cryptography.person;

import ir.smarttrustco.cryptography.basic.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends BaseRepository<PersonEntity,Long> {
}
