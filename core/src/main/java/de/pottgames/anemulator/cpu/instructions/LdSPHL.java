package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdSPHL extends Instruction {

    public LdSPHL(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.setSp(this.register.get(RegisterId.HL));

        return 8;
    }


    @Override
    public String toString() {
        return "LdSPHL";
    }

}
