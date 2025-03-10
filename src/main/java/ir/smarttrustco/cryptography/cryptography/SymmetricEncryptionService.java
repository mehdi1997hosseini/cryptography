package ir.smarttrustco.cryptography.cryptography;

public interface SymmetricEncryptionService {

    String encryptWithSymmitric(String username , String data);
    String decryptWithSymmitric(String username , String encryptedData);

}
