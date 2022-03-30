package de.pottgames.anemulator.memory;

import de.pottgames.anemulator.input.JoypadStateChangeListener;

public interface MemoryBankController extends JoypadStateChangeListener {
    int[] BOOT_ROM = { 0x31, 0xFE, 0xFF, 0xAF, 0x21, 0xFF, 0x9F, 0x32, 0xCB, 0x7C, 0x20, 0xFB, 0x21, 0x26, 0xFF, 0x0E, 0x11, 0x3E, 0x80, 0x32, 0xE2, 0x0C, 0x3E,
            0xF3, 0xE2, 0x32, 0x3E, 0x77, 0x77, 0x3E, 0xFC, 0xE0, 0x47, 0x11, 0x04, 0x01, 0x21, 0x10, 0x80, 0x1A, 0xCD, 0x95, 0x00, 0xCD, 0x96, 0x00, 0x13,
            0x7B, 0xFE, 0x34, 0x20, 0xF3, 0x11, 0xD8, 0x00, 0x06, 0x08, 0x1A, 0x13, 0x22, 0x23, 0x05, 0x20, 0xF9, 0x3E, 0x19, 0xEA, 0x10, 0x99, 0x21, 0x2F,
            0x99, 0x0E, 0x0C, 0x3D, 0x28, 0x08, 0x32, 0x0D, 0x20, 0xF9, 0x2E, 0x0F, 0x18, 0xF3, 0x67, 0x3E, 0x64, 0x57, 0xE0, 0x42, 0x3E, 0x91, 0xE0, 0x40,
            0x04, 0x1E, 0x02, 0x0E, 0x0C, 0xF0, 0x44, 0xFE, 0x90, 0x20, 0xFA, 0x0D, 0x20, 0xF7, 0x1D, 0x20, 0xF2, 0x0E, 0x13, 0x24, 0x7C, 0x1E, 0x83, 0xFE,
            0x62, 0x28, 0x06, 0x1E, 0xC1, 0xFE, 0x64, 0x20, 0x06, 0x7B, 0xE2, 0x0C, 0x3E, 0x87, 0xE2, 0xF0, 0x42, 0x90, 0xE0, 0x42, 0x15, 0x20, 0xD2, 0x05,
            0x20, 0x4F, 0x16, 0x20, 0x18, 0xCB, 0x4F, 0x06, 0x04, 0xC5, 0xCB, 0x11, 0x17, 0xC1, 0xCB, 0x11, 0x17, 0x05, 0x20, 0xF5, 0x22, 0x23, 0x22, 0x23,
            0xC9, 0xCE, 0xED, 0x66, 0x66, 0xCC, 0x0D, 0x00, 0x0B, 0x03, 0x73, 0x00, 0x83, 0x00, 0x0C, 0x00, 0x0D, 0x00, 0x08, 0x11, 0x1F, 0x88, 0x89, 0x00,
            0x0E, 0xDC, 0xCC, 0x6E, 0xE6, 0xDD, 0xDD, 0xD9, 0x99, 0xBB, 0xBB, 0x67, 0x63, 0x6E, 0x0E, 0xEC, 0xCC, 0xDD, 0xDC, 0x99, 0x9F, 0xBB, 0xB9, 0x33,
            0x3E, 0x3C, 0x42, 0xB9, 0xA5, 0xB9, 0xA5, 0x42, 0x3C, 0x21, 0x04, 0x01, 0x11, 0xA8, 0x00, 0x1A, 0x13, 0xBE, 0x20, 0xFE, 0x23, 0x7D, 0xFE, 0x34,
            0x20, 0xF5, 0x06, 0x19, 0x78, 0x86, 0x23, 0x05, 0x20, 0xFB, 0x86, 0x20, 0xFE, 0x3E, 0x01, 0xE0, 0x50 };

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

    int NR21 = 0xFF16;

    int NR22 = 0xFF17;

    int NR23 = 0xFF18;

    int NR24 = 0xFF19;

    int NR30 = 0xFF1A;

    int NR31 = 0xFF1B;

    int NR32 = 0xFF1C;

    int NR33 = 0xFF1D;

    int NR34 = 0xFF1E;

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


    int read8Bit(int address);


    int read16Bit(int address);


    void write(int address, int value);


    void incDiv();


    void setBooted();


    boolean isBooted();


    String getGameName();


    MemoryStepResult step();


    default boolean isBitSet(int address, int bitnum) {
        return (this.read8Bit(address) & 1 << bitnum) > 0;
    }


    default void setBit(int address, int bitnum, boolean value) {
        assert address >= 0x0000 && address <= 0xFFFF;
        assert bitnum >= 0 && bitnum <= 7;

        int data = this.read8Bit(address);
        if (value) {
            data |= 1 << bitnum;
        } else {
            data &= ~(1 << bitnum);
        }
        this.write(address, data);
    }


    public static class MemoryStepResult {
        public boolean timerInterruptRequest;


        public void reset() {
            this.timerInterruptRequest = false;
        }
    }

}
