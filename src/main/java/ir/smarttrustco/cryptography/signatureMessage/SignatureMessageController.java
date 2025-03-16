package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.basic.BaseController;
import ir.smarttrustco.cryptography.signatureMessage.dto.SignMessageRequestDto;
import ir.smarttrustco.cryptography.signatureMessage.dto.SignatureMessageDto;
import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signature-message")
public class SignatureMessageController extends BaseController<SignatureMessageEntity,Long,SignatureMessageService> {

    public SignatureMessageController(SignatureMessageService service) {
        super(service);
    }

    public ResponseEntity<?> sign(@RequestBody SignatureMessageDto signatureMessage , @RequestBody@NotNull UserDtoByKey user) {
        return null;
    }

    @PostMapping("/sign-message/")
    public ResponseEntity<?> signMessage(@RequestBody SignMessageRequestDto signMessageRequest){
        service.sign(signMessageRequest.getSignatureMessage(), signMessageRequest.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/verify-message/")
    public ResponseEntity<?> verifyMessage(@RequestParam Long messageCode){
        return new ResponseEntity<>(service.verify(messageCode),HttpStatus.OK);
    }

}
