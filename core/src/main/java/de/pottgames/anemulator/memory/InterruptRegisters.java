package de.pottgames.anemulator.memory;

public class InterruptRegisters implements Memory {
    private final int[] memory = new int[2];


    @Override
    public boolean acceptsAddress(int address) {
        return address == Memory.IF || address == Memory.IE;
    }


    @Override
    public int readByte(int address) {
        switch (address) {
            case Memory.IF:
                return this.memory[0];
            case Memory.IE:
                return this.memory[1];
            default:
                throw new RuntimeException("Invalid address.");
        }
    }


    @Override
    public int readWord(int address) {
        throw new RuntimeException("Reading a word from the IE/IF register is not allowed.");
    }


    @Override
    public void writeByte(int address, int value) {
        switch (address) {
            case Memory.IF:
                this.memory[0] = value | 0xE0;
                break;
            case Memory.IE:
                this.memory[1] = value;
                break;
            default:
                throw new RuntimeException("Invalid address.");
        }
    }

}
