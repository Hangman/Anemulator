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

public enum Interrupt {
    VBLANK(0x40, 0), LCD_STAT(0x48, 1), TIMER(0x50, 2), SERIAL(0x58, 3), JOYPAD(0x60, 4);


    public static Interrupt[] list = Interrupt.values();

    private final int jumpAddress;
    private final int flagMask;
    private final int bitnum;


    Interrupt(int address, int bitnum) {
        this.jumpAddress = address;
        this.bitnum = bitnum;
        this.flagMask = 1 << bitnum;
    }


    public int getJumpAddress() {
        return this.jumpAddress;
    }


    public int getFlagMask() {
        return this.flagMask;
    }


    public int getBitnum() {
        return this.bitnum;
    }

}
