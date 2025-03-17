package ir.smarttrustco.cryptography.cryptography.cryptography_new;

public enum KeySizeType {
    BITS_128(128), BITS_192(192), BITS_256(256),
    BITS_384(384), BITS_512(512), BITS_521(521),
    BITS_1024(1024), BITS_2048(2048), BITS_4096(4096);

    private final int size;

    KeySizeType(int size) {
        this.size = size;
    }

    public int size() {
        return size;
    }
}
