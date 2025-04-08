package ir.smarttrustco.cryptography.messages;

import ir.smarttrustco.cryptography.basic.BaseController;
import ir.smarttrustco.cryptography.messages.dto.ReqShowTextMessageAndFilesDto;
import ir.smarttrustco.cryptography.messages.dto.SendMessageDto;
import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("Message")
public class MessageController extends BaseController<MessageEntity, Long, MessageService> {

    @Autowired
    public MessageController(MessageService service) {
        super(service);
    }

    @RequestMapping(value = "/send-message", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> sendMessage(@ModelAttribute SendMessageDto sendMessage) {
        Boolean isSentMessage = service.sendMessage(sendMessage);
        return isSentMessage ? new ResponseEntity<>("Message sent to " + sendMessage.getReceiver(), HttpStatus.OK) :
                new ResponseEntity<>("Message could not be sent to " + sendMessage.getReceiver(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/show-all-messages/")
    public ResponseEntity<?> showAllMessages(@RequestParam String username) {
        return new ResponseEntity<>(service.showAllMessages(username), HttpStatus.OK);
    }

    @PostMapping("/show-text-message/")
    public ResponseEntity<?> showTextMessage(@RequestParam Long messageCode, @RequestBody UserDtoByKey user) {
        return new ResponseEntity<>(service.showTextMessage(messageCode, user), HttpStatus.OK);
    }

    @PostMapping(value = "/showTextMessageAndAllFile/" , consumes = { "multipart/form-data" })
    public ResponseEntity<?> showTextMessageAndAllFile(@ModelAttribute ReqShowTextMessageAndFilesDto request) {
        return new ResponseEntity<>(service.showTextMessageAndAllFile(request), HttpStatus.OK);
    }


}
