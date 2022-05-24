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
import de.pottgames.anemulator.memory.Memory;

public class CallCa16 extends Instruction {

    public CallCa16(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.memory.readWord(this.register.getPc());
        this.register.setPc(this.register.getPc() + 2);
        if (this.register.isFlagSet(FlagId.C)) {
            this.register.setSp(this.register.getSp() - 1);
            this.memory.writeByte(this.register.getSp(), (this.register.getPc() & 0xFF00) >>> 8);
            this.register.setSp(this.register.getSp() - 1);
            this.memory.writeByte(this.register.getSp(), this.register.getPc() & 0xFF);
            this.register.setPc(address);
            return 16;
        }

        return 12;
    }


    @Override
    public String toString() {
        return "CallCa16";
    }


    @Override
    public int getLength() {
        return 3;
    }

}
