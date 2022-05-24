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

package de.pottgames.anemulator.cpu;

import de.pottgames.anemulator.memory.Memory;

public abstract class Instruction {
    protected final Register register;
    protected final Memory   memory;


    public Instruction(Register register, Memory memory) {
        this.register = register;
        this.memory = memory;
    }


    public abstract int run();


    public abstract int getLength();


    public boolean isPrefix() {
        return false;
    }

}
