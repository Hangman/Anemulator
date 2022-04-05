package de.pottgames.anemulator.cpu;

import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Mbc;
import de.pottgames.anemulator.memory.Memory;

public class Booter {
    private final Memory   memory;
    private final Mbc      mbc;
    private final Register register;


    public Booter(Register register, Memory memory, Mbc mbc) {
        this.register = register;
        this.memory = memory;
        this.mbc = mbc;
    }


    public void boot() {
        this.register.set(RegisterId.A, 0x01);
        this.register.setFlag(FlagId.Z, true);
        this.register.setFlag(FlagId.N, false);
        // TODO: CALC HEADER CHECKSUM
        // set H flag if header checksum is not 0x00
        // set C flag if header checksum is not 0x00
        this.register.setFlag(FlagId.H, true);
        this.register.setFlag(FlagId.C, true);
        //
        this.register.set(RegisterId.B, 0x00);
        this.register.set(RegisterId.C, 0x13);
        this.register.set(RegisterId.D, 0x00);
        this.register.set(RegisterId.E, 0xD8);
        this.register.set(RegisterId.H, 0x01);
        this.register.set(RegisterId.L, 0x4D);
        this.register.setPc(0x100);
        this.register.setSp(0xFFFE);

        this.memory.writeByte(Memory.JOYPAD, 0xCF);
        this.memory.writeByte(Memory.SB, 0x00);
        this.memory.writeByte(Memory.SC, 0x7E);
        this.memory.writeByte(Memory.DIV, 0x18);
        this.memory.writeByte(Memory.TIMA, 0x00);
        this.memory.writeByte(Memory.TMA, 0x00);
        this.memory.writeByte(Memory.TAC, 0xF8);
        this.memory.writeByte(Memory.IF, 0xE1);
        this.memory.writeByte(Memory.NR10, 0x80);
        this.memory.writeByte(Memory.NR11, 0xBF);
        this.memory.writeByte(Memory.NR12, 0xF3);
        this.memory.writeByte(Memory.NR13, 0xFF);
        this.memory.writeByte(Memory.NR14, 0xBF);
        this.memory.writeByte(Memory.NR21, 0x3F);
        this.memory.writeByte(Memory.NR22, 0x00);
        this.memory.writeByte(Memory.NR23, 0xFF);
        this.memory.writeByte(Memory.NR24, 0xBF);
        this.memory.writeByte(Memory.NR30, 0x7F);
        this.memory.writeByte(Memory.NR31, 0xFF);
        this.memory.writeByte(Memory.NR32, 0x9F);
        this.memory.writeByte(Memory.NR33, 0xFF);
        this.memory.writeByte(Memory.NR34, 0xBF);
        this.memory.writeByte(Memory.NR41, 0xFF);
        this.memory.writeByte(Memory.NR42, 0x00);
        this.memory.writeByte(Memory.NR43, 0x00);
        this.memory.writeByte(Memory.NR44, 0xBF);
        this.memory.writeByte(Memory.NR50, 0x77);
        this.memory.writeByte(Memory.NR51, 0xF3);
        this.memory.writeByte(Memory.NR52, 0xF1);
        this.memory.writeByte(Memory.LCDC, 0x91);
        this.memory.writeByte(Memory.LCD_STAT, 0x81);
        this.memory.writeByte(Memory.SCROLL_Y, 0x00);
        this.memory.writeByte(Memory.SCROLL_X, 0x00);
        this.memory.writeByte(Memory.LCD_LY, 0x91);
        this.memory.writeByte(Memory.LCD_LYC, 0x00);
        // DMA IS INITIALIZED IN Dma
        this.memory.writeByte(Memory.BGP, 0xFC);
        this.memory.writeByte(Memory.OBP0, 0x00);
        this.memory.writeByte(Memory.OBP1, 0x00);
        this.memory.writeByte(Memory.WY, 0x00);
        this.memory.writeByte(Memory.WX, 0x00);
        this.memory.writeByte(Memory.KEY1, 0xFF);
        this.memory.writeByte(Memory.VBK, 0xFF);
        this.memory.writeByte(Memory.HDMA1, 0xFF);
        this.memory.writeByte(Memory.HDMA2, 0xFF);
        this.memory.writeByte(Memory.HDMA3, 0xFF);
        this.memory.writeByte(Memory.HDMA4, 0xFF);
        this.memory.writeByte(Memory.HDMA5, 0xFF);
        this.memory.writeByte(Memory.RP, 0xFF);
        this.memory.writeByte(Memory.BCPS, 0xFF);
        this.memory.writeByte(Memory.BCPD, 0xFF);
        this.memory.writeByte(Memory.OCPS, 0xFF);
        this.memory.writeByte(Memory.OCPD, 0xFF);
        this.memory.writeByte(Memory.SVBK, 0xFF);
        this.memory.writeByte(Memory.IE, 0x00);

        this.mbc.setBooted();
    }

}
