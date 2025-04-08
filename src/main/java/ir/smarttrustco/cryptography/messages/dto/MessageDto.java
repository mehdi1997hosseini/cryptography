package ir.smarttrustco.cryptography.messages.dto;

import ir.smarttrustco.cryptography.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Serializable {
    private String title;
    private Long code;
    private Boolean isRead ;
    private String encryptedMessage;

    private String sender;
    private String receiver;
}
