package de.pottgames.anemulator.memory;

import de.pottgames.anemulator.input.JoypadStateChangeListener;

public interface MemoryBankController extends JoypadStateChangeListener {

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
     * Interrupt Flag register<br>
     * TODO: document
     */
    int IF = 0xFF0F;

    /**
     * Interrupt Enable register<br>
     * TODO: document
     */
    int IE = 0xFFFF;


    int read8Bit(int address);


    int read16Bit(int address);


    void write(int address, int value);


    void incDiv();


    String getGameName();


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

}
