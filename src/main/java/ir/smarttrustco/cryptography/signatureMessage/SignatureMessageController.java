package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.basic.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signature-message")
public class SignatureMessageController extends BaseController<SignatureMessageEntity,Long,SignatureMessageService> {

    public SignatureMessageController(SignatureMessageService service) {
        super(service);
    }


}
