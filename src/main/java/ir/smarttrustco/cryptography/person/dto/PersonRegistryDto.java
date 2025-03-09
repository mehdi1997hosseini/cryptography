package ir.smarttrustco.cryptography.person.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonRegistryDto implements Serializable {
    private String firstName;
    private String lastName;
    private String ssn;
    private String email;
    private String phoneNumber;

}
