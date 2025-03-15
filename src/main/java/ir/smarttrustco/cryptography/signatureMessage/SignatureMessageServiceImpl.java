package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.basic.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SignatureMessageServiceImpl extends BaseServiceImpl<SignatureMessageEntity,Long,SignatureMessageRepository> implements SignatureMessageService {
    public SignatureMessageServiceImpl(SignatureMessageRepository repository) {
        super(repository);
    }

}
