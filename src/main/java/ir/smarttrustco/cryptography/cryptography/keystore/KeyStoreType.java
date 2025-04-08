package ir.smarttrustco.cryptography.cryptography.keystore;

import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

public enum KeyStoreType {
    JKS, PKCS12;

    public KeyStore getInstance() {
        try {
            return KeyStore.getInstance(this.name());
        } catch (KeyStoreException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void creteFileKeyStore(KeyStore keyStore, String nameFile, Key privateKey, String password) {
        nameFile = !nameFile.endsWith(".jks") ? nameFile + ".jks" : nameFile;
        try {
            keyStore.load(null, null);
            keyStore.setKeyEntry("mykey", privateKey, password.toCharArray(), new Certificate[]{null});
            FileOutputStream fos = new FileOutputStream("S:\\keystore\\" + nameFile);
            keyStore.store(fos, password.toCharArray());
        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Key getPrivateKeyFromKeyStore(String passKeyStore, String aliasKeyStore, String passKey) {
        KeyStore keyStore = getInstance();
        String keyStorePath = "C:\\Users\\beyrami\\Desktop\\mehdi_keyPair.p12";
        try (FileInputStream fis = new FileInputStream(keyStorePath)) {
            keyStore.load(fis, passKeyStore.toCharArray());
            return (PrivateKey) keyStore.getKey(aliasKeyStore, passKey.toCharArray());
        } catch (IOException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException |
                 KeyStoreException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getStrPrivateKeyFromKeyStore(String passKeyStore, String aliasKeyStore, String passKey) {
        KeyStore keyStore = getInstance();
        String keyStorePath = "C:\\Users\\beyrami\\Desktop\\mehdi_keyPair.p12";
        try (FileInputStream fis = new FileInputStream(keyStorePath)) {
            keyStore.load(fis, passKeyStore.toCharArray());
            return convertKeyToString ((PrivateKey) keyStore.getKey(aliasKeyStore, passKey.toCharArray()));
        } catch (IOException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException |
                 KeyStoreException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Certificate getCertificate (String passKeystore ,String aliasKeystore) {
        try {
            KeyStore keyStore = getInstance();
            String keyStorePath = "C:\\Users\\beyrami\\Desktop\\mehdi_keyPair.p12";
            try (InputStream keyStream = new FileInputStream(keyStorePath)) {
                keyStore.load(keyStream, passKeystore.toCharArray());
            } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
                throw new RuntimeException(e);
            }
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                System.out.println("Alias: " + aliases.nextElement());
            }
            return keyStore.getCertificate(aliasKeystore);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String convertKeyToString(Key privateKey) {
        byte[] encryptedBytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

}
