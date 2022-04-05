package de.pottgames.anemulator.input;

import de.pottgames.anemulator.cpu.Interrupt;
import de.pottgames.anemulator.input.JoypadKey.JoypadKeyType;
import de.pottgames.anemulator.memory.Memory;
import de.pottgames.anemulator.memory.Mmu;

public class Joypad implements Memory, JoypadStateChangeListener {
    private final int[]     memory         = new int[1];
    private final boolean[] buttonsPressed = new boolean[JoypadKey.values().length];
    private final Mmu       mmu;


    public Joypad(Mmu mmu) {
        this.mmu = mmu;
        this.updateInput();
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == Memory.JOYPAD;
    }


    @Override
    public int readByte(int address) {
        return this.memory[address - Memory.JOYPAD];
    }


    @Override
    public void writeByte(int address, int value) {
        this.memory[address - Memory.JOYPAD] = value;
        this.updateInput();
    }


    @Override
    public void onJoypadStateChange(JoypadKey key, boolean pressed) {
        this.buttonsPressed[key.getIndex()] = pressed;
        this.updateInput();

        // TODO: ONLY TRIGGER INTERRUPT IF THE CORRESPONDING BUTTON TYPE IS SELECTED
        this.mmu.setBit(Memory.IF, Interrupt.JOYPAD.getBitnum(), true);
    }


    private void updateInput() {
        int ff00 = this.memory[0];
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
        this.memory[0] = ff00;
    }

}
