package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Ld_HL_d8 extends Instruction {

    public Ld_HL_d8(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.HL);
        final int data = this.memory.readByte(this.register.getPc());
        this.register.setPc(this.register.getPc() + 1);
        this.memory.writeByte(address, data);

        return 12;
    }


    @Override
    public String toString() {
        return "Ld_HL_d8";
    }


    @Override
    public int getLength() {
        return 2;
    }

}
