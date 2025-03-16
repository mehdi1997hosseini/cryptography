package ir.smarttrustco.cryptography.cryptography.cryptography_new;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.util.Base64;

class DecryptDataService {
    private final AlgorithmType algorithm;

    public DecryptDataService(AlgorithmType algorithm) {
        this.algorithm = algorithm;
    }

    String decryptWithAsymmetric(Object privateKey, String encryptedData) {
        try {
            if (privateKey instanceof String)
                privateKey = MasterCryptography.KeyConvertor.convertStringToPrivateKey((String) privateKey, algorithm.name());

            Cipher cipher = Cipher.getInstance(algorithm.name());
            cipher.init(Cipher.DECRYPT_MODE, (PrivateKey) privateKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    String decryptWithSymmetric(Object key, String encryptedData) {
        try {
            if (key instanceof String)
                key = MasterCryptography.KeyConvertor.convertStringToSecretKey((String) key, algorithm.name());

            Cipher cipher = Cipher.getInstance(algorithm.name());
            cipher.init(Cipher.DECRYPT_MODE, (SecretKey) key);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
