package ir.smarttrustco.cryptography.attachmentFile;

import ir.smarttrustco.cryptography.basic.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface FileService extends BaseService<FileEntity, Long> {
    List<FileEntity> findAllFileByMessageId(Long messageId);
}
