package de.pottgames.anemulator.memory;

public class SerialBus implements Memory {
    private final int[] memory = new int[2];


    @Override
    public boolean acceptsAddress(int address) {
        return address == Memory.SB || address == Memory.SC;
    }


    @Override
    public int readByte(int address) {
        switch (address) {
            case Memory.SB:
                return this.memory[0];
            case Memory.SC:
                return this.memory[1];
            default:
                throw new RuntimeException("Invalid address.");
        }
    }


    @Override
    public void writeByte(int address, int value) {
        switch (address) {
            case Memory.SB:
                this.memory[0] = value;
                break;
            case Memory.SC:
                this.memory[1] = value;
                break;
            default:
                throw new RuntimeException("Invalid address.");
        }
    }

}
