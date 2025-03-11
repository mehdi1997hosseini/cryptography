package ir.smarttrustco.cryptography.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoByKey {
    private String username;
    private String privateKey;
}
