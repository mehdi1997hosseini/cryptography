package ir.smarttrustco.cryptography.cryptography;

public interface SymmetricEncryptionService {
    final String SYMMETRIC_ALGORITHM = "AES";

    String encrypt(String username ,String data);
    String decrypt(String username ,String encryptedData);

}
