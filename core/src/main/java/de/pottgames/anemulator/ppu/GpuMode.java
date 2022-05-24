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

package de.pottgames.anemulator.ppu;

public enum GpuMode {
    OAM_SEARCH(0b10), PIXEL_TRANSFER(0b11), H_BLANK(0b00), V_BLANK(0b01);


    private final int flagBits;


    GpuMode(int flagBits) {
        this.flagBits = flagBits;
    }


    public int getFlagBits() {
        return this.flagBits;
    }

}
