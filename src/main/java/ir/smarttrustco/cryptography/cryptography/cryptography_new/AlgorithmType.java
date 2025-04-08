package ir.smarttrustco.cryptography.cryptography.cryptography_new;

public enum AlgorithmType {
    AES("AES"), BLOWFISH("BLOWFISH"), RSA("RSA"), EC("EC") ,RSA_SHA256("SHA-256") ,RSA_SHA384("SHA-384") ,RSA_SHA512("SHA-512") ,
    ECDSA("ECDSA");
    private String algorithm;
    AlgorithmType(String algorithm) {
        this.algorithm = algorithm;
    }
    public String getAlgorithm() {
        return algorithm;
    }

}