package de.pottgames.anemulator.ppu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import de.pottgames.anemulator.memory.Memory;

/**
 * TODO: LY register is always 0 if the lcd is off.
 *
 */
public class PPU {
    private static final Color TRANSPARENT = new Color(0xFFFFFF00);
    private final Color[]      colors      = { new Color(0xFFFFFFFF), new Color(0xA8A8A8FF), new Color(0x545454FF), new Color(0x000000FF) };
    private final Memory       mmu;

    private GpuMode state = GpuMode.V_BLANK;

    private int           cycleAccumulator = 0;
    private final Pixmap  frontBuffer;
    private final int[][] backBuffer       = new int[160][144];
    private final int[]   tileCache        = new int[16];
    private boolean       wasOff           = false;


    public PPU(Memory memory, Pixmap backBuffer) {
        this.mmu = memory;
        this.frontBuffer = backBuffer;
    }


    public boolean step() {
        final boolean gpuOn = this.mmu.isBitSet(Memory.LCDC, 7);

        if (gpuOn) {
            this.cycleAccumulator += 4;
            this.wasOff = false;

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
        } else if (!this.wasOff) {
            this.setLine(0);
            this.setState(GpuMode.H_BLANK);
            this.wasOff = true;
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
                    this.tileCache[i] = this.mmu.readByte(tileStartAddress + i);
                }

                final int colorPaletteIndex = this.getColorPaletteIndexOfTilePixel(this.tileCache, tilePixelX, tilePixelY);
                final Color color = this.getBGColor(colorPaletteIndex);

                pixmap.setColor(color);
                pixmap.drawPixel(x, y);
            }
        }
    }


    private void pixelTransfer() {
        final boolean renderBG = this.mmu.isBitSet(Memory.LCDC, 0);
        final boolean renderWindow = this.mmu.isBitSet(Memory.LCDC, 5);
        final boolean renderObjects = this.mmu.isBitSet(Memory.LCDC, 1);
        final int currentLine = this.mmu.readByte(Memory.LCD_LY);
        final boolean atlasAddressMode = this.mmu.isBitSet(Memory.LCDC, 4);

        if (renderBG) {

            // RENDER BACKGROUND
            this.renderBg(currentLine, atlasAddressMode);

            // RENDER WINDOW
            if (renderWindow) {
                this.renderWindow(currentLine, atlasAddressMode);
            }
        }

        // RENDER OBJECTS
        if (renderObjects) {
            this.renderObjects(currentLine);
        }

        this.cycleAccumulator -= 172;
        this.setState(GpuMode.H_BLANK);
    }


    private void renderWindow(int currentLine, boolean atlasAddressMode) {
        final int windowMapStartAddress = this.mmu.isBitSet(Memory.LCDC, 6) ? 0x9C00 : 0x9800;
        final int wx = this.mmu.readByte(Memory.WX) - 7;
        final int wy = this.mmu.readByte(Memory.WY);
        int lastTileAddress = -1;

        if (currentLine >= wy) {
            for (int x = wx; x < 160; x++) {
                // FIND TILE ADDRESS
                final int windowLine = currentLine - wy;
                final int pixelX = x - wx;
                final int tilePixelX = pixelX % 8;
                final int tilePixelY = windowLine % 8;
                final int tileAddress = windowMapStartAddress + windowLine / 8 * 32 + pixelX / 8;

                if (pixelX >= 0 && pixelX < 160) {

                    // READ TILE DATA
                    if (tileAddress != lastTileAddress) {
                        int atlasTileAddress;
                        if (atlasAddressMode) {
                            final int atlasTileIndex = this.mmu.readByte(tileAddress);
                            atlasTileAddress = 0x8000 + atlasTileIndex * 16;
                        } else {
                            final byte atlasTileIndex = (byte) this.mmu.readByte(tileAddress);
                            atlasTileAddress = 0x9000 + atlasTileIndex * 16;
                        }

                        for (int i = 0; i < 16; i++) {
                            this.tileCache[i] = this.mmu.readByte(atlasTileAddress + i);
                        }
                        lastTileAddress = tileAddress;
                    }

                    // FETCH COLOR
                    final int colorPaletteIndex = this.getColorPaletteIndexOfTilePixel(this.tileCache, tilePixelX, tilePixelY);
                    final Color color = this.getBGColor(colorPaletteIndex);

                    // RENDER
                    this.backBuffer[pixelX][currentLine] = colorPaletteIndex;
                    this.frontBuffer.setColor(color);
                    this.frontBuffer.drawPixel(pixelX, currentLine);
                }
            }
        }
    }


    private void renderBg(int currentLine, boolean atlasAddressMode) {
        final int bgMapStartAddress = this.mmu.isBitSet(Memory.LCDC, 3) ? 0x9C00 : 0x9800;
        final int scrollX = this.mmu.readByte(Memory.SCROLL_X);
        int bgMapY = currentLine + this.mmu.readByte(Memory.SCROLL_Y);
        if (bgMapY > 255) {
            bgMapY -= 255;
        }
        final int bgMapBlockY = bgMapY / 8;
        final int tilePixelY = bgMapY % 8;

        int lastTileAddress = -1;

        for (int pixelX = 0; pixelX < 160; pixelX++) {
            // FIND TILE ADDRESS
            int bgMapX = pixelX + scrollX;
            if (bgMapX > 255) {
                bgMapX -= 255;
            }
            final int bgMapBlockX = bgMapX / 8;
            final int tilePixelX = bgMapX % 8;
            final int tileAddress = bgMapStartAddress + bgMapBlockY * 32 + bgMapBlockX;

            // READ TILE DATA
            if (tileAddress != lastTileAddress) {
                int atlasTileAddress;
                if (atlasAddressMode) {
                    final int atlasTileIndex = this.mmu.readByte(tileAddress);
                    atlasTileAddress = 0x8000 + atlasTileIndex * 16;
                } else {
                    final byte atlasTileIndex = (byte) this.mmu.readByte(tileAddress);
                    atlasTileAddress = 0x9000 + atlasTileIndex * 16;
                }

                for (int i = 0; i < 16; i++) {
                    this.tileCache[i] = this.mmu.readByte(atlasTileAddress + i);
                }
                lastTileAddress = tileAddress;
            }

            // FETCH COLOR
            final int colorPaletteIndex = this.getColorPaletteIndexOfTilePixel(this.tileCache, tilePixelX, tilePixelY);
            final Color color = this.getBGColor(colorPaletteIndex);

            // RENDER
            this.backBuffer[pixelX][currentLine] = colorPaletteIndex;
            this.frontBuffer.setColor(color);
            this.frontBuffer.drawPixel(pixelX, currentLine);
        }
    }


    private void renderObjects(final int currentLine) {
        final int objHeight = this.mmu.isBitSet(Memory.LCDC, 2) ? 16 : 8;
        for (int i = 0; i < 40; i++) {
            final int oamAddress = 0xFE00 + i * 4;
            int objY = this.mmu.readByte(oamAddress);
            int objX = this.mmu.readByte(oamAddress + 1);
            if (this.isObjectVisible(objX, objY, objHeight, currentLine)) {
                final int objTileIndex = this.mmu.readByte(oamAddress + 2);
                final int objAttributes = this.mmu.readByte(oamAddress + 3);
                final int atlasTileAddress = 0x8000 + objTileIndex * 16;
                final boolean flipX = (objAttributes & 0b100000) > 0;
                final boolean flipY = (objAttributes & 0b1000000) > 0;
                final boolean priority = (objAttributes & 0b10000000) == 0;
                final int paletteAddress = (objAttributes & 0b10000) > 0 ? 0xFF49 : 0xFF48;
                objX -= 8;
                objY -= 16;
                int tilePixelY = flipY ? objHeight - 1 - (currentLine - objY) : currentLine - objY;
                int atlasAddressModificator = objHeight == 16 ? flipY ? 16 : 0 : 0;
                if (tilePixelY >= 8) {
                    tilePixelY -= 8;
                    atlasAddressModificator = objHeight == 16 ? flipY ? 0 : 16 : 0;
                }

                // FETCH TILE PIXELS FROM ATLAS
                for (int j = 0; j < 16; j++) {
                    this.tileCache[j] = this.mmu.readByte(atlasTileAddress + atlasAddressModificator + j);
                }

                this.drawObjectLine(this.tileCache, objX, tilePixelY, flipX, priority, currentLine, paletteAddress);
            }
        }
    }


    private void drawObjectLine(int[] tile, int x, int tilePixelY, boolean flipX, boolean priority, int scanline, int paletteAddress) {
        for (int pixelX = 0; pixelX < 8; pixelX++) {
            final int tilePixelX = flipX ? 7 - pixelX : pixelX;
            final int colorPaletteIndex = this.getColorPaletteIndexOfTilePixel(this.tileCache, tilePixelX, tilePixelY);
            final Color color = this.getObjectColor(paletteAddress, colorPaletteIndex);

            final int renderX = x + pixelX;
            if (renderX >= 160) {
                return;
            }
            if (renderX >= 0 && (priority || this.backBuffer[renderX][scanline] == 0)) {
                this.frontBuffer.setColor(color);
                this.frontBuffer.drawPixel(x + pixelX, scanline);
            }
        }
    }


    private boolean isObjectVisible(int objX, int objY, int objHeight, int scanline) {
        objY -= 16;
        objX -= 8;
        if (objX >= -8 && objX < 160 + 8 && scanline >= objY && scanline < objY + objHeight) {
            return true;
        }

        return false;
    }


    private void hBlank() {
        final int currentLine = this.mmu.readByte(Memory.LCD_LY);
        this.setLine(currentLine + 1);
        if (currentLine + 1 > 143) {
            this.setState(GpuMode.V_BLANK);
        } else {
            this.setState(GpuMode.OAM_SEARCH);
        }
        this.cycleAccumulator -= 204;
    }


    private void vBlank() {
        final int currentLine = this.mmu.readByte(Memory.LCD_LY);
        if (currentLine + 1 > 153) {
            this.setLine(0);
            this.setState(GpuMode.OAM_SEARCH);
        } else {
            this.setLine(currentLine + 1);
        }
        this.cycleAccumulator -= 456;
    }


    private void setLine(int number) {
        this.mmu.writeByte(Memory.LCD_LY, number);
        final int lyc = this.mmu.readByte(Memory.LCD_LYC);
        this.mmu.setBit(Memory.LCD_STAT, 2, number == lyc);

        // REQUEST INTERRUPT IF LYC = LY
        if (this.mmu.isBitSet(Memory.LCD_STAT, 6) && lyc == number) {
            this.mmu.setBit(Memory.IF, 1, true);
        }
    }


    private void setState(GpuMode state) {
        this.state = state;

        // SET MODE BITS IN LCD_STAT REGISTER
        int lcdStat = this.mmu.readByte(Memory.LCD_STAT);
        lcdStat &= ~0b11;
        lcdStat |= state.getFlagBits();
        this.mmu.writeByte(Memory.LCD_STAT, lcdStat);

        switch (state) {
            case H_BLANK:
                // REQUEST INTERRUPT IF HBLANK IS ENABLED AS SOURCE OF INTERRUPTS
                if (this.mmu.isBitSet(Memory.LCD_STAT, 3)) {
                    this.mmu.setBit(Memory.IF, 1, true);
                }
                break;
            case OAM_SEARCH:
                // REQUEST INTERRUPT IF OAM SEARCH IS ENABLED AS SOURCE OF INTERRUPTS
                if (this.mmu.isBitSet(Memory.LCD_STAT, 5)) {
                    this.mmu.setBit(Memory.IF, 1, true);
                }
                break;
            case PIXEL_TRANSFER:
                break;
            case V_BLANK:
                // REQUEST INTERRUPT IF VBLANK IS ENABLED AS SOURCE OF INTERRUPTS
                if (this.mmu.isBitSet(Memory.LCD_STAT, 4)) {
                    this.mmu.setBit(Memory.IF, 1, true);
                }
                this.mmu.setBit(Memory.IF, 0, true);
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


    private Color getObjectColor(int paletteAddress, int colorIndex) {
        final int palette = this.mmu.readByte(paletteAddress);
        switch (colorIndex) {
            case 3:
                return this.colors[palette >>> 6];
            case 2:
                return this.colors[palette >>> 4 & 0b11];
            case 1:
                return this.colors[palette >>> 2 & 0b11];
            case 0:
                return PPU.TRANSPARENT;
        }

        return null;
    }


    private Color getBGColor(final int colorIndex) {
        final int palette = this.mmu.readByte(Memory.BGP);
        switch (colorIndex) {
            case 3:
                return this.colors[palette >>> 6];
            case 2:
                return this.colors[palette >>> 4 & 0b11];
            case 1:
                return this.colors[palette >>> 2 & 0b11];
            case 0:
                return this.colors[palette & 0b11];
        }

        throw new RuntimeException("Unknown bg color palette index: " + colorIndex);
    }

}
