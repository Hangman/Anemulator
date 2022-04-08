package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Cpu;
import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.Memory;

public class Halt extends Instruction {
    private final Cpu cpu;


    public Halt(Register register, Memory memory, Cpu cpu) {
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
