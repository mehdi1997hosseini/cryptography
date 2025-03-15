package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.basic.BaseEntity;
import ir.smarttrustco.cryptography.messages.MessageEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @ManyToOne
    private MessageEntity message;

    private String signature;

}
