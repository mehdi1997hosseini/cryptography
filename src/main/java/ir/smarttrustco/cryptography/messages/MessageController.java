package ir.smarttrustco.cryptography.messages;

import ir.smarttrustco.cryptography.basic.BaseController;
import ir.smarttrustco.cryptography.messages.dto.SendMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Message")
public class MessageController extends BaseController<MessageEntity, Long, MessageService> {

    @Autowired
    public MessageController(MessageService service) {
        super(service);
    }

    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageDto sendMessage) {
        service.sendMessage(sendMessage);
        return null;
    }
}
