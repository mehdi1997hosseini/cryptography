package ir.smarttrustco.cryptography.messages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReceiveDto {
    private Long code;
    private String title;
    private Boolean isRead;
    private String sender;

}
