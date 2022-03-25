package de.pottgames.anemulator.memory;

import de.pottgames.anemulator.input.JoypadKey;
import de.pottgames.anemulator.input.JoypadKey.JoypadKeyType;

public class RomOnlyMBC implements MemoryBankController {
    /**
     * 0x0000 - 0x7FFF => ROM<br>
     * 0x8000 - 0x9FFF => VRAM<br>
     * 0xA000 - 0xBFFF => EXTERNAL RAM<br>
     * 0xC000 - 0xDFFF => RAM<br>
     * 0xE000 - 0xFDFF => ECHO OF RAM<br>
     * 0xFE00 - 0xFE9F => OAM RAM<br>
     * 0xFEA0 - 0xFEFF => UNUSED<br>
     * 0xFF00 - 0xFF4B => I/O<br>
     * 0xFF4C - 0xFF7F => UNUSED<br>
     * 0xFF80 - 0xFFFF => INTERNAL RAM<br>
     */
    private int[] memory = new int[0xffff + 1];

    private int[]     bootRomGameCache = new int[MemoryBankController.BOOT_ROM.length];
    private String    gameName;
    private boolean   bootFinished     = false;
    private boolean[] buttonsPressed   = new boolean[JoypadKey.values().length];


    public RomOnlyMBC(int[] romData) {
        System.arraycopy(romData, 0, this.memory, 0, Math.min(0x8000, romData.length));
        final char[] gameNameChars = new char[0x143 - 0x134];
        for (int i = 0; i < gameNameChars.length; i++) {
            gameNameChars[i] = (char) this.read8Bit(0x134 + i);
        }
        this.gameName = new String(gameNameChars).trim();

        System.arraycopy(this.memory, 0, this.bootRomGameCache, 0, MemoryBankController.BOOT_ROM.length);
        System.arraycopy(MemoryBankController.BOOT_ROM, 0, this.memory, 0, MemoryBankController.BOOT_ROM.length);

        this.memory[MemoryBankController.JOYPAD] = 0b00111111;
    }


    @Override
    public int read8Bit(int address) {
        return this.memory[address];
    }


    @Override
    public int read16Bit(int address) {
        return this.memory[address] | this.memory[address + 1] << 8;
    }


    @Override
    public void write(int address, int value) {
        this.memory[address] = value;

        // ECHO RAM
        if (address >= 0xC000 && address <= 0xDDFF) {
            this.memory[address + 0x2000] = value;
        } else if (address >= 0xE000 && address <= 0xFDFF) {
            this.memory[address - 0x2000] = value;
        }

        // UPDATE JOYPAD REGISTER
        if (address == MemoryBankController.JOYPAD) {
            this.updateInput();
        }
    }


    @Override
    public String getGameName() {
        return this.gameName;
    }


    @Override
    public void finishBoot() {
        if (!this.bootFinished) {
            System.arraycopy(this.bootRomGameCache, 0, this.memory, 0, this.bootRomGameCache.length);
            this.bootRomGameCache = null;
            this.bootFinished = true;
            System.out.println("BOOT COMPLETE");
        }
    }


    private void updateInput() {
        int ff00 = this.memory[MemoryBankController.JOYPAD];
        final JoypadKeyType typeSelected = (ff00 & 1 << 5) == 0 ? JoypadKeyType.ACTION : JoypadKeyType.DIRECTION;

        for (final JoypadKey key : JoypadKey.values()) {
            if (key.getType() == typeSelected) {
                final boolean pressed = this.buttonsPressed[key.getIndex()];
                if (pressed) {
                    ff00 &= ~(1 << key.getBitnum());
                } else {
                    ff00 |= 1 << key.getBitnum();
                }
            }
        }
        this.memory[MemoryBankController.JOYPAD] = ff00;
    }


    @Override
    public void onJoypadStateChange(JoypadKey key, boolean pressed) {
        this.buttonsPressed[key.getIndex()] = pressed;
        this.updateInput();
    }

}
