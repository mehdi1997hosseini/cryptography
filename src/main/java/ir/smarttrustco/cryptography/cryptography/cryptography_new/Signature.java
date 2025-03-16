package ir.smarttrustco.cryptography.cryptography.cryptography_new;

public interface Signature extends Cryptography {
    String sign(Object key , String plaintext);
}
