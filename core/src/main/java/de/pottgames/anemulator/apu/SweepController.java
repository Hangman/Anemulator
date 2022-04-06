package de.pottgames.anemulator.apu;

import de.pottgames.anemulator.memory.Memory;

public class SweepController implements Memory {
    private final int   registerAddress;
    private final int[] register = new int[1];


    public SweepController(int registerAddress) {
        this.registerAddress = registerAddress;
    }


    public void step() {

    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == this.registerAddress;
    }


    @Override
    public int readByte(int address) {
        return this.register[address - this.registerAddress];
    }


    @Override
    public void writeByte(int address, int value) {
        this.register[address - this.registerAddress] = value;
        // TODO: INTERPRETE
    }

}
