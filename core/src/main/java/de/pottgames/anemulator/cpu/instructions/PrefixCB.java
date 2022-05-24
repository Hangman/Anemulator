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

import com.badlogic.gdx.utils.IntMap;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.extendedinstructions.*;
import de.pottgames.anemulator.error.UnsupportedFeatureException;
import de.pottgames.anemulator.memory.Memory;

public class PrefixCB extends Instruction {
    private IntMap<Instruction> extendedInstructions = new IntMap<>();
    public static int           lastInstructionOpcode;


    public PrefixCB(Register register, Memory memory) {
        super(register, memory);
        this.initExtendedInstructions();
    }


    private void initExtendedInstructions() {
        this.extendedInstructions.put(0x00, new RlcB(this.register, this.memory));
        this.extendedInstructions.put(0x01, new RlcC(this.register, this.memory));
        this.extendedInstructions.put(0x02, new RlcD(this.register, this.memory));
        this.extendedInstructions.put(0x03, new RlcE(this.register, this.memory));
        this.extendedInstructions.put(0x04, new RlcH(this.register, this.memory));
        this.extendedInstructions.put(0x05, new RlcL(this.register, this.memory));
        this.extendedInstructions.put(0x06, new Rlc_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x07, new de.pottgames.anemulator.cpu.extendedinstructions.RlcA(this.register, this.memory));
        this.extendedInstructions.put(0x08, new RrcB(this.register, this.memory));
        this.extendedInstructions.put(0x09, new RrcC(this.register, this.memory));
        this.extendedInstructions.put(0x0A, new RrcD(this.register, this.memory));
        this.extendedInstructions.put(0x0B, new RrcE(this.register, this.memory));
        this.extendedInstructions.put(0x0C, new RrcH(this.register, this.memory));
        this.extendedInstructions.put(0x0D, new RrcL(this.register, this.memory));
        this.extendedInstructions.put(0x0E, new Rrc_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x0F, new RrcA(this.register, this.memory));

        this.extendedInstructions.put(0x10, new RlB(this.register, this.memory));
        this.extendedInstructions.put(0x11, new RlC(this.register, this.memory));
        this.extendedInstructions.put(0x12, new RlD(this.register, this.memory));
        this.extendedInstructions.put(0x13, new RlE(this.register, this.memory));
        this.extendedInstructions.put(0x14, new RlH(this.register, this.memory));
        this.extendedInstructions.put(0x15, new RlL(this.register, this.memory));
        this.extendedInstructions.put(0x16, new Rl_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x17, new RlA(this.register, this.memory));
        this.extendedInstructions.put(0x18, new RrB(this.register, this.memory));
        this.extendedInstructions.put(0x19, new RrC(this.register, this.memory));
        this.extendedInstructions.put(0x1A, new RrD(this.register, this.memory));
        this.extendedInstructions.put(0x1B, new RrE(this.register, this.memory));
        this.extendedInstructions.put(0x1C, new RrH(this.register, this.memory));
        this.extendedInstructions.put(0x1D, new RrL(this.register, this.memory));
        this.extendedInstructions.put(0x1E, new Rr_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x1F, new RrA(this.register, this.memory));

        this.extendedInstructions.put(0x20, new SlaB(this.register, this.memory));
        this.extendedInstructions.put(0x21, new SlaC(this.register, this.memory));
        this.extendedInstructions.put(0x22, new SlaD(this.register, this.memory));
        this.extendedInstructions.put(0x23, new SlaE(this.register, this.memory));
        this.extendedInstructions.put(0x24, new SlaH(this.register, this.memory));
        this.extendedInstructions.put(0x25, new SlaL(this.register, this.memory));
        this.extendedInstructions.put(0x26, new Sla_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x27, new SlaA(this.register, this.memory));
        this.extendedInstructions.put(0x28, new SraB(this.register, this.memory));
        this.extendedInstructions.put(0x29, new SraC(this.register, this.memory));
        this.extendedInstructions.put(0x2A, new SraD(this.register, this.memory));
        this.extendedInstructions.put(0x2B, new SraE(this.register, this.memory));
        this.extendedInstructions.put(0x2C, new SraH(this.register, this.memory));
        this.extendedInstructions.put(0x2D, new SraL(this.register, this.memory));
        this.extendedInstructions.put(0x2E, new Sra_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x2F, new SraA(this.register, this.memory));

        this.extendedInstructions.put(0x30, new SwapB(this.register, this.memory));
        this.extendedInstructions.put(0x31, new SwapC(this.register, this.memory));
        this.extendedInstructions.put(0x32, new SwapD(this.register, this.memory));
        this.extendedInstructions.put(0x33, new SwapE(this.register, this.memory));
        this.extendedInstructions.put(0x34, new SwapH(this.register, this.memory));
        this.extendedInstructions.put(0x35, new SwapL(this.register, this.memory));
        this.extendedInstructions.put(0x36, new Swap_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x37, new SwapA(this.register, this.memory));
        this.extendedInstructions.put(0x38, new SrlB(this.register, this.memory));
        this.extendedInstructions.put(0x39, new SrlC(this.register, this.memory));
        this.extendedInstructions.put(0x3A, new SrlD(this.register, this.memory));
        this.extendedInstructions.put(0x3B, new SrlE(this.register, this.memory));
        this.extendedInstructions.put(0x3C, new SrlH(this.register, this.memory));
        this.extendedInstructions.put(0x3D, new SrlL(this.register, this.memory));
        this.extendedInstructions.put(0x3E, new Srl_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x3F, new SrlA(this.register, this.memory));

        this.extendedInstructions.put(0x40, new Bit0B(this.register, this.memory));
        this.extendedInstructions.put(0x41, new Bit0C(this.register, this.memory));
        this.extendedInstructions.put(0x42, new Bit0D(this.register, this.memory));
        this.extendedInstructions.put(0x43, new Bit0E(this.register, this.memory));
        this.extendedInstructions.put(0x44, new Bit0H(this.register, this.memory));
        this.extendedInstructions.put(0x45, new Bit0L(this.register, this.memory));
        this.extendedInstructions.put(0x46, new Bit0_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x47, new Bit0A(this.register, this.memory));
        this.extendedInstructions.put(0x48, new Bit1B(this.register, this.memory));
        this.extendedInstructions.put(0x49, new Bit1C(this.register, this.memory));
        this.extendedInstructions.put(0x4A, new Bit1D(this.register, this.memory));
        this.extendedInstructions.put(0x4B, new Bit1E(this.register, this.memory));
        this.extendedInstructions.put(0x4C, new Bit1H(this.register, this.memory));
        this.extendedInstructions.put(0x4D, new Bit1L(this.register, this.memory));
        this.extendedInstructions.put(0x4E, new Bit1_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x4F, new Bit1A(this.register, this.memory));

        this.extendedInstructions.put(0x50, new Bit2B(this.register, this.memory));
        this.extendedInstructions.put(0x51, new Bit2C(this.register, this.memory));
        this.extendedInstructions.put(0x52, new Bit2D(this.register, this.memory));
        this.extendedInstructions.put(0x53, new Bit2E(this.register, this.memory));
        this.extendedInstructions.put(0x54, new Bit2H(this.register, this.memory));
        this.extendedInstructions.put(0x55, new Bit2L(this.register, this.memory));
        this.extendedInstructions.put(0x56, new Bit2_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x57, new Bit2A(this.register, this.memory));
        this.extendedInstructions.put(0x58, new Bit3B(this.register, this.memory));
        this.extendedInstructions.put(0x59, new Bit3C(this.register, this.memory));
        this.extendedInstructions.put(0x5A, new Bit3D(this.register, this.memory));
        this.extendedInstructions.put(0x5B, new Bit3E(this.register, this.memory));
        this.extendedInstructions.put(0x5C, new Bit3H(this.register, this.memory));
        this.extendedInstructions.put(0x5D, new Bit3L(this.register, this.memory));
        this.extendedInstructions.put(0x5E, new Bit3_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x5F, new Bit3A(this.register, this.memory));

        this.extendedInstructions.put(0x60, new Bit4B(this.register, this.memory));
        this.extendedInstructions.put(0x61, new Bit4C(this.register, this.memory));
        this.extendedInstructions.put(0x62, new Bit4D(this.register, this.memory));
        this.extendedInstructions.put(0x63, new Bit4E(this.register, this.memory));
        this.extendedInstructions.put(0x64, new Bit4H(this.register, this.memory));
        this.extendedInstructions.put(0x65, new Bit4L(this.register, this.memory));
        this.extendedInstructions.put(0x66, new Bit4_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x67, new Bit4A(this.register, this.memory));
        this.extendedInstructions.put(0x68, new Bit5B(this.register, this.memory));
        this.extendedInstructions.put(0x69, new Bit5C(this.register, this.memory));
        this.extendedInstructions.put(0x6A, new Bit5D(this.register, this.memory));
        this.extendedInstructions.put(0x6B, new Bit5E(this.register, this.memory));
        this.extendedInstructions.put(0x6C, new Bit5H(this.register, this.memory));
        this.extendedInstructions.put(0x6D, new Bit5L(this.register, this.memory));
        this.extendedInstructions.put(0x6E, new Bit5_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x6F, new Bit5A(this.register, this.memory));

        this.extendedInstructions.put(0x70, new Bit6B(this.register, this.memory));
        this.extendedInstructions.put(0x71, new Bit6C(this.register, this.memory));
        this.extendedInstructions.put(0x72, new Bit6D(this.register, this.memory));
        this.extendedInstructions.put(0x73, new Bit6E(this.register, this.memory));
        this.extendedInstructions.put(0x74, new Bit6H(this.register, this.memory));
        this.extendedInstructions.put(0x75, new Bit6L(this.register, this.memory));
        this.extendedInstructions.put(0x76, new Bit6_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x77, new Bit6A(this.register, this.memory));
        this.extendedInstructions.put(0x78, new Bit7B(this.register, this.memory));
        this.extendedInstructions.put(0x79, new Bit7C(this.register, this.memory));
        this.extendedInstructions.put(0x7A, new Bit7D(this.register, this.memory));
        this.extendedInstructions.put(0x7B, new Bit7E(this.register, this.memory));
        this.extendedInstructions.put(0x7C, new Bit7H(this.register, this.memory));
        this.extendedInstructions.put(0x7D, new Bit7L(this.register, this.memory));
        this.extendedInstructions.put(0x7E, new Bit7_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x7F, new Bit7A(this.register, this.memory));

        this.extendedInstructions.put(0x80, new Res0B(this.register, this.memory));
        this.extendedInstructions.put(0x81, new Res0C(this.register, this.memory));
        this.extendedInstructions.put(0x82, new Res0D(this.register, this.memory));
        this.extendedInstructions.put(0x83, new Res0E(this.register, this.memory));
        this.extendedInstructions.put(0x84, new Res0H(this.register, this.memory));
        this.extendedInstructions.put(0x85, new Res0L(this.register, this.memory));
        this.extendedInstructions.put(0x86, new Res0_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x87, new Res0A(this.register, this.memory));
        this.extendedInstructions.put(0x88, new Res1B(this.register, this.memory));
        this.extendedInstructions.put(0x89, new Res1C(this.register, this.memory));
        this.extendedInstructions.put(0x8A, new Res1D(this.register, this.memory));
        this.extendedInstructions.put(0x8B, new Res1E(this.register, this.memory));
        this.extendedInstructions.put(0x8C, new Res1H(this.register, this.memory));
        this.extendedInstructions.put(0x8D, new Res1L(this.register, this.memory));
        this.extendedInstructions.put(0x8E, new Res1_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x8F, new Res1A(this.register, this.memory));

        this.extendedInstructions.put(0x90, new Res2B(this.register, this.memory));
        this.extendedInstructions.put(0x91, new Res2C(this.register, this.memory));
        this.extendedInstructions.put(0x92, new Res2D(this.register, this.memory));
        this.extendedInstructions.put(0x93, new Res2E(this.register, this.memory));
        this.extendedInstructions.put(0x94, new Res2H(this.register, this.memory));
        this.extendedInstructions.put(0x95, new Res2L(this.register, this.memory));
        this.extendedInstructions.put(0x96, new Res2_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x97, new Res2A(this.register, this.memory));
        this.extendedInstructions.put(0x98, new Res3B(this.register, this.memory));
        this.extendedInstructions.put(0x99, new Res3C(this.register, this.memory));
        this.extendedInstructions.put(0x9A, new Res3D(this.register, this.memory));
        this.extendedInstructions.put(0x9B, new Res3E(this.register, this.memory));
        this.extendedInstructions.put(0x9C, new Res3H(this.register, this.memory));
        this.extendedInstructions.put(0x9D, new Res3L(this.register, this.memory));
        this.extendedInstructions.put(0x9E, new Res3_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x9F, new Res3A(this.register, this.memory));

        this.extendedInstructions.put(0xA0, new Res4B(this.register, this.memory));
        this.extendedInstructions.put(0xA1, new Res4C(this.register, this.memory));
        this.extendedInstructions.put(0xA2, new Res4D(this.register, this.memory));
        this.extendedInstructions.put(0xA3, new Res4E(this.register, this.memory));
        this.extendedInstructions.put(0xA4, new Res4H(this.register, this.memory));
        this.extendedInstructions.put(0xA5, new Res4L(this.register, this.memory));
        this.extendedInstructions.put(0xA6, new Res4_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xA7, new Res4A(this.register, this.memory));
        this.extendedInstructions.put(0xA8, new Res5B(this.register, this.memory));
        this.extendedInstructions.put(0xA9, new Res5C(this.register, this.memory));
        this.extendedInstructions.put(0xAA, new Res5D(this.register, this.memory));
        this.extendedInstructions.put(0xAB, new Res5E(this.register, this.memory));
        this.extendedInstructions.put(0xAC, new Res5H(this.register, this.memory));
        this.extendedInstructions.put(0xAD, new Res5L(this.register, this.memory));
        this.extendedInstructions.put(0xAE, new Res5_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xAF, new Res5A(this.register, this.memory));

        this.extendedInstructions.put(0xB0, new Res6B(this.register, this.memory));
        this.extendedInstructions.put(0xB1, new Res6C(this.register, this.memory));
        this.extendedInstructions.put(0xB2, new Res6D(this.register, this.memory));
        this.extendedInstructions.put(0xB3, new Res6E(this.register, this.memory));
        this.extendedInstructions.put(0xB4, new Res6H(this.register, this.memory));
        this.extendedInstructions.put(0xB5, new Res6L(this.register, this.memory));
        this.extendedInstructions.put(0xB6, new Res6_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xB7, new Res6A(this.register, this.memory));
        this.extendedInstructions.put(0xB8, new Res7B(this.register, this.memory));
        this.extendedInstructions.put(0xB9, new Res7C(this.register, this.memory));
        this.extendedInstructions.put(0xBA, new Res7D(this.register, this.memory));
        this.extendedInstructions.put(0xBB, new Res7E(this.register, this.memory));
        this.extendedInstructions.put(0xBC, new Res7H(this.register, this.memory));
        this.extendedInstructions.put(0xBD, new Res7L(this.register, this.memory));
        this.extendedInstructions.put(0xBE, new Res7_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xBF, new Res7A(this.register, this.memory));

        this.extendedInstructions.put(0xC0, new Set0B(this.register, this.memory));
        this.extendedInstructions.put(0xC1, new Set0C(this.register, this.memory));
        this.extendedInstructions.put(0xC2, new Set0D(this.register, this.memory));
        this.extendedInstructions.put(0xC3, new Set0E(this.register, this.memory));
        this.extendedInstructions.put(0xC4, new Set0H(this.register, this.memory));
        this.extendedInstructions.put(0xC5, new Set0L(this.register, this.memory));
        this.extendedInstructions.put(0xC6, new Set0_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xC7, new Set0A(this.register, this.memory));
        this.extendedInstructions.put(0xC8, new Set1B(this.register, this.memory));
        this.extendedInstructions.put(0xC9, new Set1C(this.register, this.memory));
        this.extendedInstructions.put(0xCA, new Set1D(this.register, this.memory));
        this.extendedInstructions.put(0xCB, new Set1E(this.register, this.memory));
        this.extendedInstructions.put(0xCC, new Set1H(this.register, this.memory));
        this.extendedInstructions.put(0xCD, new Set1L(this.register, this.memory));
        this.extendedInstructions.put(0xCE, new Set1_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xCF, new Set1A(this.register, this.memory));

        this.extendedInstructions.put(0xD0, new Set2B(this.register, this.memory));
        this.extendedInstructions.put(0xD1, new Set2C(this.register, this.memory));
        this.extendedInstructions.put(0xD2, new Set2D(this.register, this.memory));
        this.extendedInstructions.put(0xD3, new Set2E(this.register, this.memory));
        this.extendedInstructions.put(0xD4, new Set2H(this.register, this.memory));
        this.extendedInstructions.put(0xD5, new Set2L(this.register, this.memory));
        this.extendedInstructions.put(0xD6, new Set2_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xD7, new Set2A(this.register, this.memory));
        this.extendedInstructions.put(0xD8, new Set3B(this.register, this.memory));
        this.extendedInstructions.put(0xD9, new Set3C(this.register, this.memory));
        this.extendedInstructions.put(0xDA, new Set3D(this.register, this.memory));
        this.extendedInstructions.put(0xDB, new Set3E(this.register, this.memory));
        this.extendedInstructions.put(0xDC, new Set3H(this.register, this.memory));
        this.extendedInstructions.put(0xDD, new Set3L(this.register, this.memory));
        this.extendedInstructions.put(0xDE, new Set3_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xDF, new Set3A(this.register, this.memory));

        this.extendedInstructions.put(0xE0, new Set4B(this.register, this.memory));
        this.extendedInstructions.put(0xE1, new Set4C(this.register, this.memory));
        this.extendedInstructions.put(0xE2, new Set4D(this.register, this.memory));
        this.extendedInstructions.put(0xE3, new Set4E(this.register, this.memory));
        this.extendedInstructions.put(0xE4, new Set4H(this.register, this.memory));
        this.extendedInstructions.put(0xE5, new Set4L(this.register, this.memory));
        this.extendedInstructions.put(0xE6, new Set4_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xE7, new Set4A(this.register, this.memory));
        this.extendedInstructions.put(0xE8, new Set5B(this.register, this.memory));
        this.extendedInstructions.put(0xE9, new Set5C(this.register, this.memory));
        this.extendedInstructions.put(0xEA, new Set5D(this.register, this.memory));
        this.extendedInstructions.put(0xEB, new Set5E(this.register, this.memory));
        this.extendedInstructions.put(0xEC, new Set5H(this.register, this.memory));
        this.extendedInstructions.put(0xED, new Set5L(this.register, this.memory));
        this.extendedInstructions.put(0xEE, new Set5_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xEF, new Set5A(this.register, this.memory));

        this.extendedInstructions.put(0xF0, new Set6B(this.register, this.memory));
        this.extendedInstructions.put(0xF1, new Set6C(this.register, this.memory));
        this.extendedInstructions.put(0xF2, new Set6D(this.register, this.memory));
        this.extendedInstructions.put(0xF3, new Set6E(this.register, this.memory));
        this.extendedInstructions.put(0xF4, new Set6H(this.register, this.memory));
        this.extendedInstructions.put(0xF5, new Set6L(this.register, this.memory));
        this.extendedInstructions.put(0xF6, new Set6_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xF7, new Set6A(this.register, this.memory));
        this.extendedInstructions.put(0xF8, new Set7B(this.register, this.memory));
        this.extendedInstructions.put(0xF9, new Set7C(this.register, this.memory));
        this.extendedInstructions.put(0xFA, new Set7D(this.register, this.memory));
        this.extendedInstructions.put(0xFB, new Set7E(this.register, this.memory));
        this.extendedInstructions.put(0xFC, new Set7H(this.register, this.memory));
        this.extendedInstructions.put(0xFD, new Set7L(this.register, this.memory));
        this.extendedInstructions.put(0xFE, new Set7_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xFF, new Set7A(this.register, this.memory));
    }


    @Override
    public int run() {
        final int opCode = this.memory.readByte(this.register.getPc());
        this.register.setPc(this.register.getPc() + 1);
        final Instruction instruction = this.extendedInstructions.get(opCode);

        if (instruction == null) {
            throw new UnsupportedFeatureException("Unsupported extended opCode: " + Integer.toHexString(opCode));
        }

        final int cycles = instruction.run();

        return cycles;
    }


    @Override
    public String toString() {
        return "PrefixCB";
    }


    @Override
    public int getLength() {
        return 1;
    }


    @Override
    public boolean isPrefix() {
        return true;
    }

}
