package ir.smarttrustco.cryptography.messages;

import ir.smarttrustco.cryptography.basic.BaseServiceImpl;
import ir.smarttrustco.cryptography.basic.utility.NumberUtils;
import ir.smarttrustco.cryptography.cryptography.SymmetricEncryptionService;
import ir.smarttrustco.cryptography.messages.dto.SendMessageDto;
import ir.smarttrustco.cryptography.user.UserEntity;
import ir.smarttrustco.cryptography.user.UserService;
import jakarta.persistence.EntityGraph;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageEntity, Long, MessageRepository> implements MessageService {

    private final UserService userService;
    private final SymmetricEncryptionService symmetricEncryption;

    public MessageServiceImpl(MessageRepository repository, UserService userService, SymmetricEncryptionService symmetricEncryption) {
        super(repository);
        this.userService = userService;
        this.symmetricEncryption = symmetricEncryption;
    }

    @Override
    public MessageEntity findById(Long id) {
        EntityGraph<MessageEntity> entityGraph = getEntityManager().createEntityGraph(MessageEntity.class);
        entityGraph.addAttributeNodes("sender", "receiver");

        MessageEntity entity = getEntityManager().createQuery("select m from MessageEntity m where m.id = :id", MessageEntity.class)
                .setParameter("id", id)
                .setHint("jakarta.persistence.fetchgraph", entityGraph)
                .getSingleResult();
        try {
            String decryptMessage = symmetricEncryption.decrypt(entity.getReceiver().getUsername(), entity.getEncryptedMessage());
            entity.setEncryptedMessage(decryptMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public Boolean sendMessage(SendMessageDto message) {
        MessageEntity messageEntity = new MessageEntity();
        UserEntity sender = userService.getUserByUsername(message.getSender());
        messageEntity.setSender(sender);
        UserEntity receiver = userService.getUserByUsername(message.getReceiver());
        messageEntity.setReceiver(receiver);
        messageEntity.setTitle(message.getTitle());
        messageEntity.setCode(NumberUtils.uniqueNumber());
//        AsEncryption.getPublicKey(receiver.getUsername());
        messageEntity.setEncryptedMessage(message.getMessage());

        MessageEntity save = repository.save(messageEntity);
        return save.getId() != null;
    }

}
