package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Ld_HLplus_A extends Instruction {

    public Ld_HLplus_A(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.HL);
        this.memory.writeByte(address, this.register.get(RegisterId.A));
        this.register.set(RegisterId.HL, address + 1 & 0xFFFF);

        return 8;
    }


    @Override
    public String toString() {
        return "Ld_HLplus_A";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
