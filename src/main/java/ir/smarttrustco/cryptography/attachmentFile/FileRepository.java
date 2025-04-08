package ir.smarttrustco.cryptography.attachmentFile;

import ir.smarttrustco.cryptography.basic.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends BaseRepository<FileEntity,Long> {
    List<FileEntity> findAllByMessage_Id(Long messageId);
}
