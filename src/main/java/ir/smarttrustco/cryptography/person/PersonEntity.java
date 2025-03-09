package ir.smarttrustco.cryptography.person;

import ir.smarttrustco.cryptography.basic.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBL_PERSON")
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class PersonEntity extends BaseEntity<Long> {
    //@Column(nullable = false)
    private String firstName;
    //@Column(nullable = false)
    private String lastName;
    //@Column(nullable = false)
    private String ssn;
    private String email;
    @Column(unique = true, nullable = false)
    private String phoneNumber;

}
