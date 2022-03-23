package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class LdHLd16 extends Instruction {

    public LdHLd16(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.set(RegisterId.HL, this.memory.read16Bit(this.register.pc));
        this.register.pc += 2;

        return 12;
    }

}
