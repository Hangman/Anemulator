package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class LdDEd16 extends Instruction {

    public LdDEd16(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.set(RegisterId.DE, this.memory.read16Bit(this.register.getPc()));
        this.register.setPc(this.register.getPc() + 2);

        return 12;
    }


    @Override
    public String toString() {
        return "LdDEd16";
    }

}
