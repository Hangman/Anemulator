package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Stop extends Instruction {

    public Stop(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        // we ignore this for now
        // TODO really stop
        return 4;
    }


    @Override
    public String toString() {
        return "STOP";
    }

}
