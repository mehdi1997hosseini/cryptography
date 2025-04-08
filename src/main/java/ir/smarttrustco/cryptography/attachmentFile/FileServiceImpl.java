package ir.smarttrustco.cryptography.attachmentFile;

import ir.smarttrustco.cryptography.basic.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl extends BaseServiceImpl<FileEntity, Long, FileRepository> implements FileService {

    public FileServiceImpl(FileRepository repository) {
        super(repository);
    }

    @Override
    public List<FileEntity> findAllFileByMessageId(Long messageId) {
        return getEntityManager().createQuery("select f from FileEntity f where f.message.id = :messageId", FileEntity.class)
                .setParameter("messageId", messageId)
                .getResultList().stream().peek(file -> file.setMessage(null)).collect(Collectors.toList());
    }

}
