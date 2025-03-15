package ir.smarttrustco.cryptography.signatureMessage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignatureMessageDto implements Serializable {

    private String signature;

    private Long messageCode;
    private String title;

}
