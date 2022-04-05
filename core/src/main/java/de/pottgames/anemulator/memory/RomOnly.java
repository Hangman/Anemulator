package de.pottgames.anemulator.memory;

public class RomOnly implements Mbc {
    private final int[]  memory = new int[0x8001];
    private boolean      booted = false;
    private final String gameName;


    public RomOnly(int[] romData) {
        if (romData.length > 0x8000) {
            throw new RuntimeException("ROM size too large for the RomOnly controller.");
        }

        System.arraycopy(romData, 0, this.memory, 0, Math.min(0x8000, romData.length));
        final char[] gameNameChars = new char[0x143 - 0x134];
        for (int i = 0; i < gameNameChars.length; i++) {
            gameNameChars[i] = (char) this.readByte(0x134 + i);
        }
        this.gameName = new String(gameNameChars).trim();
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address >= 0 && address < 0x8000 || address == Memory.DISABLE_BOOT_ROM;
    }


    @Override
    public int readByte(int address) {
        if (!this.booted && address >= 0x0000 && address <= 0x00FF) {
            return Mbc.BOOT_ROM[address];
        }

        return this.memory[address];
    }


    @Override
    public int readWord(int address) {
        if (!this.booted && address >= 0x0000 && address <= 0x00FF) {
            return Mbc.BOOT_ROM[address] | Mbc.BOOT_ROM[address + 1] << 8;
        }

        if (address == Memory.DISABLE_BOOT_ROM) {
            return this.memory[0x8000];
        }

        return this.memory[address] | this.memory[address + 1] << 8;
    }


    @Override
    public void writeByte(int address, int value) {
        if (address == Memory.DISABLE_BOOT_ROM) {
            if (value > 0) {
                this.booted = true;
            }
            this.memory[0x8000] = value;
        }

        // ignore writes to the ROM
    }


    @Override
    public String getGameName() {
        return this.gameName;
    }


    @Override
    public void setBooted() {
        this.booted = true;
    }


    @Override
    public boolean isBooted() {
        return this.booted;
    }

}
