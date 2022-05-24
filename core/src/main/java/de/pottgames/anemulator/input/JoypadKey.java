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

package de.pottgames.anemulator.input;

import de.pottgames.anemulator.memory.Mbc;

public enum JoypadKey {
    UP(0, JoypadKeyType.DIRECTION, 2),
    DOWN(1, JoypadKeyType.DIRECTION, 3),
    LEFT(2, JoypadKeyType.DIRECTION, 1),
    RIGHT(3, JoypadKeyType.DIRECTION, 0),
    START(4, JoypadKeyType.ACTION, 3),
    SELECT(5, JoypadKeyType.ACTION, 2),
    A(6, JoypadKeyType.ACTION, 0),
    B(7, JoypadKeyType.ACTION, 1);


    private final int           index;
    private final JoypadKeyType type;

    /**
     * The corresponding bitnum in the {@link Mbc#JOYPAD FF00} register.
     */
    private final int bitnum;


    JoypadKey(int index, JoypadKeyType type, int bitnum) {
        this.index = index;
        this.type = type;
        this.bitnum = bitnum;
    }


    public int getIndex() {
        return this.index;
    }


    public JoypadKeyType getType() {
        return this.type;
    }


    public int getBitnum() {
        return this.bitnum;
    }


    public enum JoypadKeyType {
        ACTION, DIRECTION;
    }

}
