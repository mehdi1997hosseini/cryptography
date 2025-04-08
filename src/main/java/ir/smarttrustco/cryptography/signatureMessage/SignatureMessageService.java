package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.basic.BaseService;
import ir.smarttrustco.cryptography.signatureMessage.dto.SignatureMessageDto;
import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;

public interface SignatureMessageService extends BaseService<SignatureMessageEntity, Long> {
    void signFileMessage(SignatureMessageDto signatureMessage , UserDtoByKey user);
    void signFileMessage(Long messageCode );
    Boolean verify(Long messageCode);
    Boolean verifyFileMessage(Long messageCode);

}
