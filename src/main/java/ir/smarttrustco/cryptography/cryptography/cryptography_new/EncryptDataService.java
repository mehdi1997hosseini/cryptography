package ir.smarttrustco.cryptography.cryptography.cryptography_new;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Base64;

class EncryptDataService {

    private final AlgorithmType algorithm;

    public EncryptDataService(AlgorithmType algorithm) {
        this.algorithm = algorithm;
    }

    String encryptWithAsymmetric(Object publicKey, String data) {
        try {
            if (publicKey instanceof String)
                publicKey = MasterCryptography.KeyConvertor.convertStringToPublicKey((String) publicKey, algorithm.name());

            Cipher cipher = Cipher.getInstance(algorithm.name());
            cipher.init(Cipher.ENCRYPT_MODE, (PublicKey) publicKey);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    String encryptWithSymmetric(Object secretKey, String data) {
        try {
            if (secretKey instanceof String)
                secretKey = MasterCryptography.KeyConvertor.convertStringToSecretKey((String) secretKey, algorithm.name());

            Cipher cipher = Cipher.getInstance(algorithm.name());
            cipher.init(Cipher.ENCRYPT_MODE, (SecretKey) secretKey);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
