package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdHLd16 extends Instruction {

    public LdHLd16(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.set(RegisterId.HL, this.memory.readWord(this.register.getPc()));
        this.register.setPc(this.register.getPc() + 2);

        return 12;
    }


    @Override
    public String toString() {
        return "LdHLd16";
    }

}
