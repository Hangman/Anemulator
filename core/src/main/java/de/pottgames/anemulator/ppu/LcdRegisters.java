package de.pottgames.anemulator.ppu;

import de.pottgames.anemulator.memory.Memory;

public class LcdRegisters implements Memory {
    private final int[] memory = new int[11];


    @Override
    public boolean acceptsAddress(int address) {
        return address == Memory.LCDC || address == Memory.LCD_STAT || address == Memory.LCD_LY || address == Memory.LCD_LYC || address == Memory.SCROLL_X
                || address == Memory.SCROLL_Y || address == Memory.BGP || address == Memory.OBP0 || address == Memory.OBP1 || address == Memory.WX
                || address == Memory.WY;
    }


    @Override
    public int readByte(int address) {
        switch (address) {
            case Memory.LCDC:
                return this.memory[0];
            case Memory.LCD_STAT:
                return this.memory[1];
            case Memory.LCD_LY:
                return this.memory[2];
            case Memory.LCD_LYC:
                return this.memory[3];
            case Memory.SCROLL_X:
                return this.memory[4];
            case Memory.SCROLL_Y:
                return this.memory[5];
            case Memory.BGP:
                return this.memory[6];
            case Memory.OBP0:
                return this.memory[7];
            case Memory.OBP1:
                return this.memory[8];
            case Memory.WX:
                return this.memory[9];
            case Memory.WY:
                return this.memory[10];
            default:
                throw new RuntimeException("Invalid address.");
        }
    }


    @Override
    public void writeByte(int address, int value) {
        switch (address) {
            case Memory.LCDC:
                this.memory[0] = value;
                break;
            case Memory.LCD_STAT:
                this.memory[1] = value;
                break;
            case Memory.LCD_LY:
                this.memory[2] = value;
                break;
            case Memory.LCD_LYC:
                this.memory[3] = value;
                break;
            case Memory.SCROLL_X:
                this.memory[4] = value;
                break;
            case Memory.SCROLL_Y:
                this.memory[5] = value;
                break;
            case Memory.BGP:
                this.memory[6] = value;
                break;
            case Memory.OBP0:
                this.memory[7] = value;
                break;
            case Memory.OBP1:
                this.memory[8] = value;
                break;
            case Memory.WX:
                this.memory[9] = value;
                break;
            case Memory.WY:
                this.memory[10] = value;
                break;
            default:
                throw new RuntimeException("Invalid address.");
        }
    }

}
