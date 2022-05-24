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
import de.pottgames.anemulator.memory.Mmu;

public class Timer implements Memory {
    private static final int CLOCKS_PER_DIVIDER_INC = 256;
    private int              dividerAccumulator;
    private int              accumulator;
    private final int[]      memory                 = new int[4];
    private final Mmu        mmu;


    public Timer(Mmu mmu) {
        this.mmu = mmu;
        this.writeInternal(Memory.DIV, 0x18);
        this.writeInternal(Memory.TIMA, 0x00);
        this.writeInternal(Memory.TMA, 0x00);
        this.writeInternal(Memory.TAC, 0xF8);
    }


    public void step() {
        this.stepDivider();
        this.stepTimer();
    }


    private void stepDivider() {
        this.dividerAccumulator += 4;
        if (this.dividerAccumulator >= Timer.CLOCKS_PER_DIVIDER_INC) {
            this.dividerAccumulator -= Timer.CLOCKS_PER_DIVIDER_INC;
            int dividerRegister = this.readByte(Memory.DIV);
            dividerRegister++;
            if (dividerRegister > 0xFF) {
                dividerRegister = 0;
            }
            this.writeInternal(Memory.DIV, dividerRegister);
        }
    }


    private void stepTimer() {
        if ((this.readByte(Memory.TAC) & 0b100) > 0) {
            this.accumulator += 4;
            final int requiredClocks = this.requiredClocks();
            if (this.accumulator >= requiredClocks) {
                this.accumulator -= requiredClocks;
                int timer = this.readByte(Memory.TIMA);
                timer++;
                if (timer > 0xFF) {
                    final int timerInitialValue = this.readByte(Memory.TMA);
                    this.writeByte(Memory.TIMA, timerInitialValue);
                    int ifRegister = this.mmu.readByte(Memory.IF);
                    this.mmu.writeByte(Memory.IF, ifRegister |= 1 << Interrupt.TIMER.getBitnum());
                    return;
                }
                this.writeByte(Memory.TIMA, timer);
            }
        }
    }


    private int requiredClocks() {
        final int tac = this.readByte(Memory.TAC) & 0b11;
        if (tac == 0b01) {
            return 16;
        }
        if (tac == 0b10) {
            return 64;
        }
        if (tac == 0b11) {
            return 256;
        }
        return 1024;
    }


    @Override
    public boolean acceptsAddress(int address) {
        switch (address) {
            case Memory.DIV:
            case Memory.TIMA:
            case Memory.TMA:
            case Memory.TAC:
                return true;
            default:
                return false;
        }
    }


    @Override
    public int readByte(int address) {
        return this.memory[address - 0xFF04];
    }


    @Override
    public void writeByte(int address, int value) {
        if (address == Memory.DIV) {
            value = 0;
            this.writeInternal(Memory.TIMA, 0);
        }

        this.memory[address - 0xFF04] = value;
    }


    private void writeInternal(int address, int value) {
        this.memory[address - 0xFF04] = value;
    }

}
