package de.pottgames.anemulator.memory;

public class Mbc1 implements Mbc {
    private final String  gameName;
    private final int[][] romBanks;
    private final int[][] ramBanks;
    private Mode          mode               = Mode.ROM;
    private int           bankSelectRegister = 1;
    private boolean       ramEnabled         = false;
    private boolean       booted             = false;
    private final int[]   romBank0           = new int[0x4000];


    public Mbc1(int[] cartridgeData) {
        this.romBanks = new int[128][0x4000];
        this.ramBanks = new int[4][0x2000];

        // COPY ROM BANK 0
        System.arraycopy(cartridgeData, 0, this.romBank0, 0, 0x4000);

        // COPY OTHER ROM BANKS
        int remainingData = cartridgeData.length - 0x4000;
        int bankIndex = 0;
        while (remainingData > 0) {
            final int length = Math.min(remainingData, 0x4000);
            System.arraycopy(cartridgeData, cartridgeData.length - remainingData, this.romBanks[bankIndex], 0, length);
            remainingData -= length;
            bankIndex++;
        }

        // FETCH GAME NAME
        final char[] gameNameChars = new char[0x143 - 0x134];
        for (int i = 0; i < gameNameChars.length; i++) {
            gameNameChars[i] = (char) this.readByte(0x134 + i);
        }
        this.gameName = new String(gameNameChars).trim();
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address >= 0 && address < 0x8000 || address == Memory.DISABLE_BOOT_ROM || address >= 0xA000 && address < 0xC000;
    }


    @Override
    public int readByte(int address) {
        if (!this.booted && address >= 0x0000 && address <= 0x00FF) {
            return Mbc.BOOT_ROM[address];
        }

        if (address >= 0x4000 && address < 0x8000) {
            if (this.mode == Mode.ROM) {
                return this.romBanks[this.bankSelectRegister - 1][address - 0x4000];
            }
            return this.romBanks[(this.bankSelectRegister & 0b11111) - 1][address - 0x4000];
        }
        if (address >= 0xA000 && address < 0xC000) {
            if (this.ramEnabled && this.mode == Mode.RAM) {
                return this.ramBanks[(this.bankSelectRegister & 0b1111111) >>> 5][address - 0xA000];
            }
            return this.ramBanks[0][address - 0xA000];
        }

        return this.romBank0[address];
    }


    @Override
    public int readWord(int address) {
        if (address < 0 || address > 0xFFFF) {
            throw new RuntimeException("Invalid memory address: " + address);
        }

        if (!this.booted && address >= 0x0000 && address <= 0x00FF) {
            return Mbc.BOOT_ROM[address] | Mbc.BOOT_ROM[address + 1] << 8;
        }

        return this.readByte(address) | this.readByte(address + 1) << 8;
    }


    @Override
    public void writeByte(int address, int value) {
        if (address >= 0x0000 && address < 0x2000) {
            // ENABLE/DISABLE RAM
            value &= 0xF;
            this.ramEnabled = value == 0x0A;

        } else if (address >= 0x2000 && address < 0x4000) {
            // SELECT ROM BANK NUMBER (LOWER 5 BITS)
            value &= 0b11111;
            if (value == 0) {
                value = 1;
            }
            this.bankSelectRegister = this.bankSelectRegister & 0b1100000 | value;

        } else if (address >= 0x4000 && address < 0x6000) {
            // SELECT RAM BANK NUMBER OR UPPER BITS OF ROM BANK NUMBER
            this.bankSelectRegister = this.bankSelectRegister & 0b11111 | (value & 0b11) << 5;
            if (this.bankSelectRegister == 0) {
                this.bankSelectRegister = 1;
            }

        } else if (address >= 0x6000 && address < 0x8000) {
            // SET MODE
            if ((value & 0b11) == 0) {
                this.mode = Mode.ROM;
            } else if ((value & 0b11) == 1) {
                this.mode = Mode.RAM;
            }

        } else if (address >= 0xA000 && address < 0xC000) {
            // WRITE TO EXTERNAL RAM
            if (this.mode == Mode.RAM) {
                this.ramBanks[(this.bankSelectRegister & 0b1111111) >>> 5][address - 0xA000] = value;
            } else {
                this.ramBanks[0][address - 0xA000] = value;
            }

        } else if (address == Memory.DISABLE_BOOT_ROM) {
            // DISABLE BOOT ROM
            this.romBank0[address] = value;
            if (value > 0) {
                this.booted = true;
            }

        } else {
            this.romBank0[address] = value;
        }
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


    private enum Mode {
        ROM, RAM;
    }

}
