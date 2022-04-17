package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Ld_a16_A extends Instruction {

    public Ld_a16_A(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.memory.readWord(this.register.getPc());
        this.register.setPc(this.register.getPc() + 2);
        final int value = this.register.get(RegisterId.A);
        this.memory.writeByte(address, value);

        return 16;
    }


    @Override
    public String toString() {
        return "Ld_a16_A";
    }


    @Override
    public int getLength() {
        return 3;
    }

}
