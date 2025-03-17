package ir.smarttrustco.cryptography.cryptography.cryptography_new;

import java.util.Map;

class Asymmetric implements Cryptography {
    private final AlgorithmType algorithm;
    private final KeySizeType keySize;
    private DigitalSignatureAlgorithmType digitalSignatureAlgorithm;

    public Asymmetric(AlgorithmType algorithm, KeySizeType keySize) {
        this.algorithm = algorithm;
        this.keySize = keySize;
    }

    public Asymmetric(AlgorithmType algorithm, KeySizeType keySize, final DigitalSignatureAlgorithmType digitalSignatureAlgorithm) {
        this.algorithm = algorithm;
        this.keySize = keySize;
        this.digitalSignatureAlgorithm = digitalSignatureAlgorithm;
    }

    @Override
    public Map<KeyType, String> keyGenerator() {
        return MasterCryptography.KeyGenerator.generatedAsymmetricKeys(algorithm.name(), keySize.size());
    }

    @Override
    public String encrypt(Object key, String plaintext) {
        return new EncryptDataService(algorithm).encryptWithAsymmetric(key, plaintext);
    }

    @Override
    public String decrypt(Object key, String ciphertext) {
        return new DecryptDataService(algorithm).decryptWithAsymmetric(key, ciphertext);
    }

    @Override
    public String signature(Object privateKey, String plaintext) {
        return DigitalSignatureService.signature(privateKey, algorithm, digitalSignatureAlgorithm, plaintext);
    }

    @Override
    public Boolean verify(Object publicKey, String ciphertext, String signature) {
        return DigitalSignatureService.verifySignature(publicKey, algorithm, digitalSignatureAlgorithm, ciphertext, signature);
    }
}
