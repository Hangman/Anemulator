package de.pottgames.anemulator.apu;

import de.pottgames.anemulator.memory.Memory;

public class SweepController implements Memory {
    private final int sweepRegisterAddress;


    public SweepController(int sweepRegisterAddress) {
        this.sweepRegisterAddress = sweepRegisterAddress;
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
