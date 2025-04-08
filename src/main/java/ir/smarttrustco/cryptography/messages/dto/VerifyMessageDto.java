package ir.smarttrustco.cryptography.messages.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyMessageDto {
    private MessageDto messageDto;
    private String publicKeySender;
    private String publicKeyReceiver;
}
