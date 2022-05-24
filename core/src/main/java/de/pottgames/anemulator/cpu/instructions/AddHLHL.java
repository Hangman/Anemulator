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

public class AddHLHL extends Instruction {

    public AddHLHL(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.HL);
        final int hl = this.register.get(RegisterId.HL);
        final int result = value + hl;
        this.register.set(RegisterId.HL, result & 0xFFFF);

        // SET FLAGS
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, (hl & 0xFFF) + (value & 0xFFF) > 0xFFF);
        this.register.setFlag(FlagId.C, result > 0xFFFF);

        return 8;
    }


    @Override
    public String toString() {
        return "AddHLHL";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
