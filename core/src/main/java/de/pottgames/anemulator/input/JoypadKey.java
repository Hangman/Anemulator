package de.pottgames.anemulator.input;

import de.pottgames.anemulator.memory.MemoryBankController;

public enum JoypadKey {
    UP(0, JoypadKeyType.DIRECTION, 2),
    DOWN(1, JoypadKeyType.DIRECTION, 3),
    LEFT(2, JoypadKeyType.DIRECTION, 1),
    RIGHT(3, JoypadKeyType.DIRECTION, 0),
    START(4, JoypadKeyType.ACTION, 3),
    SELECT(5, JoypadKeyType.ACTION, 2),
    A(6, JoypadKeyType.ACTION, 0),
    B(7, JoypadKeyType.ACTION, 1);


    private final int           index;
    private final JoypadKeyType type;

    /**
     * The corresponding bitnum in the {@link MemoryBankController#JOYPAD FF00} register.
     */
    private final int bitnum;


    JoypadKey(int index, JoypadKeyType type, int bitnum) {
        this.index = index;
        this.type = type;
        this.bitnum = bitnum;
    }


    public int getIndex() {
        return this.index;
    }


    public JoypadKeyType getType() {
        return this.type;
    }


    public int getBitnum() {
        return this.bitnum;
    }


    public enum JoypadKeyType {
        ACTION, DIRECTION;
    }

}
