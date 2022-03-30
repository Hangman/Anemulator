package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class JpNZa16 extends Instruction {

    public JpNZa16(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        if (!this.register.isFlagSet(FlagId.Z)) {
            this.register.setPc(this.memory.read16Bit(this.register.getPc()));
            return 16;
        }

        this.register.setPc(this.register.getPc() + 2);
        return 12;
    }


    @Override
    public String toString() {
        return "JpNZa16";
    }

}
