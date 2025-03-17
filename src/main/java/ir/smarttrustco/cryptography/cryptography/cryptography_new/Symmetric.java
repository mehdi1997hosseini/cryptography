package ir.smarttrustco.cryptography.cryptography.cryptography_new;

import java.util.Map;

class Symmetric implements Cryptography {
    private final AlgorithmType algorithm;
    private final KeySizeType keySize;

    public Symmetric(AlgorithmType algorithm, KeySizeType keySize) {
        this.algorithm = algorithm;
        this.keySize = keySize;
    }

    @Override
    public Map<KeyType, String> keyGenerator() {
        return MasterCryptography.KeyGenerator.generateSymmetricSecretKey(algorithm.name(), keySize.size());
    }

    @Override
    public String encrypt(Object key, String plaintext) {
        return new EncryptDataService(algorithm).encryptWithSymmetric(key, plaintext);
    }

    @Override
    public String decrypt(Object key, String ciphertext) {
        return new DecryptDataService(algorithm).decryptWithSymmetric(key, ciphertext);
    }

    @Override
    public String signature(Object privateKey, String plaintext) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Boolean verify(Object publicKey, String ciphertext, String signature) {
        throw new RuntimeException("Not implemented");
    }
}
