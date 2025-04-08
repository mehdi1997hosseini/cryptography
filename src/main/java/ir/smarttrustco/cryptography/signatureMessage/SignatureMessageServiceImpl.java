package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.attachmentFile.FileEntity;
import ir.smarttrustco.cryptography.attachmentFile.FileService;
import ir.smarttrustco.cryptography.basic.BaseServiceImpl;
import ir.smarttrustco.cryptography.cryptography.DigitalSignatureService;
import ir.smarttrustco.cryptography.cryptography.cryptography_new.AlgorithmType;
import ir.smarttrustco.cryptography.cryptography.keystore.KeyStoreType;
import ir.smarttrustco.cryptography.messages.MessageEntity;
import ir.smarttrustco.cryptography.messages.MessageService;
import ir.smarttrustco.cryptography.messages.dto.VerifyMessageDto;
import ir.smarttrustco.cryptography.signatureMessage.dto.SignatureMessageDto;
import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SignatureMessageServiceImpl extends BaseServiceImpl<SignatureMessageEntity, Long, SignatureMessageRepository> implements SignatureMessageService {
    private final MessageService messageService;
    private final DigitalSignatureService digitalSignatureService;
    private final SignatureMessageMapper mapper;
    private final FileService fileService;

    public SignatureMessageServiceImpl(SignatureMessageRepository repository, MessageService messageService, DigitalSignatureService digitalSignatureService
            , SignatureMessageMapper mapper, FileService fileService) {
        super(repository);
        this.messageService = messageService;
        this.digitalSignatureService = digitalSignatureService;
        this.mapper = mapper;
        this.fileService = fileService;
    }

    @Override
    public void signFileMessage(SignatureMessageDto signatureMessage, UserDtoByKey user) {
        MessageEntity messageEntity = messageService.findMessageEntityByMessageCode(signatureMessage.getMessageCode(), user);
        signatureMessage.setSignature(digitalSignatureService.signatureMessage(user.getPrivateKey(), messageEntity.getEncryptedMessage()));
        SignatureMessageEntity entity = mapper.toEntity(signatureMessage);
        entity.setMessage(messageEntity);
        save(entity);
    }

    @Override
    public void signFileMessage(Long messageCode) {
        MessageEntity messageEntity = messageService.findMessageEntityByMessageCode(messageCode);
        SignatureMessageEntity signatureMessage = new SignatureMessageEntity();
        List<FileEntity> files = fileService.findAllFileByMessageId(messageEntity.getId());
        if (files == null || files.isEmpty())
            return;

        try {
            Key privateKey = KeyStoreType.PKCS12.getPrivateKeyFromKeyStore("1376", "mehdi", "1376");

            files.forEach(file -> {
                String filePath = file.getFilePath() + file.getFileName();
                String signFile = digitalSignatureService.signatureFile(privateKey, filePath);
                signatureMessage.setSignature(signFile);
                signatureMessage.setMessage(messageEntity);
                System.out.println("Signing file: " + file.getFileName());
                save(signatureMessage);
            });
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Boolean verify(Long messageCode) {
        VerifyMessageDto messageByMessageCode = messageService.findMessageByMessageCode(messageCode);
        SignatureMessageEntity signatureMessageEntityByMessageCode = repository.findSignatureMessageEntityByMessage_Code(messageByMessageCode.getMessageDto().getCode());
        return digitalSignatureService.verifySignature(messageByMessageCode.getPublicKeyReceiver(), messageByMessageCode.getMessageDto().getEncryptedMessage(), signatureMessageEntityByMessageCode.getSignature());
    }

    @Override
    public Boolean verifyFileMessage(Long messageCode) {
        MessageEntity messageEntity = messageService.findMessageEntityByMessageCode(messageCode);
        List<SignatureMessageEntity> signatureMessageEntityByMessageCode = repository.findAllByMessage_Code(messageEntity.getCode());
        List<FileEntity> files = fileService.findAllFileByMessageId(messageEntity.getId());
        AtomicReference<Boolean> isVerifyFile = new AtomicReference<>(false);
        files.forEach(file -> {
            File systemFile = new File(file.getFilePath()+file.getFileName());
            byte[] byteFile = null;
            try {
                byteFile = Files.readAllBytes(systemFile.toPath());
                MessageDigest digest = MessageDigest.getInstance(AlgorithmType.RSA_SHA256.getAlgorithm());
                byte[] hashFile = digest.digest(byteFile);
                Certificate certificate = KeyStoreType.PKCS12.getCertificate("1376","mehdi");
                PublicKey publicKey = certificate.getPublicKey();
                SignatureMessageEntity signatureMessage = signatureMessageEntityByMessageCode.stream().filter(sign -> digitalSignatureService.verifySignature(publicKey, hashFile, sign.getSignature())).findFirst().get();
                if ( signatureMessage!=null) {
                    System.out.println("✅ امضای دیجیتال معتبر است."+ file.getFileName());
                    isVerifyFile.set(true);
                } else {
                    System.out.println("❌ امضای دیجیتال نامعتبر است." + file.getFileName());
                }

            } catch (IOException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
        return isVerifyFile.get();
    }
}
