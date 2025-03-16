package ir.smarttrustco.cryptography.cryptography.cryptography_new;

import ir.smarttrustco.cryptography.cryptography.DigitalSignatureService;

import java.util.Map;

public class DigitalSignature implements Signature {
    private final AlgorithmType algorithm;
    private final KeySizeType keySize;
    private final DigitalSignatureAlgorithmType digitalSignatureAlgorithm;

    public DigitalSignature(AlgorithmType algorithm, KeySizeType keySize, DigitalSignatureAlgorithmType digitalSignatureAlgorithm) {
        this.algorithm = algorithm;
        this.keySize = keySize;
        this.digitalSignatureAlgorithm = digitalSignatureAlgorithm;
    }

    @Override
    public String sign(Object key , String plaintext) {
        return "";
    }

    @Override
    public Map<KeyType, String> keyGenerator() {
        return Map.of();
    }

    @Override
    public String encrypt(Object key, String plaintext) {
        return "";
    }

    @Override
    public String decrypt(Object key, String ciphertext) {
        return "";
    }
}
