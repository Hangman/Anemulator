package de.pottgames.anemulator.ppu;

public enum GpuMode {
    OAM_SEARCH(0b10), PIXEL_TRANSFER(0b11), H_BLANK(0b00), V_BLANK(0b01);


    private final int flagBits;


    GpuMode(int flagBits) {
        this.flagBits = flagBits;
    }


    public int getFlagBits() {
        return this.flagBits;
    }

}
