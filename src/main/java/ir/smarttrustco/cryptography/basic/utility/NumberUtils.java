package ir.smarttrustco.cryptography.basic.utility;

import java.util.Random;

public interface NumberUtils {
    static Long uniqueNumber() {
        long seed = System.currentTimeMillis();
        Random rng = new Random(seed);
//        long number = rng.nextLong();
//        long number = rng.nextLong() % 10000000000000;
        long number = (rng.nextLong() % 9000000000000l) + 1000000000000l;
        return number < 0 ? -number : number ;
    }

}
