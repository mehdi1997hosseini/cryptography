package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.basic.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureMessageRepository extends BaseRepository<SignatureMessageEntity, Long> {
}
