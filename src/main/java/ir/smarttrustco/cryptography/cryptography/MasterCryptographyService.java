package ir.smarttrustco.cryptography.cryptography;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

interface MasterCryptographyService {
    final String ASYMMETRIC_ALGORITHM = "RSA";
    final String SYMMETRIC_ALGORITHM = "AES";
    final String SYMMETRIC_ALGORITHM_SHA256 = "SHA-256";
    final int KEY_PAIR_SIZE = 2048;
    final int KEY_SECRET_SIZE = 512;

    // final SecretKey SECRET_KEY = MasterCryptography.KeyEncryption.generateSecretKey();

    interface KeyGenerator {
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
                javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance(SYMMETRIC_ALGORITHM);
                keyGenerator.init(KEY_SECRET_SIZE);
                return keyGenerator.generateKey();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        ;

        static SecretKey generatorSecretKeyByUsername(String username) {
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

    interface KeyConvertor {
        static String convertSecretKeyToString(SecretKey secretKey) {
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        }

        static SecretKey convertStringToSecretKey(String keyString, String algorithm) {
            byte[] decodedKey = Base64.getDecoder().decode(keyString);
            return new SecretKeySpec(decodedKey, algorithm);
        }

        static String encodeKeyToString(Key keyPair) {
            try {
                byte[] encryptedBytes = keyPair.getEncoded();
                return Base64.getEncoder().encodeToString(encryptedBytes);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        static PublicKey convertStringToPublicKey(String publicKeyStr) {
            try {
                byte[] decode = Base64.getDecoder().decode(publicKeyStr);
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decode);
                KeyFactory keyFactory = KeyFactory.getInstance(ASYMMETRIC_ALGORITHM);
                return keyFactory.generatePublic(x509EncodedKeySpec);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        static PrivateKey convertStringToPrivateKey(String privateKeyStr) {
            try {
                privateKeyStr = privateKeyStr.replaceAll("\\s+", "").trim();
                byte[] decode = Base64.getDecoder().decode(privateKeyStr);
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decode);
                KeyFactory keyFactory = KeyFactory.getInstance(ASYMMETRIC_ALGORITHM);
                return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

    }

}
