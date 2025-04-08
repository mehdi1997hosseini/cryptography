package ir.smarttrustco.cryptography.cryptography.cryptography_new;


import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

class DigitalSignatureService {


    static String signature(Object privateKey, AlgorithmType algorithm,
                            DigitalSignatureAlgorithmType digitalSignatureAlgorithm, String message) {
        try {
            if (privateKey instanceof String)
                privateKey = MasterCryptography.KeyConvertor.convertStringToPrivateKey((String) privateKey, algorithm.name());

            Signature signature = Signature.getInstance(digitalSignatureAlgorithm.name());
            signature.initSign((PrivateKey) privateKey);
            signature.update(message.getBytes());
            byte[] signedBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    static Boolean verifySignature(Object publicKey, AlgorithmType algorithmType,
                                   DigitalSignatureAlgorithmType digitalSignatureAlgorithm, String message, String signedMessage) {
        try {
            if (publicKey instanceof String)
                publicKey = MasterCryptography.KeyConvertor.convertStringToPublicKey((String) publicKey, algorithmType.name());

            Signature signature = Signature.getInstance(digitalSignatureAlgorithm.name());
            signature.initVerify((PublicKey) publicKey);
            signature.update(message.getBytes());
            byte[] signedBytes = Base64.getDecoder().decode(signedMessage);
            return signature.verify(signedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
