package de.pottgames.anemulator.memory;

import de.pottgames.anemulator.cpu.CallStack;
import de.pottgames.anemulator.cpu.DividerTimer;
import de.pottgames.anemulator.cpu.Timer;
import de.pottgames.anemulator.input.JoypadKey;
import de.pottgames.anemulator.input.JoypadKey.JoypadKeyType;

public class RomOnly implements MemoryBankController {
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
    private final int[] memory = new int[0xFFFF + 1];

    private boolean                booted         = false;
    private final String           gameName;
    private boolean[]              buttonsPressed = new boolean[JoypadKey.values().length];
    private final CallStack        callStack;
    private final Timer            timer;
    private final DividerTimer     dividerTimer;
    private final MemoryStepResult stepResult     = new MemoryStepResult();


    public RomOnly(int[] romData, CallStack callStack) {
        this.callStack = callStack;

        System.arraycopy(romData, 0, this.memory, 0, Math.min(0x8000, romData.length));
        final char[] gameNameChars = new char[0x143 - 0x134];
        for (int i = 0; i < gameNameChars.length; i++) {
            gameNameChars[i] = (char) this.read8Bit(0x134 + i);
        }
        this.gameName = new String(gameNameChars).trim();

        this.memory[MemoryBankController.JOYPAD] = 0b00111111;
        this.updateInput();
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

        return this.memory[address] | this.memory[address + 1] << 8;
    }


    @Override
    public void write(int address, int value) {
        if (address >= 0 && address <= 0x7fff) {
            // IGNORE ANY WRITE TO THE ROM
            return;
        }

        if (value > 0xFF || value < 0) {
            throw new RuntimeException("value out of byte range: " + value + " at address: " + address);
        }

        if (address < 0 || address > 0xFFFF) {
            this.callStack.print();
            throw new RuntimeException("Invalid memory address: " + address);
        }

        if (address == MemoryBankController.IF) {
            this.memory[address] = value | 0xE0;
            return;
        }

        // RESET DIVIDER REGISTER
        if (address == MemoryBankController.DIV) {
            this.memory[address] = 0;
            this.timer.reset();
            return;
        }

        this.memory[address] = value;

        // DMA TRANSFER
        if (address == MemoryBankController.DMA) {
            final int dmaAddress = value << 8;
            for (int i = 0; i < 0xA0; i++) {
                this.memory[0xFE00 + i] = this.memory[dmaAddress + i];
            }
        }

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

        // DISABLE BOOT ROM
        if (address == MemoryBankController.DISABLE_BOOT_ROM) {
            if (value > 0) {
                this.booted = true;
            }
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
    public void incDiv() {
        this.memory[MemoryBankController.DIV]++;
        if (this.memory[MemoryBankController.DIV] > 0xFF) {
            this.memory[MemoryBankController.DIV] = 0;
        }
    }


    @Override
    public String getGameName() {
        return this.gameName;
    }


    private void updateInput() {
        int ff00 = this.memory[MemoryBankController.JOYPAD];
        ff00 |= 0b11000000;
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


    @Override
    public void setBooted() {
        this.booted = true;
    }


    @Override
    public boolean isBooted() {
        return this.booted;
    }

}
