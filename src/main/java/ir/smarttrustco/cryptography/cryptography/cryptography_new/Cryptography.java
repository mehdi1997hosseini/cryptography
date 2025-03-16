package ir.smarttrustco.cryptography.cryptography.cryptography_new;

import java.util.Map;

public interface Cryptography {
    Map<KeyType, String> keyGenerator();

    String encrypt(Object key, String plaintext);

    String decrypt(Object key, String ciphertext);
}
