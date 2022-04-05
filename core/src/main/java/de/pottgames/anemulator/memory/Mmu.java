package de.pottgames.anemulator.memory;

import java.util.List;

public class Mmu implements Memory {
    private Memory[] unitLut = new Memory[0xFFFF + 1];


    public void addMemoryUnits(List<Memory> unitList) {
        for (int i = 0; i < this.unitLut.length; i++) {
            for (final Memory unit : unitList) {
                if (unit.acceptsAddress(i)) {
                    this.unitLut[i] = unit;
                    break;
                }
            }
        }
    }


    @Override
    public boolean acceptsAddress(int address) {
        return this.unitLut[address] != null;
    }


    @Override
    public int readByte(int address) {
        final Memory memory = this.unitLut[address];
        return memory.readByte(address);
    }


    @Override
    public void writeByte(int address, int value) {
        final Memory memory = this.unitLut[address];
        memory.writeByte(address, value);
    }

}
