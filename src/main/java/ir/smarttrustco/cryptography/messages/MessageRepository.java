package ir.smarttrustco.cryptography.messages;

import ir.smarttrustco.cryptography.basic.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends BaseRepository<MessageEntity,Long> {
    MessageEntity findMessageEntityByCode(Long code);
}
