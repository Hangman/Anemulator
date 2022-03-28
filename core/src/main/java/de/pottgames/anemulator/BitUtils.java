package de.pottgames.anemulator;

public class BitUtils {

    public static boolean isBitSet(int value, int bitnum) {
        return (value & 1 << bitnum) > 0;
    }

}
