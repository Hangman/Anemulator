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

package de.pottgames.anemulator.apu;

import de.pottgames.anemulator.memory.Memory;

public class Sweep implements Memory {
    private int            shadowFrequency;
    private int            timer;
    private int            period;
    private boolean        enabled;
    private int            frequency;
    private int            shift;
    private boolean        decrementing;
    private final Runnable disableChannel;


    public Sweep(Runnable disableChannel) {
        this.disableChannel = disableChannel;
    }


    public void step() {
        if (this.timer > 0) {
            this.timer -= 1;
        }

        if (this.timer == 0) {
            this.timer = this.period > 0 ? this.period : 8;

            if (this.enabled && this.period > 0) {
                final int newFrequency = this.calculateFrequency();

                if (newFrequency <= 2047 && this.shift > 0) {
                    this.frequency = newFrequency;
                    this.shadowFrequency = newFrequency;

                    this.calculateFrequency();
                }
            }
        }
    }


    private int calculateFrequency() {
        int newFrequency = this.shadowFrequency >>> this.shift;

        if (this.decrementing) {
            newFrequency = this.shadowFrequency - newFrequency;
        } else {
            newFrequency = this.shadowFrequency + newFrequency;
        }

        // OVERFLOW CHECK
        if (newFrequency > 2047) {
            this.disableChannel.run();
        }

        return newFrequency;
    }


    public int getFrequency(int unsweepedFrequency) {
        return this.enabled ? this.frequency : unsweepedFrequency;
    }


    public void triggerEvent(int frequency) {
        this.shadowFrequency = frequency;
        this.timer = this.period > 0 ? this.period : 8;
        this.enabled = this.period > 0 || this.shift > 0;
        if (this.shift > 0) {
            this.calculateFrequency();
        }
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == Memory.NR10;
    }


    @Override
    public int readByte(int address) {
        if (address == Memory.NR10) {
            return this.period << 4 | (this.decrementing ? 0b1000 : 0) | this.shift | 0b10000000;
        }

        throw new RuntimeException("Invalid address");
    }


    @Override
    public void writeByte(int address, int value) {
        if (address != Memory.NR10) {
            throw new RuntimeException("Invalid address");
        }

        this.decrementing = (value & 0b1000) > 0;
        this.period = (value & 0b1110000) >>> 4;
        this.shift = value & 0b111;
    }

}
