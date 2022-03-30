package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Ld_HL_E extends Instruction {

    public Ld_HL_E(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.E);
        this.memory.write(this.register.get(RegisterId.HL), value);

        return 8;
    }


    @Override
    public String toString() {
        return "Ld_HL_E";
    }

}
