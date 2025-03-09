package ir.smarttrustco.cryptography.user.dto;

import ir.smarttrustco.cryptography.person.PersonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistryDto implements Serializable {
    private String username;
    private String password;
    private Long personId;

}
