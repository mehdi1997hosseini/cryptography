package ir.smarttrustco.cryptography.cryptography;

import org.springframework.web.multipart.MultipartFile;

public interface DigitalSignatureService {
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public String signatureMessage(Object privateKey , String message);
    public String signatureFile(Object privateKey , String filePath) ;
    public Boolean verifySignature(Object publicKey , String message, String signedMessage);
    public Boolean verifySignature(Object publicKey , byte[] fileByte, String signedMessage);
}
