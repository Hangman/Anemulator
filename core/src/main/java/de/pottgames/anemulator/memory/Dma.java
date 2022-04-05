package de.pottgames.anemulator.memory;

public class Dma implements Memory {
    private final Mmu mmu;
    private int[]     register = new int[1];


    public Dma(Mmu mmu) {
        this.mmu = mmu;
        this.register[0] = 0xFF;
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == Memory.DMA;
    }


    @Override
    public int readByte(int address) {
        return this.register[address - Memory.DMA];
    }


    @Override
    public void writeByte(int address, int value) {
        this.register[address - Memory.DMA] = value;
        final int dmaAddress = value << 8;
        for (int i = 0; i < 0xA0; i++) {
            this.mmu.writeByte(0xFE00 + i, this.mmu.readByte(dmaAddress + i));
        }
    }

}
