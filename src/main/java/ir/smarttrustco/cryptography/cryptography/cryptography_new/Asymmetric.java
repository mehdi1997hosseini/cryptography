package ir.smarttrustco.cryptography.cryptography.cryptography_new;

import java.util.Map;

class Asymmetric implements Cryptography {
    private final AlgorithmType algorithm;
    private final KeySizeType keySize;

    public Asymmetric(AlgorithmType algorithm, KeySizeType keySize) {
        this.algorithm = algorithm;
        this.keySize = keySize;
    }

    @Override
    public Map<KeyType, String> keyGenerator() {
        return MasterCryptography.KeyGenerator.generatedAsymmetricKeys(algorithm.name(), keySize.getSize());
    }

    @Override
    public String encrypt(Object key, String plaintext) {
        return new EncryptDataService(algorithm).encryptWithAsymmetric(key, plaintext);
    }

    @Override
    public String decrypt(Object key ,String ciphertext) {
        return new DecryptDataService(algorithm).decryptWithAsymmetric(key, ciphertext);
    }
}
