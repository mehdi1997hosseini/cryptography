package ir.smarttrustco.cryptography.messages;

import ir.smarttrustco.cryptography.basic.BaseEntity;
import ir.smarttrustco.cryptography.user.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBL_MESSAGE")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class MessageEntity extends BaseEntity<Long> {

    @Column(name = "TITLE")
    private String title;
    @Column(name = "CODE", unique = true)
    private Long code;
    @Column(name = "IS_READ")
    private Boolean isRead = false;
    @Lob
    @Column(name = "TEXT_MESSAGE", length = 100000)
    private String encryptedMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SENDER_ID", updatable = false)
    private UserEntity sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_RECEIVER_ID", updatable = false)
    private UserEntity receiver;

}
