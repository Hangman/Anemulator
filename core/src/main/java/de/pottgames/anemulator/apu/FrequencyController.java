package de.pottgames.anemulator.apu;

import de.pottgames.anemulator.memory.Memory;

public class FrequencyController implements Memory {
    private final int frequencyRegisterAddress;


    public FrequencyController(int frequencyRegisterAddress) {
        this.frequencyRegisterAddress = frequencyRegisterAddress;
    }


    @Override
    public boolean acceptsAddress(int address) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public int readByte(int address) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public int readWord(int address) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void writeByte(int address, int value) {
        // TODO Auto-generated method stub

    }

}
