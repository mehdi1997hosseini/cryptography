package ir.smarttrustco.cryptography.user;

import ir.smarttrustco.cryptography.basic.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {
    UserEntity getUserByUsername(String username);
}
