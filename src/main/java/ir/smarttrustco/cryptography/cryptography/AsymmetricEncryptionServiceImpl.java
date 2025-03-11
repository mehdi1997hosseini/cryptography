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

    private static String encodeKeyToString(Key keyPair) {
        try {
            byte[] encryptedBytes = keyPair.getEncoded();
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    private PrivateKey getPrivateKeyFromString(String privateKeyStr) throws Exception {
        privateKeyStr = privateKeyStr.replaceAll("\\s+", "").trim();
        byte[] decode = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decode);
        KeyFactory keyFactory = KeyFactory.getInstance(ASYMMETRIC_ALGORITHM);
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }
    private PublicKey getPublicKeyFromString(String publicKeyStr) throws Exception {
        byte[] decode = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decode);
        KeyFactory keyFactory = KeyFactory.getInstance(ASYMMETRIC_ALGORITHM);
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }
    @Override
    public Map<EncryptionKeyType, String> generateAsymmetricKeys(String key) {
        Map<EncryptionKeyType, String> asymmetricKeys = new HashMap<>();
        KeyPair keyPair = KeyEncryption.generateKeyPair();
        asymmetricKeys.put(EncryptionKeyType.PRIVATE_KEY, encodeKeyToString(keyPair.getPrivate()));
        asymmetricKeys.put(EncryptionKeyType.PUBLIC_KEY, encodeKeyToString(keyPair.getPublic()));
        return asymmetricKeys;
    }
    @Override
    public String encryptWithAsymmetric(String publicKeyStr, String data) {
        try {
            PublicKey publicKey = getPublicKeyFromString(publicKeyStr);
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
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKeyFromString(privateKeyStr));
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
