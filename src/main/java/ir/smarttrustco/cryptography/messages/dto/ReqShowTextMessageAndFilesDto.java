package ir.smarttrustco.cryptography.messages.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqShowTextMessageAndFilesDto implements Serializable {
    private String username;
    private Long messageCode;
    private MultipartFile file;

}
