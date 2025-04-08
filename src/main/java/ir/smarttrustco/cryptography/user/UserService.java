package ir.smarttrustco.cryptography.user;

import ir.smarttrustco.cryptography.basic.BaseService;
import ir.smarttrustco.cryptography.user.dto.UserLoginByKeyDto;
import ir.smarttrustco.cryptography.user.dto.UserLoginDto;
import ir.smarttrustco.cryptography.user.dto.UserRegistryDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends BaseService<UserEntity, Long> {
    Boolean login(UserLoginDto user);
    Boolean login(UserLoginByKeyDto user);
    UserEntity getUserByUsername(String username);
    Resource registryUser(UserRegistryDto user);
}
