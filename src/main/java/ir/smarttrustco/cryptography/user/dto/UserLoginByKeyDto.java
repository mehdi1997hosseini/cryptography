package ir.smarttrustco.cryptography.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginByKeyDto implements Serializable {
    private String username;
    private String password;
    private String privateKeyStr;
}
