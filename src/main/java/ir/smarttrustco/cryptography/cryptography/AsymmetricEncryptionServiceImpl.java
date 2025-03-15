package ir.smarttrustco.cryptography.cryptography;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class AsymmetricEncryptionServiceImpl implements AsymmetricEncryptionService, MasterCryptographyService {
    @Override
    public Map<EncryptionKeyType, String> generateAsymmetricKeys(String key) {
        Map<EncryptionKeyType, String> asymmetricKeys = new HashMap<>();
        KeyPair keyPair = KeyGenerator.generateKeyPair();
        asymmetricKeys.put(EncryptionKeyType.PRIVATE_KEY, MasterCryptographyService.KeyConvertor.encodeKeyToString(keyPair.getPrivate()));
        asymmetricKeys.put(EncryptionKeyType.PUBLIC_KEY, MasterCryptographyService.KeyConvertor.encodeKeyToString(keyPair.getPublic()));
        return asymmetricKeys;
    }
    @Override
    public String encryptWithAsymmetric(String publicKeyStr, String data) {
        try {
            PublicKey publicKey = MasterCryptographyService.KeyConvertor.convertStringToPublicKey(publicKeyStr);
            Cipher cipher = Cipher.getInstance(ASYMMETRIC_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public String decryptWithAsymmetric(String privateKeyStr, String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(ASYMMETRIC_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, MasterCryptographyService.KeyConvertor.convertStringToPrivateKey(privateKeyStr));
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
