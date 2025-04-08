package ir.smarttrustco.cryptography.cryptography;

import ir.smarttrustco.cryptography.cryptography.cryptography_new.AlgorithmType;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

@Component
public class DigitalSignatureServiceImpl implements DigitalSignatureService {

    public String signatureMessage(Object privateKey, String message) {
        try {
            if (privateKey instanceof String)
                privateKey = MasterCryptographyService.KeyConvertor.convertStringToPrivateKey((String) privateKey);

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign((PrivateKey) privateKey);
            signature.update(message.getBytes());
            byte[] signedBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String signatureFile(Object privateKey, String filePath) {
        try {

            byte[] pdfBytes = Files.readAllBytes(Paths.get(filePath));
            ;
            MessageDigest digest = MessageDigest.getInstance(AlgorithmType.RSA_SHA256.getAlgorithm());
            byte[] pdfHash = digest.digest(pdfBytes);

            if (privateKey instanceof String)
                privateKey = MasterCryptographyService.KeyConvertor.convertStringToPrivateKey((String) privateKey);

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign((PrivateKey) privateKey);
            signature.update(pdfHash);
            byte[] signedBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Boolean verifySignature(Object publicKey, String message, String signedMessage) {
        try {
            if (publicKey instanceof String)
                publicKey = MasterCryptographyService.KeyConvertor.convertStringToPublicKey((String) publicKey);

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify((PublicKey) publicKey);
            signature.update(message.getBytes());
            byte[] signedBytes = Base64.getDecoder().decode(signedMessage);
            return signature.verify(signedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Boolean verifySignature(Object publicKey, byte[] fileByte, String signedMessage) {
        try {
            if (publicKey instanceof String)
                publicKey = MasterCryptographyService.KeyConvertor.convertStringToPublicKey((String) publicKey);

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify((PublicKey) publicKey);
            signature.update(fileByte);
            byte[] signedBytes = Base64.getDecoder().decode(signedMessage);
            return signature.verify(signedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
