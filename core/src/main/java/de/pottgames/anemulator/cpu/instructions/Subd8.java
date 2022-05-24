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

public class Subd8 extends Instruction {

    public Subd8(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.memory.readByte(this.register.getPc());
        this.register.setPc(this.register.getPc() + 1);
        final int a = this.register.get(RegisterId.A);
        final int result = a - value;
        this.register.set(RegisterId.A, result & 0xFF);

        this.register.setFlag(FlagId.Z, result == 0);
        this.register.setFlag(FlagId.N, true);
        this.register.setFlag(FlagId.H, (a & 0xF) < (value & 0xF));
        this.register.setFlag(FlagId.C, a < value);

        return 8;
    }


    @Override
    public String toString() {
        return "Subd8";
    }


    @Override
    public int getLength() {
        return 2;
    }

}
