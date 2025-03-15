package ir.smarttrustco.cryptography.cryptography;

public interface DigitalSignatureService {
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public String signatureMessage(Object privateKey , String message);
    public Boolean verifySignature(Object publicKey , String message, String signedMessage);
}
