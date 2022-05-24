/**
 * Anemulator - A Game Boy emulator<br>
 * Copyright (C) 2022 Matthias Finke
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <a href="https://www.gnu.org/licenses">https://www.gnu.org/licenses<a/>.
 */

package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class DaA extends Instruction {

    public DaA(Register register, Memory memory) {
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
        this.register.setFlag(FlagId.Z, (a & 0xFF) == 0);
        if ((a & 0x100) == 0x100) {
            this.register.setFlag(FlagId.C, true);
        }

        return 4;
    }


    @Override
    public String toString() {
        return "DaA";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
