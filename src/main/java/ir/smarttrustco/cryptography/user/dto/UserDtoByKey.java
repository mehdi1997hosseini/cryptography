package ir.smarttrustco.cryptography.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoByKey {
    @NonNull
    private String username;
    @NonNull
    private String privateKey;
}
