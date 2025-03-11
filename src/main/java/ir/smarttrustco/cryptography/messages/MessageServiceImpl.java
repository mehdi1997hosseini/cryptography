package ir.smarttrustco.cryptography.messages;

import ir.smarttrustco.cryptography.basic.BaseServiceImpl;
import ir.smarttrustco.cryptography.basic.utility.NumberUtils;
import ir.smarttrustco.cryptography.cryptography.AsymmetricEncryptionService;
import ir.smarttrustco.cryptography.cryptography.SymmetricEncryptionService;
import ir.smarttrustco.cryptography.messages.dto.MapperMessageReceive;
import ir.smarttrustco.cryptography.messages.dto.MessageDto;
import ir.smarttrustco.cryptography.messages.dto.MessageReceiveDto;
import ir.smarttrustco.cryptography.messages.dto.SendMessageDto;
import ir.smarttrustco.cryptography.user.UserEntity;
import ir.smarttrustco.cryptography.user.UserService;
import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;
import jakarta.persistence.EntityGraph;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageEntity, Long, MessageRepository> implements MessageService {

    private final UserService userService;
    private final AsymmetricEncryptionService asymmetricEncryption;
    private final MapperMessageReceive messageReceiveMapper;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository repository, UserService userService,
                              AsymmetricEncryptionService asymmetricEncryption, MapperMessageReceive messageReceiveMapper,
                              MessageMapper messageMapper) {
        super(repository);
        this.userService = userService;
        this.asymmetricEncryption = asymmetricEncryption;
        this.messageReceiveMapper = messageReceiveMapper;
        this.messageMapper = messageMapper;
    }

    @Override
    public Boolean sendMessage(SendMessageDto message) {
        MessageEntity messageEntity = new MessageEntity();
        UserEntity sender = userService.getUserByUsername(message.getSender());

        if (sender == null || sender.getId() == null)
            throw new RuntimeException("sender by username not found in system");
        messageEntity.setSender(sender);

        UserEntity receiver = userService.getUserByUsername(message.getReceiver());
        if (receiver == null || receiver.getId() == null)
            throw new RuntimeException("Receiver by username not found in system");
        messageEntity.setReceiver(receiver);

        messageEntity.setTitle(message.getTitle());
        messageEntity.setCode(NumberUtils.uniqueNumber());

        messageEntity.setEncryptedMessage(asymmetricEncryption.encryptWithAsymmetric(receiver.getPublicKey(), message.getMessage()));

        try {
            super.save(messageEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<MessageReceiveDto> showAllMessages(String username) {
        EntityGraph<MessageEntity> entityGraph = getEntityManager().createEntityGraph(MessageEntity.class);
        entityGraph.addSubgraph("sender").addAttributeNodes("username");
        entityGraph.addSubgraph("receiver").addAttributeNodes("username");

        List<MessageEntity> entities = getEntityManager().createQuery("select m from MessageEntity m where " +
                        "m.receiver.username = :username ", MessageEntity.class)
                .setParameter("username", username)
                .setHint("jakarta.persistence.fetchgraph", entityGraph)
                .getResultList();

        return messageReceiveMapper.toDto(entities);
    }

    @Override
    public MessageDto showTextMessage(Long messageCode, UserDtoByKey user) {
        MessageEntity messageEntityByCode = repository.findMessageEntityByCode(messageCode);
        if (messageEntityByCode == null)
            throw new RuntimeException("Message with code " + messageCode + " not found");
        messageEntityByCode.setIsRead(true);
        MessageDto messageDto = messageMapper.toDto(messageEntityByCode);
        messageDto.setEncryptedMessage(asymmetricEncryption.decryptWithAsymmetric(user.getPrivateKey(), messageEntityByCode.getEncryptedMessage()));
        save(messageEntityByCode);

        return messageDto;
    }

}
