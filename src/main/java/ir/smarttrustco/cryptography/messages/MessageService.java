package ir.smarttrustco.cryptography.messages;

import ir.smarttrustco.cryptography.basic.BaseService;
import ir.smarttrustco.cryptography.messages.dto.SendMessageDto;

public interface MessageService extends BaseService<MessageEntity,Long> {
    Boolean sendMessage(SendMessageDto message);
}
