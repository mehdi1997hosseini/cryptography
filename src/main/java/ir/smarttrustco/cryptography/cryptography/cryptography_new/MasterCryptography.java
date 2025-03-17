package ir.smarttrustco.cryptography.cryptography.cryptography_new;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

interface MasterCryptography {

    interface KeyGenerator {
        static KeyPair generateKeyPair(String algorithm, Integer keySize) {
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
                keyPairGenerator.initialize(keySize);
                return keyPairGenerator.generateKeyPair();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        static SecretKey generateSecretKey(String algorithm, Integer keySize) {
            try {
                javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance(algorithm);
                keyGenerator.init(keySize);
                return keyGenerator.generateKey();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        static SecretKey generatorSecretKeyByParam(String param, String algorithmHash, String algorithm, Integer keySize) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(algorithmHash);
                byte[] key = messageDigest.digest(param.getBytes());
                key = Arrays.copyOf(key, keySize);
                return new SecretKeySpec(key, algorithm);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        static Map<KeyType, String> generateAsymmetricKeys(KeyPair keyPair) {
            Map<KeyType, String> asymmetricKeys = new HashMap<>();
            asymmetricKeys.put(KeyType.ASYMMETRIC_PRIVATE_KEY, KeyConvertor.encodeKeyToString(keyPair.getPrivate()));
            asymmetricKeys.put(KeyType.ASYMMETRIC_PUBLIC_KEY, KeyConvertor.encodeKeyToString(keyPair.getPublic()));
            return asymmetricKeys;
        }
        static Map<KeyType, String> generatedAsymmetricKeys(String algorithm, Integer keySize) {
            return generateAsymmetricKeys(generateKeyPair(algorithm, keySize));
        }
        static Map<KeyType, String> generateSymmetricSecretKey(String algorithm, Integer keySize) {
            return Map.of(KeyType.SYMMETRIC_SECRET_KEY, MasterCryptography.KeyConvertor.convertSecretKeyToString(generateSecretKey(algorithm, keySize)));
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

        static PublicKey convertStringToPublicKey(String publicKeyStr, String algorithm) throws Exception {
            byte[] decode = Base64.getDecoder().decode(publicKeyStr);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decode);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePublic(x509EncodedKeySpec);
        }

        static PrivateKey convertStringToPrivateKey(String privateKeyStr, String algorithm) throws Exception {
            privateKeyStr = privateKeyStr.replaceAll("\\s+", "").trim();
            byte[] decode = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decode);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        }
    }


}
