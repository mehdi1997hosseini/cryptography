package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.basic.BaseServiceImpl;
import ir.smarttrustco.cryptography.cryptography.DigitalSignatureService;
import ir.smarttrustco.cryptography.messages.MessageEntity;
import ir.smarttrustco.cryptography.messages.MessageService;
import ir.smarttrustco.cryptography.messages.dto.VerifyMessageDto;
import ir.smarttrustco.cryptography.signatureMessage.dto.SignatureMessageDto;
import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;
import org.springframework.stereotype.Service;

@Service
public class SignatureMessageServiceImpl extends BaseServiceImpl<SignatureMessageEntity, Long, SignatureMessageRepository> implements SignatureMessageService {
    private final MessageService messageService;
    private final DigitalSignatureService digitalSignatureService;
    private final SignatureMessageMapper mapper;

    public SignatureMessageServiceImpl(SignatureMessageRepository repository, MessageService messageService, DigitalSignatureService digitalSignatureService
            , SignatureMessageMapper mapper) {
        super(repository);
        this.messageService = messageService;
        this.digitalSignatureService = digitalSignatureService;
        this.mapper = mapper;
    }

    @Override
    public void sign(SignatureMessageDto signatureMessage, UserDtoByKey user) {
        MessageEntity messageEntity = messageService.findMessageEntityByMessageCode(signatureMessage.getMessageCode(), user);
        signatureMessage.setSignature(digitalSignatureService.signatureMessage(user.getPrivateKey(), messageEntity.getEncryptedMessage()));
        SignatureMessageEntity entity = mapper.toEntity(signatureMessage);
        entity.setMessage(messageEntity);
        save(entity);
    }

    @Override
    public Boolean verify(Long messageCode) {
        VerifyMessageDto messageByMessageCode = messageService.findMessageByMessageCode(messageCode);
        SignatureMessageEntity signatureMessageEntityByMessageCode = repository.findSignatureMessageEntityByMessage_Code(messageByMessageCode.getMessageDto().getCode());
        return digitalSignatureService.verifySignature(messageByMessageCode.getPublicKeyReceiver(),messageByMessageCode.getMessageDto().getEncryptedMessage(),signatureMessageEntityByMessageCode.getSignature());
    }

}
