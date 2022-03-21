package de.pottgames.anemulator.memory;

import de.pottgames.anemulator.error.AccessViolationException;

public class RomOnlyMemoryController implements MemoryController {
    private final boolean bootstrap;
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
    private int[]         memory = new int[0xffff + 1];


    public RomOnlyMemoryController(int[] romData, boolean isBootstrap) {
        System.arraycopy(romData, 0, this.memory, 0, Math.min(0x8000, romData.length));
        this.bootstrap = isBootstrap;
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
        if (address >= 0x0000 && address <= 0x7FFF) {
            throw new AccessViolationException("Writing to ROM not allowed: " + Integer.toHexString(address));
        }

        this.memory[address] = value;
        if (address >= 0xC000 && address <= 0xDDFF) {
            this.memory[address + 0x2000] = value;
        } else if (address >= 0xE000 && address <= 0xFDFF) {
            this.memory[address - 0x2000] = value;
        }
    }


    @Override
    public boolean isBootstrap() {
        return this.bootstrap;
    }

}
