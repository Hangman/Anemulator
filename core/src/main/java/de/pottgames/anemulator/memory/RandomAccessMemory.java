package de.pottgames.anemulator.memory;

public class RandomAccessMemory implements Memory {
    private final String name;
    private final int[]  memory;
    private final int    startAddress;


    public RandomAccessMemory(String name, int startAddress, int length) {
        this.name = name;
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
    public void writeByte(int address, int value) {
        this.memory[address - this.startAddress] = value;
    }


    @Override
    public String toString() {
        return this.name;
    }

}
