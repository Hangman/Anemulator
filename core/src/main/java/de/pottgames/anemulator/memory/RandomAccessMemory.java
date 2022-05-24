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

package de.pottgames.anemulator.memory;

public class RandomAccessMemory implements Memory {
    private final String name;
    private final int[]  memory;
    private final int    startAddress;


    public RandomAccessMemory(String name, int startAddress, int length) {
        this.name = name;
        this.memory = new int[length];
        this.startAddress = startAddress;
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address >= this.startAddress && address < this.startAddress + this.memory.length;
    }


    @Override
    public int readByte(int address) {
        return this.memory[address - this.startAddress];
    }


    @Override
    public void writeByte(int address, int value) {
        this.memory[address - this.startAddress] = value;
    }


    @Override
    public String toString() {
        return this.name;
    }

}
