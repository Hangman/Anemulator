package de.pottgames.anemulator.memory;

import de.pottgames.anemulator.cpu.CallStack;
import de.pottgames.anemulator.cpu.DividerTimer;
import de.pottgames.anemulator.cpu.Timer;
import de.pottgames.anemulator.input.JoypadKey;
import de.pottgames.anemulator.input.JoypadKey.JoypadKeyType;

public class MBC1 implements MemoryBankController {
    private final String           gameName;
    private final int[][]          romBanks;
    private final int[][]          ramBanks;
    private Mode                   mode           = Mode.ROM;
    private int                    bankSelectRegister;
    private boolean                ramEnabled     = false;
    private boolean                booted         = false;
    private boolean[]              buttonsPressed = new boolean[JoypadKey.values().length];
    private final CallStack        callStack;
    private final Timer            timer;
    private final DividerTimer     dividerTimer;
    private final MemoryStepResult stepResult     = new MemoryStepResult();

    /**
     * 0x0000 - 0x3FFF => ROM BANK 0<br>
     * 0x4000 - 0x7FFF => SELECTED ROM BANK<br>
     * 0x8000 - 0x9FFF => VRAM<br>
     * 0xA000 - 0xA7FF/0xBFFF/0xBFFF(four switchable RAM BANKS) => EXTERNAL RAM<br>
     * 0xC000 - 0xDFFF => RAM<br>
     * 0xE000 - 0xFDFF => ECHO OF RAM<br>
     * 0xFE00 - 0xFE9F => OAM RAM<br>
     * 0xFEA0 - 0xFEFF => UNUSED<br>
     * 0xFF00 - 0xFF4B => I/O<br>
     * 0xFF4C - 0xFF7F => UNUSED<br>
     * 0xFF80 - 0xFFFF => INTERNAL RAM<br>
     */
    private final int[] memory = new int[0xFFFF + 1];


    public MBC1(int[] cartridgeData, CallStack callStack) {
        this.callStack = callStack;
        this.romBanks = new int[128][0x4000];
        this.ramBanks = new int[4][0x2000];

        // COPY ROM BANK 0
        System.arraycopy(cartridgeData, 0, this.memory, 0, 0x4000);

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
            gameNameChars[i] = (char) this.read8Bit(0x134 + i);
        }
        this.gameName = new String(gameNameChars).trim();

        // SETUP JOYPAD REGISTER
        this.updateInput();

        // SETUP TIMERS
        this.timer = new Timer(this);
        this.dividerTimer = new DividerTimer(this);
    }


    @Override
    public int read8Bit(int address) {
        if (address < 0 || address > 0xFFFF) {
            this.callStack.print();
            throw new RuntimeException("Invalid memory address: " + address);
        }

        if (!this.booted && address >= 0x0000 && address <= 0x00FF) {
            return MemoryBankController.BOOT_ROM[address];
        }

        if (address >= 0x4000 && address < 0x8000) {
            if (this.mode == Mode.ROM) {
                return this.romBanks[this.bankSelectRegister][address - 0x4000];
            }
            return this.romBanks[this.bankSelectRegister & 0b11111][address - 0x4000];
        }
        if (address >= 0xA000 && address < 0xC000) {
            if (this.ramEnabled && this.mode == Mode.RAM) {
                return this.ramBanks[(this.bankSelectRegister & 0b1111111) >>> 5][address - 0xA000];
            }
            return this.ramBanks[0][address - 0xA000];
        }

        return this.memory[address];
    }


    @Override
    public int read16Bit(int address) {
        if (address < 0 || address > 0xFFFF) {
            this.callStack.print();
            throw new RuntimeException("Invalid memory address: " + address);
        }

        if (!this.booted && address >= 0x0000 && address <= 0x00FF) {
            return MemoryBankController.BOOT_ROM[address] | MemoryBankController.BOOT_ROM[address + 1] << 8;
        }

        return this.read8Bit(address) | this.read8Bit(address + 1) << 8;
    }


    @Override
    public void write(int address, int value) {
        if (address < 0 || address > 0xFFFF) {
            this.callStack.print();
            throw new RuntimeException("Invalid memory address: " + address);
        }
        if (value > 0xFF || value < 0) {
            this.callStack.print();
            throw new RuntimeException("value out of byte range: " + value + " at address: " + address);
        }

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

        } else if (address == MemoryBankController.DMA) {
            // DMA TRANSFER
            final int dmaAddress = value << 8;
            for (int i = 0; i < 0xA0; i++) {
                this.write(0xFE00 + i, this.read8Bit(dmaAddress + i));
            }

        } else if (address == MemoryBankController.DISABLE_BOOT_ROM) {
            // DISABLE BOOT ROM
            this.memory[address] = value;
            if (value > 0) {
                this.booted = true;
            }

        } else {
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

            // RESET DIVIDER REGISTER
            if (address == MemoryBankController.DIV) {
                this.memory[address] = 0;
                this.timer.reset();
            }
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
    public void incDiv() {
        this.memory[MemoryBankController.DIV]++;
        if (this.memory[MemoryBankController.DIV] > 0xFF) {
            this.memory[MemoryBankController.DIV] = 0;
        }
    }


    @Override
    public MemoryStepResult step() {
        this.stepResult.reset();
        this.dividerTimer.step();
        this.stepResult.timerInterruptRequest = this.timer.step();

        return this.stepResult;
    }


    @Override
    public void onJoypadStateChange(JoypadKey key, boolean pressed) {
        this.buttonsPressed[key.getIndex()] = pressed;
        this.updateInput();
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
