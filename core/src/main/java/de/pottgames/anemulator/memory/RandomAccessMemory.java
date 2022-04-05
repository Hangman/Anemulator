package de.pottgames.anemulator.memory;

public class RandomAccessMemory implements Memory {
    private final int[] memory;
    private final int   startAddress;


    public RandomAccessMemory(int startAddress, int length) {
        this.memory = new int[length];
        this.startAddress = startAddress;
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address >= this.startAddress && address < this.startAddress + this.memory.length;
    }


    @Override
    public int readByte(int address) {
        return this.memory[address - this.startAddress];
    }


    @Override
    public int readWord(int address) {
        return this.readByte(address) | this.readByte(address + 1) << 8;
    }


    @Override
    public void writeByte(int address, int value) {
        this.memory[address - this.startAddress] = value;
    }

}
