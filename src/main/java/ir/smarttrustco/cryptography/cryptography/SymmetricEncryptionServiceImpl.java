package ir.smarttrustco.cryptography.cryptography;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class SymmetricEncryptionServiceImpl implements SymmetricEncryptionService {
    @Override
    public String encrypt(String username, String data) {
        try {
            SecretKey secretKey = MasterCryptographyService.KeyEncryption.generatorSecretKeyByUsername(username);
            Cipher cipher  = Cipher.getInstance(SYMMETRIC_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String decrypt(String username, String encryptedData) {
        try {
            SecretKey secretKey = MasterCryptographyService.KeyEncryption.generatorSecretKeyByUsername(username);
            Cipher cipher = Cipher.getInstance(SYMMETRIC_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
