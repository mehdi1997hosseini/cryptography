package ir.smarttrustco.cryptography.messages;

import ir.smarttrustco.cryptography.basic.BaseService;
import ir.smarttrustco.cryptography.messages.dto.MessageDto;
import ir.smarttrustco.cryptography.messages.dto.MessageReceiveDto;
import ir.smarttrustco.cryptography.messages.dto.SendMessageDto;
import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;

import java.util.List;

public interface MessageService extends BaseService<MessageEntity,Long> {
    Boolean sendMessage(SendMessageDto message);
    List<MessageReceiveDto> showAllMessages(String username);
    MessageDto showTextMessage(Long messageCode , UserDtoByKey user);

}
