package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.basic.BaseController;
import ir.smarttrustco.cryptography.signatureMessage.dto.SignMessageRequestDto;
import ir.smarttrustco.cryptography.signatureMessage.dto.SignatureMessageDto;
import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        service.signFileMessage(signMessageRequest.getSignatureMessage(), signMessageRequest.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/verify-message/")
    public ResponseEntity<?> verifyMessage(@RequestParam Long messageCode){
        return new ResponseEntity<>(service.verify(messageCode),HttpStatus.OK);
    }

    @PostMapping("/verify-file-message/")
    public ResponseEntity<?> verifyFileMessage(@RequestParam Long messageCode){
        return new ResponseEntity<>(service.verifyFileMessage(messageCode),HttpStatus.OK);
    }

    @PostMapping("/signature-file-message/")
    public ResponseEntity<?> signatureFileMessage(@RequestParam Long messageCode) {
        service.signFileMessage(messageCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
