package de.pottgames.anemulator.memory;

public class WRam implements Memory {
    private final int[] memory = new int[0xFE00 - 0xC000];


    @Override
    public boolean acceptsAddress(int address) {
        return address >= 0xC000 && address < 0xFE00;
    }


    @Override
    public int readByte(int address) {
        return this.memory[address - 0xC000];
    }


    @Override
    public void writeByte(int address, int value) {
        this.writeInternal(address, value);

        // ECHO
        if (address >= 0xC000 && address <= 0xDDFF) {
            this.writeInternal(address + 0x2000, value);
        } else if (address >= 0xE000) {
            this.writeInternal(address - 0x2000, value);
        }
    }


    private void writeInternal(int address, int value) {
        this.memory[address - 0xC000] = value;
    }

}
