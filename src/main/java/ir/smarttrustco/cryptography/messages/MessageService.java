package ir.smarttrustco.cryptography.messages;

import ir.smarttrustco.cryptography.basic.BaseService;
import ir.smarttrustco.cryptography.messages.dto.*;
import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;

import java.util.List;

public interface MessageService extends BaseService<MessageEntity,Long> {
    Boolean sendMessage(SendMessageDto message);
    List<MessageReceiveDto> showAllMessages(String username);
    MessageDto showTextMessage(Long messageCode , UserDtoByKey user);
    MessageEntity findMessageEntityByMessageCode(Long messageCode , UserDtoByKey user);
    Boolean updateMessageAfterSend(MessageUpdateDto updateDto);
    VerifyMessageDto findMessageByMessageCode(Long messageCode);
}
