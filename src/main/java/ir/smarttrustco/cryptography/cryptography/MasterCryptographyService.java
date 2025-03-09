package ir.smarttrustco.cryptography.cryptography;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;

interface MasterCryptographyService {
    final String ASYMMETRIC_ALGORITHM = "RSA";
    final String SYMMETRIC_ALGORITHM = "AES";
    final String SYMMETRIC_ALGORITHM_SHA256 = "SHA-256";
    final int KEY_PAIR_SIZE = 512;
    final int KEY_SECRET_SIZE = 512;

    // final SecretKey SECRET_KEY = MasterCryptography.KeyEncryption.generateSecretKey();

    interface KeyEncryption {
        static KeyPair generateKeyPair() {
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ASYMMETRIC_ALGORITHM);
                keyPairGenerator.initialize(KEY_PAIR_SIZE);
                return keyPairGenerator.generateKeyPair();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        static SecretKey generateSecretKey() {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(SYMMETRIC_ALGORITHM);
                keyGenerator.init(KEY_SECRET_SIZE);
                return keyGenerator.generateKey();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e.getMessage());
            }
        };

        static SecretKey generatorSecretKeyByUsername(String username){
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(SYMMETRIC_ALGORITHM_SHA256);
                byte[] key = messageDigest.digest(username.getBytes());
                key = Arrays.copyOf(key, 16);
                return new SecretKeySpec(key, SYMMETRIC_ALGORITHM);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
