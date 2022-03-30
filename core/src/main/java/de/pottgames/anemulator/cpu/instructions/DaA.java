package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class DaA extends Instruction {

    public DaA(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int a = this.register.get(RegisterId.A);
        final boolean flagN = this.register.isFlagSet(FlagId.N);
        final boolean flagH = this.register.isFlagSet(FlagId.H);
        final boolean flagC = this.register.isFlagSet(FlagId.C);
        if (!flagN) {
            if (flagH || (a & 0xF) > 9) {
                a += 0x06;
            }
            if (flagC || a > 0x9F) {
                a += 0x60;
            }
        } else {
            if (flagH) {
                a = a - 6 & 0xFF;
            }
            if (flagC) {
                a -= 0x60;
            }
        }
        this.register.set(RegisterId.A, a & 0xFF);

        // SET FLAGS
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.Z, a == 0);
        if ((a & 0x100) == 0x100) {
            this.register.setFlag(FlagId.C, true);
        }

        return 4;
    }


    @Override
    public String toString() {
        return "DaA";
    }

}
