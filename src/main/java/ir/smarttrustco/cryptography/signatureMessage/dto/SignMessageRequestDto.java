package ir.smarttrustco.cryptography.signatureMessage.dto;

import ir.smarttrustco.cryptography.user.dto.UserDtoByKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignMessageRequestDto implements Serializable {
    private SignatureMessageDto signatureMessage;
    private UserDtoByKey user;
}
