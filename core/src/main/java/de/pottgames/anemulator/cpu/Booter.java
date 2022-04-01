package de.pottgames.anemulator.cpu;

import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Booter {
    private final MemoryBankController memory;
    private final Register             register;


    public Booter(Register register, MemoryBankController memory) {
        this.register = register;
        this.memory = memory;
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

        this.memory.write(MemoryBankController.JOYPAD, 0xCF);
        this.memory.write(MemoryBankController.SB, 0x00);
        this.memory.write(MemoryBankController.SC, 0x7E);
        this.memory.write(MemoryBankController.DIV, 0x18);
        this.memory.write(MemoryBankController.TIMA, 0x00);
        this.memory.write(MemoryBankController.TMA, 0x00);
        this.memory.write(MemoryBankController.TAC, 0xF8);
        this.memory.write(MemoryBankController.IF, 0xE1);
        this.memory.write(MemoryBankController.NR10, 0x80);
        this.memory.write(MemoryBankController.NR11, 0xBF);
        this.memory.write(MemoryBankController.NR12, 0xF3);
        this.memory.write(MemoryBankController.NR13, 0xFF);
        this.memory.write(MemoryBankController.NR14, 0xBF);
        this.memory.write(MemoryBankController.NR21, 0x3F);
        this.memory.write(MemoryBankController.NR22, 0x00);
        this.memory.write(MemoryBankController.NR23, 0xFF);
        this.memory.write(MemoryBankController.NR24, 0xBF);
        this.memory.write(MemoryBankController.NR30, 0x7F);
        this.memory.write(MemoryBankController.NR31, 0xFF);
        this.memory.write(MemoryBankController.NR32, 0x9F);
        this.memory.write(MemoryBankController.NR33, 0xFF);
        this.memory.write(MemoryBankController.NR34, 0xBF);
        this.memory.write(MemoryBankController.NR41, 0xFF);
        this.memory.write(MemoryBankController.NR42, 0x00);
        this.memory.write(MemoryBankController.NR43, 0x00);
        this.memory.write(MemoryBankController.NR44, 0xBF);
        this.memory.write(MemoryBankController.NR50, 0x77);
        this.memory.write(MemoryBankController.NR51, 0xF3);
        this.memory.write(MemoryBankController.NR52, 0xF1);
        this.memory.write(MemoryBankController.LCDC, 0x91);
        this.memory.write(MemoryBankController.LCD_STAT, 0x81);
        this.memory.write(MemoryBankController.SCROLL_Y, 0x00);
        this.memory.write(MemoryBankController.SCROLL_X, 0x00);
        this.memory.write(MemoryBankController.LCD_LY, 0x91);
        this.memory.write(MemoryBankController.LCD_LYC, 0x00);
        this.memory.write(MemoryBankController.DMA, 0xFF);
        this.memory.write(MemoryBankController.BGP, 0xFC);
        this.memory.write(MemoryBankController.OBP0, 0x00);
        this.memory.write(MemoryBankController.OBP1, 0x00);
        this.memory.write(MemoryBankController.WY, 0x00);
        this.memory.write(MemoryBankController.WX, 0x00);
        this.memory.write(MemoryBankController.KEY1, 0xFF);
        this.memory.write(MemoryBankController.VBK, 0xFF);
        this.memory.write(MemoryBankController.HDMA1, 0xFF);
        this.memory.write(MemoryBankController.HDMA2, 0xFF);
        this.memory.write(MemoryBankController.HDMA3, 0xFF);
        this.memory.write(MemoryBankController.HDMA4, 0xFF);
        this.memory.write(MemoryBankController.HDMA5, 0xFF);
        this.memory.write(MemoryBankController.RP, 0xFF);
        this.memory.write(MemoryBankController.BCPS, 0xFF);
        this.memory.write(MemoryBankController.BCPD, 0xFF);
        this.memory.write(MemoryBankController.OCPS, 0xFF);
        this.memory.write(MemoryBankController.OCPD, 0xFF);
        this.memory.write(MemoryBankController.SVBK, 0xFF);
        this.memory.write(MemoryBankController.IE, 0x00);

        this.memory.setBooted();
    }

}
