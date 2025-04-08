package ir.smarttrustco.cryptography.attachmentFile;

import ir.smarttrustco.cryptography.basic.BaseEntity;
import ir.smarttrustco.cryptography.messages.MessageEntity;
import ir.smarttrustco.cryptography.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_FILE")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity extends BaseEntity<Long> {
    private final String filePath = "S:\\downloadFile\\";

    private String fileName;
    @Enumerated(EnumType.STRING)
    private FileType fileType = FileType.PDF;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", updatable = false)
    private MessageEntity message;

//    private String fileHash;
//    private String fileSize;

}
