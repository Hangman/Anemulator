package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Ld_HL_A extends Instruction {

    public Ld_HL_A(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.HL);
        final int value = this.register.get(RegisterId.A);
        this.memory.write(address, value);

        return 8;
    }


    @Override
    public String toString() {
        return "Ld_HL_A";
    }

}
