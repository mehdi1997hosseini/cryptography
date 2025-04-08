package ir.smarttrustco.cryptography.signatureMessage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignatureMessageDto implements Serializable {

    private Long messageCode;
    private String title;
    private String signature;
    private Boolean isVerified;

}
