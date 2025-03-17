package ir.smarttrustco.cryptography.cryptography.cryptography_new;

import ir.smarttrustco.cryptography.cryptography.SignatureAlgorithmType;
import lombok.Getter;

import java.security.KeyPair;
import java.util.List;
import java.util.Map;

public enum CryptographyType {
    SYMMETRIC(Map.of(
            AlgorithmType.AES, List.of(KeySizeType.BITS_128, KeySizeType.BITS_256, KeySizeType.BITS_384, KeySizeType.BITS_512),
            AlgorithmType.BLOWFISH, List.of(KeySizeType.BITS_128, KeySizeType.BITS_192, KeySizeType.BITS_256)
    ), List.of()),
    ASYMMETRIC(Map.of(
            AlgorithmType.RSA, List.of(KeySizeType.BITS_1024, KeySizeType.BITS_2048, KeySizeType.BITS_4096),
            AlgorithmType.EC, List.of(KeySizeType.BITS_256, KeySizeType.BITS_384, KeySizeType.BITS_521)
    ), List.of(DigitalSignatureAlgorithmType.SHA256withRSA, DigitalSignatureAlgorithmType.SHA384withRSA, DigitalSignatureAlgorithmType.SHA512withRSA));


    private final Map<AlgorithmType, List<KeySizeType>> algorithms;
    @Getter
    private final List<DigitalSignatureAlgorithmType> signatureAlgorithmTypes;

    CryptographyType(Map<AlgorithmType, List<KeySizeType>> algorithms, List<DigitalSignatureAlgorithmType> signatureAlgorithmTypes) {
        this.algorithms = algorithms;
        this.signatureAlgorithmTypes = signatureAlgorithmTypes;
    }

    public List<AlgorithmType> getAlgorithms() {
        return List.copyOf(algorithms.keySet());
    }

    public List<KeySizeType> getKeySizes(AlgorithmType algorithmType) {
        return algorithms.getOrDefault(algorithmType, List.of());
    }

    public Map<KeyType, String> instanceCryptography(AlgorithmType algorithmType, KeySizeType keySizeType) {
        try {
            if (this == SYMMETRIC) {
                return MasterCryptography.KeyGenerator.generateSymmetricSecretKey(algorithmType.name(), keySizeType.size());
            } else if (this == ASYMMETRIC) {
                KeyPair keyPair = MasterCryptography.KeyGenerator.generateKeyPair(algorithmType.name(), keySizeType.size());
                return MasterCryptography.KeyGenerator.generateAsymmetricKeys(keyPair);
            }
            throw new IllegalArgumentException("Unsupported Crypto Type");
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Cryptography instanceCryptographyModel(AlgorithmType algorithmType, KeySizeType keySizeType) {
        if (this == SYMMETRIC) {
            return new Symmetric(algorithmType, keySizeType);
        } else if (this == ASYMMETRIC) {
            return new Asymmetric(algorithmType, keySizeType);
        } else
            throw new IllegalArgumentException("Unsupported Crypto Type");
    }
    public Cryptography instanceCryptographyModel(AlgorithmType algorithmType, KeySizeType keySizeType, DigitalSignatureAlgorithmType digitalSignatureAlgorithmType) {
        if (this == SYMMETRIC) {
            return new Symmetric(algorithmType, keySizeType);
        } else if (this == ASYMMETRIC) {
            return new Asymmetric(algorithmType, keySizeType ,digitalSignatureAlgorithmType);
        } else
            throw new IllegalArgumentException("Unsupported Crypto Type");
    }

    public String encrypt(Object key, AlgorithmType algorithm, String data) throws Exception {
        if (this == SYMMETRIC) {
            return new EncryptDataService(algorithm).encryptWithSymmetric(key, data);
        } else if (this == ASYMMETRIC) {
            return new EncryptDataService(algorithm).encryptWithAsymmetric(key, data);
        } else {
            throw new IllegalArgumentException("Invalid key type for encryption");
        }
    }

    public String decrypt(Object key, AlgorithmType algorithm, String encryptedText) throws Exception {
        if (this == SYMMETRIC) {
            return new DecryptDataService(algorithm).decryptWithSymmetric(key, encryptedText);
        } else if (this == ASYMMETRIC) {
            return new DecryptDataService(algorithm).decryptWithAsymmetric(key, encryptedText);
        } else {
            throw new IllegalArgumentException("Invalid key type for decryption");
        }
    }

//    public String sign(Object privateKey , AlgorithmType algorithm, SignatureAlgorithmType signAlgorithm, String message) {
//        return Signature.signatureData(privateKey, algorithm , signAlgorithm, message);
//    }
//
//    public Boolean verify(Object publicKey , AlgorithmType algorithm, SignatureAlgorithmType signAlgorithm,String message, String signedMessage) {
//        return Signature.verifySignature(publicKey,algorithm,signAlgorithm,message,signedMessage);
//    }


}
