package ir.smarttrustco.cryptography.messages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageUpdateDto implements Serializable {
    private String title;
    private String message;
    private Long code;

    private String sender;
    private String receiver;
}
