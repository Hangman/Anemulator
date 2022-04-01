package de.pottgames.anemulator.gpu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import de.pottgames.anemulator.memory.MemoryBankController;

/**
 * TODO: LY register is always 0 if the lcd is off.
 *
 */
public class GPU {
    private final Color[]              colors = { new Color(0xFFFFFFFF), new Color(0xA8A8A8FF), new Color(0x545454FF), new Color(0x000000FF) };
    private final MemoryBankController memory;

    public GpuMode state = GpuMode.V_BLANK; // TODO: set private

    private int          cycleAccumulator = 0;
    private final Pixmap backbuffer;
    private final int[]  tileCache        = new int[16];


    public GPU(MemoryBankController memory, Pixmap backBuffer) {
        this.memory = memory;
        this.backbuffer = backBuffer;
    }


    public boolean step() {
        final boolean gpuOn = this.memory.isBitSet(MemoryBankController.LCDC, 7);

        if (gpuOn) {
            this.cycleAccumulator += 4;

            if (this.cycleAccumulator > 0) {
                final GpuMode oldMode = this.state;
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
                if (this.state == GpuMode.V_BLANK && oldMode != GpuMode.V_BLANK) {
                    return true;
                }
            }
        }

        return false;
    }


    private void oamSearch() {
        this.cycleAccumulator -= 80;
        this.setState(GpuMode.PIXEL_TRANSFER);
    }


    public void renderTileMap(Pixmap pixmap) {
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 192; y++) {
                final int tileIndexX = x / 8;
                final int tileIndexY = y / 8;
                final int tileStartAddress = 0x8000 + (tileIndexY * 16 + tileIndexX) * 16;
                final int tilePixelX = x % 8;
                final int tilePixelY = y % 8;

                for (int i = 0; i < 16; i++) {
                    this.tileCache[i] = this.memory.read8Bit(tileStartAddress + i);
                }

                final int colorPaletteIndex = this.getColorPaletteIndexOfTilePixel(this.tileCache, tilePixelX, tilePixelY);
                final Color color = this.getBGColor(colorPaletteIndex);

                pixmap.setColor(color);
                pixmap.drawPixel(x, y);
            }
        }
    }


    private void pixelTransfer() {
        final boolean renderBG = this.memory.isBitSet(MemoryBankController.LCDC, 0);
        final boolean renderWindow = this.memory.isBitSet(MemoryBankController.LCDC, 5);
        final boolean renderObjects = this.memory.isBitSet(MemoryBankController.LCDC, 1);
        final int scrollX = this.memory.read8Bit(MemoryBankController.SCROLL_X);
        final int currentLine = this.memory.read8Bit(MemoryBankController.LCD_LY);
        final boolean atlasAddressMode = this.memory.isBitSet(MemoryBankController.LCDC, 4);
        int bgMapY = currentLine + this.memory.read8Bit(MemoryBankController.SCROLL_Y);
        if (bgMapY > 255) {
            bgMapY -= 255;
        }
        final int bgMapBlockY = bgMapY / 8;
        final int tilePixelY = bgMapY % 8;

        if (renderBG) {

            // RENDER BACKGROUND
            final int bgMapStartAddress = this.memory.isBitSet(MemoryBankController.LCDC, 3) ? 0x9C00 : 0x9800;
            for (int pixelX = 0; pixelX < 159; pixelX++) {
                int bgMapX = pixelX + scrollX;
                if (bgMapX > 255) {
                    bgMapX -= 255;
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
                final Color color = this.getBGColor(colorPaletteIndex);

                this.backbuffer.setColor(color);
                this.backbuffer.drawPixel(pixelX, currentLine);
            }

            // RENDER WINDOW
            if (renderWindow) {
                final int windowMapStartAddress = this.memory.isBitSet(MemoryBankController.LCDC, 6) ? 0x9C00 : 0x9800;
                final int wx = this.memory.read8Bit(MemoryBankController.WX);
                final int wy = this.memory.read8Bit(MemoryBankController.WY);

                for (int pixelX = 0; pixelX < 159; pixelX++) {
                    // TODO
                }
            }
        }

        // RENDER OBJECTS
        if (renderObjects) {
            final int objHeight = this.memory.isBitSet(MemoryBankController.LCDC, 2) ? 16 : 8;
            // TODO
        }

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
        final int currentLine = this.memory.read8Bit(MemoryBankController.LCD_LY);
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


    private Color getBGColor(final int paletteIndex) {
        final int palette = this.memory.read8Bit(MemoryBankController.BGP);
        switch (paletteIndex) {
            case 3:
                return this.colors[palette >>> 6];
            case 2:
                return this.colors[palette >>> 4 & 0b11];
            case 1:
                return this.colors[palette >>> 2 & 0b11];
            case 0:
                return this.colors[palette & 0b11];
        }

        throw new RuntimeException("Unknown bg color palette index: " + paletteIndex);
    }

}
