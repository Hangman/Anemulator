package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.CPU;
import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Halt extends Instruction {
    private final CPU cpu;


    public Halt(Register register, MemoryBankController memory, CPU cpu) {
        super(register, memory);
        this.cpu = cpu;
    }


    @Override
    public int run() {
        this.cpu.halt();

        return 4;
    }


    @Override
    public String toString() {
        return "Halt";
    }

}
