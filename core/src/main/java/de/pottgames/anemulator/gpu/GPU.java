package de.pottgames.anemulator.gpu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import de.pottgames.anemulator.memory.MemoryBankController;

public class GPU {
    private final Color[]              colors           = { new Color(0xFFFFFFFF), new Color(0xA8A8A8FF), new Color(0x545454FF), new Color(0x000000FF) };
    private final MemoryBankController memory;
    private GpuMode                    state            = GpuMode.OAM_SEARCH;
    private int                        cycleAccumulator = 0;
    private final Pixmap               backbuffer;
    private final int[]                tileCache        = new int[16];


    public GPU(MemoryBankController memory, Pixmap backBuffer) {
        this.memory = memory;
        this.backbuffer = backBuffer;
    }


    public void step() {
        this.cycleAccumulator += 4;

        if (this.cycleAccumulator > 0) {
            final boolean gpuOn = this.memory.isBitSet(MemoryBankController.LCDC, 7);

            if (gpuOn) {
                switch (this.state) {
                    case OAM_SEARCH:
                        this.oamSearch();
                        break;
                    case PIXEL_TRANSFER:
                        this.pixelTransfer();
                        break;
                    case H_BLANK:
                        this.hBlank();
                        break;
                    case V_BLANK:
                        this.vBlank();
                        break;
                }
            }
        }
    }


    private void oamSearch() {
        this.cycleAccumulator -= 80;
        this.setState(GpuMode.PIXEL_TRANSFER);
    }


    public void renderTileMap(Pixmap pixmap) {

    }


    public void renderFullBgMap(Pixmap pixmap) {

    }


    private void pixelTransfer() {
        final boolean renderBG = this.memory.isBitSet(MemoryBankController.LCDC, 0);
        final int scrollX = this.memory.read8Bit(MemoryBankController.SCROLL_X);
        final int currentLine = this.memory.read8Bit(MemoryBankController.LCD_LY);
        int bgMapY = currentLine + this.memory.read8Bit(MemoryBankController.SCROLL_Y);
        final boolean atlasAddressMode = this.memory.isBitSet(MemoryBankController.LCDC, 4);
        if (bgMapY > 143) {
            bgMapY -= 143;
        }
        final int bgMapBlockY = bgMapY / 8;
        final int tilePixelY = bgMapY % 8;

        // RENDER BACKGROUND
        if (renderBG) {
            final int bgMapStartAddress = this.memory.isBitSet(MemoryBankController.LCDC, 3) ? 0x9C00 : 0x9800;

            for (int pixelX = 0; pixelX < 159; pixelX++) {
                int bgMapX = pixelX + scrollX;
                if (bgMapX > 159) {
                    bgMapX -= 159;
                }
                final int bgMapBlockX = bgMapX / 8;
                final int tilePixelX = bgMapX % 8;

                final int tileAddress = bgMapStartAddress + bgMapBlockY * 32 + bgMapBlockX;
                int atlasTileAddress;
                if (atlasAddressMode) {
                    final int atlasTileIndex = this.memory.read8Bit(tileAddress);
                    atlasTileAddress = 0x8000 + atlasTileIndex * 16;
                } else {
                    final byte atlasTileIndex = (byte) this.memory.read8Bit(tileAddress);
                    atlasTileAddress = 0x9000 + atlasTileIndex * 16;
                }
                for (int i = 0; i < 16; i++) {
                    this.tileCache[i] = this.memory.read8Bit(atlasTileAddress + i);
                }
                final int colorPaletteIndex = this.getColorPaletteIndexOfTilePixel(this.tileCache, tilePixelX, tilePixelY);

                final Color color = this.colors[colorPaletteIndex];
                this.backbuffer.setColor(color);
                this.backbuffer.drawPixel(pixelX, currentLine);
            }
        }

        // RENDER WINDOW
        // TODO

        // RENDER OBJECTS
        // TODO

        this.cycleAccumulator -= 172;
        this.setState(GpuMode.H_BLANK);
    }


    private void hBlank() {
        final int currentLine = this.memory.read8Bit(MemoryBankController.LCD_LY);
        this.setLine(currentLine + 1);
        if (currentLine + 1 > 143) {
            this.setState(GpuMode.V_BLANK);
        } else {
            this.setState(GpuMode.OAM_SEARCH);
        }
        this.cycleAccumulator -= 204;
    }


    private void vBlank() {
        final int currentLine = this.memory.read8Bit(0xFF44);
        if (currentLine + 1 > 153) {
            this.setLine(0);
            this.setState(GpuMode.OAM_SEARCH);
        } else {
            this.setLine(currentLine + 1);
        }
        this.cycleAccumulator -= 456;
    }


    private void setLine(int number) {
        this.memory.write(MemoryBankController.LCD_LY, number);
        final int lyc = this.memory.read8Bit(MemoryBankController.LCD_LYC);
        this.memory.setBit(MemoryBankController.LCD_STAT, 2, number == lyc);

        // REQUEST INTERRUPT IF LYC = LY
        if (this.memory.isBitSet(MemoryBankController.LCD_STAT, 6) && lyc == number) {
            this.memory.setBit(MemoryBankController.IF, 1, true);
        }
    }


    private void setState(GpuMode state) {
        this.state = state;

        // SET MODE BITS IN LCD_STAT REGISTER
        int lcdStat = this.memory.read8Bit(MemoryBankController.LCD_STAT);
        lcdStat &= ~0b11;
        lcdStat |= state.getFlagBits();
        this.memory.write(MemoryBankController.LCD_STAT, lcdStat);

        switch (state) {
            case H_BLANK:
                // REQUEST INTERRUPT IF HBLANK IS ENABLED AS SOURCE OF INTERRUPTS
                if (this.memory.isBitSet(MemoryBankController.LCD_STAT, 3)) {
                    this.memory.setBit(MemoryBankController.IF, 1, true);
                }
                break;
            case OAM_SEARCH:
                // REQUEST INTERRUPT IF OAM SEARCH IS ENABLED AS SOURCE OF INTERRUPTS
                if (this.memory.isBitSet(MemoryBankController.LCD_STAT, 5)) {
                    this.memory.setBit(MemoryBankController.IF, 1, true);
                }
                break;
            case PIXEL_TRANSFER:
                break;
            case V_BLANK:
                // REQUEST INTERRUPT IF VBLANK IS ENABLED AS SOURCE OF INTERRUPTS
                if (this.memory.isBitSet(MemoryBankController.LCD_STAT, 4)) {
                    this.memory.setBit(MemoryBankController.IF, 1, true);
                }
                this.memory.setBit(MemoryBankController.IF, 0, true);
                break;
        }
    }


    private int getColorPaletteIndexOfTilePixel(int[] tile, int x, int y) {
        int byte1 = tile[y * 2];
        int byte2 = tile[y * 2 + 1];
        byte1 = byte1 >>> 7 - x & 0x1;
        byte2 = byte2 >>> 7 - x & 0x1;
        return byte1 | byte2 << 1;
    }


    private int getBGColor(final int paletteIndex) {
        final int palette = this.memory.read8Bit(MemoryBankController.BGP);
        switch (paletteIndex) {
            case 3:
                return palette >>> 6;
            case 2:
                return palette >>> 4 & 0b11;
            case 1:
                return palette >>> 2 & 0b11;
            case 0:
                return palette & 0b11;
        }

        throw new RuntimeException("Unknown bg color palette index: " + paletteIndex);
    }

}
