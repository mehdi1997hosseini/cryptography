package ir.smarttrustco.cryptography.messages.dto;

import ir.smarttrustco.cryptography.attachmentFile.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowMessageDto implements Serializable {
    private MessageDto message;
    private List<FileEntity> file;

}
