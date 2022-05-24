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

public interface Memory {

    /**
     * Joypad State register<br>
     * <br>
     * Bit 7 = unused<br>
     * Bit 6 = unused<br>
     * Bit 5 = Select action buttons : 0=selected, 1=not selected<br>
     * Bit 4 = Select direction buttons : 0=selected, 1=not selected<br>
     * Bit 3 = Down or Start : 0=pressed, 1=not pressed<br>
     * Bit 2 = Up or Select : 0=pressed, 1=not pressed<br>
     * Bit 1 = Left or B : 0=pressed, 1=not pressed<br>
     * Bit 0 = Right or A : 0=pressed, 1=not pressed<br>
     */
    int JOYPAD = 0xFF00;

    /**
     * Serial transfer data register<br>
     * Before a transfer, it holds the next byte that will go out.
     *
     * During a transfer, it has a blend of the outgoing and incoming bytes. Each cycle, the leftmost bit is shifted out (and over the wire) and the incoming
     * bit is shifted in from the other side.
     */
    int SB = 0xFF01;

    /**
     * Serial transfer control register<br>
     * Bit 7 = Transfer Start Flag (0=No transfer is in progress or requested, 1=Transfer in progress, or request<br>
     * Bit 1 = Clock Speed (0=Normal, 1=Fast) ** CGB Mode Only **<br>
     * Bit 0 = Shift Clock (0=External Clock, 1=Internal Clock)<br>
     */
    int SC = 0xFF02;

    /**
     * This register is incremented at a rate of 16384Hz (~16779Hz on SGB).<br>
     * Writing any value to this register resets it to $00.<br>
     * Additionally, this register is reset when executing the stop instruction, and only begins ticking again once stop mode ends.
     */
    int DIV = 0xFF04;

    /**
     * Timer Counter register<br>
     * <br>
     * This timer is incremented at the clock frequency specified by the TAC register ($FF07).<br>
     * When the value overflows (exceeds $FF) it is reset to the value specified in TMA (FF06) and an interrupt is requested.
     */
    int TIMA = 0xFF05;

    /**
     * Timer Modulo register<br>
     * <br>
     * When TIMA overflows, it is reset to the value in this register and an interrupt is requested. Example of use: if TMA is set to $FF, an interrupt is
     * requested at the clock frequency selected in TAC (because every increment is an overflow). However, if TMA is set to $FE, an interrupt is only requested
     * every two increments, which effectively divides the selected clock by two. Setting TMA to $FD would divide the clock by three, and so on.<br>
     * <br>
     * If a TMA write is executed on the same cycle as the content of TMA is transferred to TIMA due to a timer overflow, the old value is transferred to TIMA.
     */
    int TMA = 0xFF06;

    /**
     * Timer Control register<br>
     * <br>
     * Bit 2 = Timer Enable : 0=off, 1=on<br>
     * Bit 1, 0 = Input Clock Select :<br>
     * 00 = CPU Clock / 1024<br>
     * 01 = CPU CLock / 16<br>
     * 10 = CPU Clock / 64<br>
     * 11 = CPU Clock / 256<br>
     */
    int TAC = 0xFF07;

    /**
     * Interrupt Flag register<br>
     * Bit 4 = Joypad : 0=off, 1=request<br>
     * Bit 3 = Serial : 0=off, 1=request<br>
     * Bit 2 = Timer : 0=off, 1=request<br>
     * Bit 1 = LCD_STAT : 0=off, 1=request<br>
     * Bit 0 = VBlank : 0=off, 1=request<br>
     */
    int IF = 0xFF0F;

    int NR10 = 0xFF10;

    int NR11 = 0xFF11;

    int NR12 = 0xFF12;

    int NR13 = 0xFF13;

    int NR14 = 0xFF14;

    int NR20 = 0xFF15;

    int NR21 = 0xFF16;

    int NR22 = 0xFF17;

    int NR23 = 0xFF18;

    int NR24 = 0xFF19;

    int NR30 = 0xFF1A;

    int NR31 = 0xFF1B;

    int NR32 = 0xFF1C;

    int NR33 = 0xFF1D;

    int NR34 = 0xFF1E;

    int NR40 = 0xFF1F;

    int NR41 = 0xFF20;

    int NR42 = 0xFF21;

    int NR43 = 0xFF22;

    int NR44 = 0xFF23;

    int NR50 = 0xFF24;

    int NR51 = 0xFF25;

    int NR52 = 0xFF26;

