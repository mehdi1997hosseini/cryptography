package ir.smarttrustco.cryptography.messages;

import ir.smarttrustco.cryptography.basic.BaseService;
import ir.smarttrustco.cryptography.messages.dto.*;
import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService extends BaseService<MessageEntity,Long> {
    Boolean sendMessage(SendMessageDto message);
    List<MessageReceiveDto> showAllMessages(String username);
    ShowMessageDto showTextMessage(Long messageCode , UserDtoByKey user);
    MessageEntity findMessageEntityByMessageCode(Long messageCode , UserDtoByKey user);
    MessageEntity findMessageEntityByMessageCode(Long messageCode);
    Boolean updateMessageAfterSend(MessageUpdateDto updateDto);
    VerifyMessageDto findMessageByMessageCode(Long messageCode);
    ShowMessageDto showTextMessageAndAllFile(ReqShowTextMessageAndFilesDto request);
}
