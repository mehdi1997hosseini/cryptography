package ir.smarttrustco.cryptography.user;

import ir.smarttrustco.cryptography.basic.BaseController;
import ir.smarttrustco.cryptography.user.dto.UserLoginDto;
import ir.smarttrustco.cryptography.user.dto.UserRegistryDto;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController extends BaseController<UserEntity,Long,UserService> {
    private final UserMapper userMapper;

    public UserController(UserService service, UserMapper userMapper) {
        super(service);
        this.userMapper = userMapper;
    }

    @PostMapping("/registry")
    public ResponseEntity<?> registry(@RequestBody UserRegistryDto user) {
        Resource resource = service.registryUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto user) {
        if (!service.login(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
