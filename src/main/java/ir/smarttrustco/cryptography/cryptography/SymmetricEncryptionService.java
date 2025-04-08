package ir.smarttrustco.cryptography.cryptography;

public interface SymmetricEncryptionService {

    String encryptWithSymmetric(String username , String data);
    String decryptWithSymmetric(String username , String encryptedData);

}
