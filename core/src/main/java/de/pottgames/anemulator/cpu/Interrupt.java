package de.pottgames.anemulator.cpu;

public enum Interrupt {
    VBLANK(0x40, 1 << 0), LCD_STAT(0x48, 1 << 1), TIMER(0x50, 1 << 2), SERIAL(0x58, 1 << 3), JOYPAD(0x60, 1 << 4);


    private final int jumpAddress;
    private final int flagMask;


    Interrupt(int address, int flagMask) {
        this.jumpAddress = address;
        this.flagMask = flagMask;
    }


    public int getJumpAddress() {
        return this.jumpAddress;
    }


    public int getFlagMask() {
        return this.flagMask;
    }

}
