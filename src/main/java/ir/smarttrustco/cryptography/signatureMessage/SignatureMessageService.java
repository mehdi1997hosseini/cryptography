package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.basic.BaseService;
import ir.smarttrustco.cryptography.signatureMessage.dto.SignatureMessageDto;
import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;

public interface SignatureMessageService extends BaseService<SignatureMessageEntity, Long> {
    void sign(SignatureMessageDto signatureMessage , UserDtoByKey user);
    Boolean verify(Long messageCode);
}
