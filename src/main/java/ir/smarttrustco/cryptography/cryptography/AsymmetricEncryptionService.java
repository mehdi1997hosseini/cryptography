package ir.smarttrustco.cryptography.cryptography;

import java.util.Map;

public interface AsymmetricEncryptionService {
    Map<EncryptionKeyType,String> generateAsymmetricKeys(String key);
    String encryptWithAsymmetric(String publicKeyStr , String dataForEncrypt);
    String decryptWithAsymmetric(String privateKeyStr , String encryptedData);
}