    /**
     * LCD Control register<br>
     * <br>
     * Bit 7 = LCD and GPU enable : 0=off, 1=on<br>
     * Bit 6 = Window tile map area : 0=0x9800-0x9BFF, 1=0x9C00-0x9FFF<br>
     * Bit 5 = Window enable : 0=off, 1=on<br>
     * Bit 4 = Background and Window tile data area : 0=0x8800-0x97FF, 1=0x8000-0x8FFF<br>
     * Bit 3 = Background tile map area : 0=0x9800-0x9BFF, 1=0x9C00-0x9FFF<br>
     * Bit 2 = Object size : 0=8x8, 1=8x16<br>
     * Bit 1 = Object enable: 0=off, 1=on<br>
     * Bit 0 = Background and Window enable/priority : 0=off, 1=on<br>
     */
    int LCDC = 0xFF40;

    /**
     * LCD Status register<br>
     * Bit 7 = unused<br>
     * Bit 6 = LY interrupt source : 0=off, 1=on<br>
     * Bit 5 = Mode 2 OAM interrupt source : 0=off, 1=on<br>
     * Bit 4 = Mode 1 VBlank interrupt source : 0=off, 1=on<br>
     * Bit 3 = Mode 0 HBlank interrupt source : 0=off, 1=on<br>
     * Bit 2 = LY Flag : 0=different, 1=equal<br>
     * Bit 1, 0 = Mode Flag : 00=HBlank, 01=VBlank, 10=OAMSearch, 11=PixelTransfer<br>
     */
    int LCD_STAT = 0xFF41;

    /**
     * Background scroll Y register<br>
     */
    int SCROLL_Y = 0xFF42;

    /**
     * Background scroll X register<br>
     */
    int SCROLL_X = 0xFF43;

    /**
     * LCD Y line register<br>
     * LY indicates the current horizontal line, which might be about to be drawn, being drawn, or just been drawn. LY can hold any value from 0 to 153, with
     * values from 144 to 153 indicating the VBlank period.
     */
    int LCD_LY = 0xFF44;

    /**
     * LCD Compare Y line register<br>
     * The Game Boy permanently compares the value of the LYC and LY registers. When both values are identical, the “LYC=LY” flag in the STAT register is set,
     * and (if enabled) a STAT interrupt is requested.
     */
    int LCD_LYC = 0xFF45;

    /**
     * Writing to this register launches a DMA transfer from ROM or RAM to OAM (Object Attribute Memory). The written value specifies the transfer source
     * address divided by $100, that is, source and destination are:<br>
     * Source: $XX00-$XX9F ;XX = $00 to $DF Destination: $FE00-$FE9F
     */
    int DMA = 0xFF46;

    /**
     * BG Palette Data register<br>
     * <br>
     * Bit 7, 6 = Color for index 3<br>
     * Bit 5, 4 = Color for index 2<br>
     * Bit 3, 2 = Color for index 1<br>
     * Bit 1, 0 = Color for index 0<br>
     */
    int BGP = 0xFF47;

    int OBP0 = 0xFF48;

    int OBP1 = 0xFF49;

    /**
     * Window Y Position
     */
    int WY = 0xFF4A;

    /**
     * Window X Position + 7
     */
    int WX = 0xFF4B;

    int KEY1 = 0xFF4D;

    int VBK = 0xFF4F;

    /**
     * Set to non-zero to disable Boot Rom
     */
    int DISABLE_BOOT_ROM = 0xFF50;

    int HDMA1 = 0xFF51;

    int HDMA2 = 0xFF52;

    int HDMA3 = 0xFF53;

    int HDMA4 = 0xFF54;

    int HDMA5 = 0xFF55;

    int RP = 0xFF56;

    int BCPS = 0xFF68;

    int BCPD = 0xFF69;

    int OCPS = 0xFF6A;

    int OCPD = 0xFF6B;

    int SVBK = 0xFF70;

    /**
     * Interrupt Enable register<br>
     * <br>
     * Bit 4 = Joypad : 0=disabled, 1=enabled<br>
     * Bit 3 = Serial : 0=disabled, 1=enabled<br>
     * Bit 2 = Timer : 0=disabled, 1=enabled<br>
     * Bit 1 = LCD STAT : 0=disabled, 1=enabled<br>
     * Bit 0 = VBlank : 0=disabled, 1=enabled<br>
     */
    int IE = 0xFFFF;


    boolean acceptsAddress(int address);


    int readByte(int address);


    default int readWord(int address) {
        return this.readByte(address) | this.readByte(address + 1) << 8;
    }


    void writeByte(int address, int value);


    default boolean isBitSet(int address, int bitnum) {
        return (this.readByte(address) & 1 << bitnum) > 0;
    }


    default void setBit(int address, int bitnum, boolean value) {
        assert address >= 0x0000 && address <= 0xFFFF;
        assert bitnum >= 0 && bitnum <= 7;

        int data = this.readByte(address);
        if (value) {
            data |= 1 << bitnum;
        } else {
            data &= ~(1 << bitnum);
        }
        this.writeByte(address, data);
    }

}
