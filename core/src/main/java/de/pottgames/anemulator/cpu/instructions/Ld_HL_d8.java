package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class Ld_HL_d8 extends Instruction {

    public Ld_HL_d8(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.HL);
        final int data = this.memory.read8Bit(this.register.pc);
        this.register.pc++;
        this.memory.write(address, data);

        return 12;
    }

}
