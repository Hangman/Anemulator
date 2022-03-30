package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class LdAd8 extends Instruction {

    public LdAd8(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.set(RegisterId.A, this.memory.read8Bit(this.register.getPc()));
        this.register.setPc(this.register.getPc() + 1);

        return 8;
    }


    @Override
    public String toString() {
        return "LdAd8";
    }

}
