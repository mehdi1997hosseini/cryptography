package ir.smarttrustco.cryptography.user;

import ir.smarttrustco.cryptography.basic.BaseServiceImpl;
import ir.smarttrustco.cryptography.basic.converter.ConverterFileUtil;
import ir.smarttrustco.cryptography.cryptography.AsymmetricEncryptionService;
import ir.smarttrustco.cryptography.cryptography.EncryptionKeyType;
import ir.smarttrustco.cryptography.cryptography.SymmetricEncryptionService;
import ir.smarttrustco.cryptography.user.dto.UserLoginByKeyDto;
import ir.smarttrustco.cryptography.user.dto.UserLoginDto;
import ir.smarttrustco.cryptography.user.dto.UserRegistryDto;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserEntity, Long, UserRepository> implements UserService {

    private final AsymmetricEncryptionService asymmetricEncryption;
    private final SymmetricEncryptionService symmetricEncryption;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository repository, AsymmetricEncryptionService asymmetricEncryption, SymmetricEncryptionService symmetricEncryption, UserMapper userMapper) {
        super(repository);
        this.asymmetricEncryption = asymmetricEncryption;
        this.symmetricEncryption = symmetricEncryption;
        this.userMapper = userMapper;
    }

    @Override
    public void save(UserEntity entity) {
        super.save(entity);
    }

    @Override
    public Boolean login(UserLoginDto login) {
        File privateKeyFile = ConverterFileUtil.convertMultipartFileToFile(login.getFile());
        String privateKeyStr = ConverterFileUtil.readFileAndConvertToString(privateKeyFile);
        UserEntity user = getEntityManager().createQuery("select u from UserEntity u where u.username = :username", UserEntity.class)
                .setParameter("username", login.getUsername())
                .getSingleResult();

        return user != null && asymmetricEncryption.decryptWithAsymmetric(privateKeyStr, user.getPassword()).equals(login.getPassword());
    }

    @Override
    public Boolean login(UserLoginByKeyDto user) {
        UserEntity entity = getEntityManager().createQuery("select u from UserEntity u where u.username = :username", UserEntity.class)
                .setParameter("username", user.getUsername())
                .getSingleResult();

        return entity != null && asymmetricEncryption.decryptWithAsymmetric(user.getPrivateKeyStr(), user.getPassword()).equals(user.getPassword());
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        UserEntity user = repository.getUserByUsername(username);
        if (user == null) throw new RuntimeException("User not found");
        return user;
    }

    @Override
    public Resource registryUser(final UserRegistryDto user) {
        List<UserEntity> findUserByUserName = getEntityManager().createQuery("select u from UserEntity u where u.username = :username", UserEntity.class)
                .setParameter("username", user.getUsername())
                .getResultList();
        if (findUserByUserName != null && !findUserByUserName.isEmpty())
            throw new RuntimeException("User by Username is exist . try enter new username ");

        Map<EncryptionKeyType, String> keys = asymmetricEncryption.generateAsymmetricKeys(user.getUsername());
        UserEntity entity = userMapper.toEntity(user);
        entity.setPublicKey(keys.get(EncryptionKeyType.PUBLIC_KEY));
        entity.setPassword(asymmetricEncryption.encryptWithAsymmetric(keys.get(EncryptionKeyType.PUBLIC_KEY), entity.getPassword()));
        save(entity);

        return ConverterFileUtil.convertStringToResource(user.getUsername(), keys.get(EncryptionKeyType.PRIVATE_KEY));
    }

}
