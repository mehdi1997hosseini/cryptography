package ir.smarttrustco.cryptography.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto implements Serializable {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private MultipartFile file;

}
