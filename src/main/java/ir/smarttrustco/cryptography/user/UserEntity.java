package ir.smarttrustco.cryptography.user;

import ir.smarttrustco.cryptography.basic.BaseEntity;
import ir.smarttrustco.cryptography.person.PersonEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBL_USER")
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class UserEntity extends BaseEntity<Long> {

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String publicKey;
    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private PersonEntity person;

}
