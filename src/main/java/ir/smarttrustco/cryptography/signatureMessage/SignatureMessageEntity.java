package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.basic.BaseEntity;
import ir.smarttrustco.cryptography.messages.MessageEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBL_SIGNATURE_MESSAGE")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class SignatureMessageEntity extends BaseEntity<Long> {

    @Lob
    @Column(length = 2048)
    private String signature;
    @Column(name = "IS_VERIFIED")
    private Boolean isVerified = false;

    @ManyToOne
    @JoinColumn(name = "MESSAGE_ID")
    private MessageEntity message;

}
