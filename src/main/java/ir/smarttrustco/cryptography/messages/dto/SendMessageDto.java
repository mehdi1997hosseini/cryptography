package ir.smarttrustco.cryptography.messages.dto;

import ir.smarttrustco.cryptography.attachmentFile.dto.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageDto implements Serializable {
    private String title;
    private String message;
    private List<MultipartFile> uploadFiles;

    private String sender;
    private String receiver;
}
