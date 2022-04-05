package de.pottgames.anemulator.cpu;

public enum Interrupt {
    VBLANK(0x40, 0), LCD_STAT(0x48, 1), TIMER(0x50, 2), SERIAL(0x58, 3), JOYPAD(0x60, 4);


    public static Interrupt[] list = Interrupt.values();

    private final int jumpAddress;
    private final int flagMask;
    private final int bitnum;


    Interrupt(int address, int bitnum) {
        this.jumpAddress = address;
        this.bitnum = bitnum;
        this.flagMask = 1 << bitnum;
    }


    public int getJumpAddress() {
        return this.jumpAddress;
    }


    public int getFlagMask() {
        return this.flagMask;
    }


    public int getBitnum() {
        return this.bitnum;
    }

}
