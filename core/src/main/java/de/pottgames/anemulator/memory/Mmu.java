package de.pottgames.anemulator.memory;

public class Mmu implements Memory {
    // private final Array<Memory> units = new Array<>();+
    private Memory[] units = new Memory[0];
    // private final List<Memory> units = new ArrayList<>();


    public void addMemoryUnit(Memory memoryUnit) {
        // this.units.add(memoryUnit);
        final Memory[] temp = new Memory[this.units.length + 1];
        System.arraycopy(this.units, 0, temp, 0, this.units.length);
        temp[this.units.length] = memoryUnit;
        this.units = temp;
    }


    @Override
    public boolean acceptsAddress(int address) {
        final Memory unit = this.getMemoryUnit(address);
        if (unit != null) {
            return true;
        }

        return false;
    }


    private Memory getMemoryUnit(int address) {
        for (final Memory memory : this.units) {
            if (memory.acceptsAddress(address)) {
                return memory;
            }
        }

        return null;
    }


    @Override
    public int readByte(int address) {
        final Memory memory = this.getMemoryUnit(address);
        return memory.readByte(address);
    }


    @Override
    public int readWord(int address) {
        final Memory memory = this.getMemoryUnit(address);
        return memory.readWord(address);
    }


    @Override
    public void writeByte(int address, int value) {
        final Memory memory = this.getMemoryUnit(address);
        memory.writeByte(address, value);
    }

}
