package ir.smarttrustco.cryptography.messages;

import ir.smarttrustco.cryptography.attachmentFile.FileEntity;
import ir.smarttrustco.cryptography.attachmentFile.FileService;
import ir.smarttrustco.cryptography.basic.BaseServiceImpl;
import ir.smarttrustco.cryptography.basic.converter.ConverterFileUtil;
import ir.smarttrustco.cryptography.basic.utility.NumberUtils;
import ir.smarttrustco.cryptography.cryptography.AsymmetricEncryptionService;
import ir.smarttrustco.cryptography.cryptography.keystore.KeyStoreType;
import ir.smarttrustco.cryptography.messages.dto.*;
import ir.smarttrustco.cryptography.user.UserEntity;
import ir.smarttrustco.cryptography.user.UserService;
import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;
import jakarta.persistence.EntityGraph;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageEntity, Long, MessageRepository> implements MessageService {

    private final UserService userService;
    private final AsymmetricEncryptionService asymmetricEncryption;
    private final MapperMessageReceive messageReceiveMapper;
    private final MessageMapper messageMapper;
    private final FileService fileService;


    public MessageServiceImpl(MessageRepository repository, UserService userService,
                              AsymmetricEncryptionService asymmetricEncryption, MapperMessageReceive messageReceiveMapper,
                              MessageMapper messageMapper, FileService fileService) {
        super(repository);
        this.userService = userService;
        this.asymmetricEncryption = asymmetricEncryption;
        this.messageReceiveMapper = messageReceiveMapper;
        this.messageMapper = messageMapper;
        this.fileService = fileService;
    }

    @Override
    @Transactional
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

        MessageEntity save = repository.save(messageEntity);
        if (!message.getUploadFiles().isEmpty()){
            message.getUploadFiles().forEach(uploadFile -> {
                FileEntity fileEntity = new FileEntity();
                String originalFilename = uploadFile.getOriginalFilename();
                File file = new File(fileEntity.getFilePath() + originalFilename);
                fileEntity.setFileName(originalFilename);
                fileEntity.setMessage(save);
                try {
                    uploadFile.transferTo(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileService.save(fileEntity);
            });
        }
        return save.getId() != null;
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
    public ShowMessageDto showTextMessage(Long messageCode, UserDtoByKey user) {
        MessageEntity messageEntityByCode = repository.findMessageEntityByCode(messageCode);
        if (messageEntityByCode == null)
            throw new RuntimeException("Message with code " + messageCode + " not found");
        messageEntityByCode.setIsRead(true);
        MessageDto messageDto = messageMapper.toDto(messageEntityByCode);
        messageDto.setEncryptedMessage(asymmetricEncryption.decryptWithAsymmetric(user.getPrivateKey(), messageEntityByCode.getEncryptedMessage()));
        save(messageEntityByCode);
        return ShowMessageDto.builder().message(messageDto)
                .file(fileService.findAllFileByMessageId(messageEntityByCode.getId())).build();
    }

    @Override
    public MessageEntity findMessageEntityByMessageCode(Long messageCode, UserDtoByKey user) {
        EntityGraph<MessageEntity> entityGraph = getEntityManager().createEntityGraph(MessageEntity.class);
        entityGraph.addSubgraph("sender").addAttributeNodes("username");
        entityGraph.addSubgraph("receiver").addAttributeNodes("username");

        MessageEntity messageEntity = getEntityManager().createQuery("select m from MessageEntity m where " +
                        "m.code = :messageCode ", MessageEntity.class)
                .setParameter("messageCode", messageCode)
                .setHint("jakarta.persistence.fetchgraph", entityGraph)
                .getSingleResult();

        if (messageEntity == null)
            throw new RuntimeException("Message with code " + messageCode + " not found");

        if (!messageEntity.getReceiver().getUsername().equals(user.getUsername()) && !messageEntity.getSender().getUsername().equals(user.getUsername()))
            throw new RuntimeException("access denied ...");

        if (!messageEntity.getIsRead()) {
            messageEntity.setIsRead(true);
            save(messageEntity);
        }
        messageEntity.setEncryptedMessage(asymmetricEncryption.decryptWithAsymmetric(user.getPrivateKey(), messageEntity.getEncryptedMessage()));

        return messageEntity;
    }

    @Override
    public MessageEntity findMessageEntityByMessageCode(Long messageCode) {
        EntityGraph<MessageEntity> entityGraph = getEntityManager().createEntityGraph(MessageEntity.class);
        entityGraph.addSubgraph("sender").addAttributeNodes("id","username");
        entityGraph.addSubgraph("receiver").addAttributeNodes("id","username");
        return getEntityManager().createQuery("select e from MessageEntity e where e.code = :messageCode", MessageEntity.class)
                .setParameter("messageCode", messageCode)
                .setHint("jakarta.persistence.fetchgraph", entityGraph)
                .getSingleResult();
    }

    @Override
    public Boolean updateMessageAfterSend(MessageUpdateDto updateDto) {
        MessageEntity messageEntityByCode = repository.findMessageEntityByCode(updateDto.getCode());
        if (messageEntityByCode == null)
            throw new RuntimeException("Message with code " + updateDto.getCode() + " not found");

        softDeleteById(messageEntityByCode.getId());
        SendMessageDto sendMessageDto = new SendMessageDto();
        sendMessageDto.setSender(updateDto.getSender());
        sendMessageDto.setReceiver(updateDto.getReceiver());
        sendMessageDto.setTitle(updateDto.getTitle());
        sendMessageDto.setMessage(updateDto.getMessage());

        return sendMessage(sendMessageDto);
    }

    @Override
    public VerifyMessageDto findMessageByMessageCode(Long messageCode) {
        EntityGraph<MessageEntity> entityGraph = getEntityManager().createEntityGraph(MessageEntity.class);
        entityGraph.addSubgraph("sender").addAttributeNodes("publicKey");
        entityGraph.addSubgraph("receiver").addAttributeNodes("publicKey");

        MessageEntity messageEntity = getEntityManager().createQuery("select m from MessageEntity m where " +
                        "m.code = :messageCode ", MessageEntity.class)
                .setParameter("messageCode", messageCode)
                .setHint("jakarta.persistence.fetchgraph", entityGraph)
                .getSingleResult();

        if (messageEntity == null)
            throw new RuntimeException("Message with code " + messageCode + " not found");

        return VerifyMessageDto.builder().messageDto(messageMapper.toDto(messageEntity))
                .publicKeyReceiver(messageEntity.getReceiver().getPublicKey())
                .publicKeySender(messageEntity.getReceiver().getPublicKey()).build();
    }

    @Override
    public ShowMessageDto showTextMessageAndAllFile(ReqShowTextMessageAndFilesDto request) {
        MessageEntity messageEntity = findMessageEntityByMessageCode(request.getMessageCode());
        List<FileEntity> resultFiles = fileService.findAllFileByMessageId(messageEntity.getId());
        if (resultFiles == null || resultFiles.isEmpty())
            return null;

        messageEntity.setIsRead(true);
        save(messageEntity);
        MessageDto messageDto = messageMapper.toDto(messageEntity);
        File file = ConverterFileUtil.convertMultipartFileToFile(request.getFile());
        String privateKey = ConverterFileUtil.readFileAndConvertToString(file);
        messageDto.setEncryptedMessage(asymmetricEncryption.decryptWithAsymmetric(privateKey, messageEntity.getEncryptedMessage()));
        return ShowMessageDto.builder().message(messageDto)
                .file(resultFiles).build();
    }


}
