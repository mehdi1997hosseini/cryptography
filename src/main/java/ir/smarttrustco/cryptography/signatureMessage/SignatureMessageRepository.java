package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.basic.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignatureMessageRepository extends BaseRepository<SignatureMessageEntity, Long> {
    SignatureMessageEntity findSignatureMessageEntityByMessage_Code(Long messageCode);
    List<SignatureMessageEntity> findAllByMessage_Code(Long messageCode);
}
