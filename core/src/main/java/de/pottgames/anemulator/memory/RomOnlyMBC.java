package de.pottgames.anemulator.memory;

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

    private int[] bootRomGameCache = new int[MemoryBankController.BOOT_ROM.length];

    private String  gameName;
    private boolean bootFinished = false;


    public RomOnlyMBC(int[] romData) {
        System.arraycopy(romData, 0, this.memory, 0, Math.min(0x8000, romData.length));
        final char[] gameNameChars = new char[0x143 - 0x134];
        for (int i = 0; i < gameNameChars.length; i++) {
            gameNameChars[i] = (char) this.read8Bit(0x134 + i);
        }
        this.gameName = new String(gameNameChars).trim();

        System.arraycopy(this.memory, 0, this.bootRomGameCache, 0, MemoryBankController.BOOT_ROM.length);
        System.arraycopy(MemoryBankController.BOOT_ROM, 0, this.memory, 0, MemoryBankController.BOOT_ROM.length);
    }


    @Override
    public int read8Bit(int address) {
        // if (address == 0xFF00) {
        // this.updateInputs();
        // }

        return this.memory[address];
    }


    @Override
    public int read16Bit(int address) {
        // if (address == 0xFF00 || address == 0xFEFF) {
        // this.updateInputs();
        // }

        return this.memory[address] | this.memory[address + 1] << 8;
    }


    @Override
    public void write(int address, int value) {
        this.memory[address] = value;
        if (address >= 0xC000 && address <= 0xDDFF) {
            this.memory[address + 0x2000] = value;
        } else if (address >= 0xE000 && address <= 0xFDFF) {
            this.memory[address - 0x2000] = value;
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

    // private void updateInputs() {
    // int ff00 = this.memory[0xFF00];
    // JoypadKeyType type = JoypadKeyType.DIRECTION;
    // if ((ff00 & 1 << 5) == 0) {
    // type = JoypadKeyType.ACTION;
    // }
    //
    // for (final JoypadKey key : JoypadKey.values()) {
    // if (key.type == type) {
    // final boolean pressed = this.inputController.isButtonPressed(key);
    // if (pressed) {
    // ff00 &= ~(1 << key.bitnum);
    // } else {
    // ff00 |= 1 << key.bitnum;
    // }
    // }
    // }
    //
    // this.memory[0xFF00] = ff00;
    // }

}
