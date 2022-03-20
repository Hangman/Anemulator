package de.pottgames.anemulator.cpu;

import java.util.Arrays;

public class Register {
    private int[]     register          = new int[RegisterId.values().length];
    private boolean[] flags             = new boolean[FlagId.values().length];
    public int        programCounter    = 0x100;
    public int        stackPointer      = 0xfffe;
    public boolean    interruptsEnabled = false;


    public enum RegisterId {
        A(0, false), B(1, false), C(2, false), D(3, false), E(4, false), H(5, false), L(6, false), BC(7, true), DE(8, true), HL(9, true);


        private final int     index;
        private final boolean is16Bit;


        RegisterId(int index, boolean is16Bit) {
            this.index = index;
            this.is16Bit = is16Bit;
        }


        public boolean is16Bit() {
            return this.is16Bit;
        }

    }


    public enum FlagId {
        Z(0), N(1), H(2), C(3);


        private final int index;


        FlagId(int index) {
            this.index = index;
        }

    }


    public int get(RegisterId id) {
        return this.register[id.index];
    }


    public void set(RegisterId id, int value) {
        this.setInternal(id, value, true);
    }


    private void setInternal(RegisterId id, int value, boolean firstAccess) {
        this.register[id.index] = value;

        if (firstAccess) {
            switch (id) {
                case B:
                    this.setInternal(RegisterId.BC, value << 8 | this.get(RegisterId.BC) & 0x00FF, false);
                    break;
                case BC:
                    this.setInternal(RegisterId.B, value >>> 8, false);
                    this.setInternal(RegisterId.C, value & 0x00FF, false);
                    break;
                case C:
                    this.setInternal(RegisterId.BC, this.get(RegisterId.BC) & 0xFF00 | value, false);
                    break;
                case D:
                    this.setInternal(RegisterId.DE, value << 8 | this.get(RegisterId.DE) & 0x00FF, false);
                    break;
                case DE:
                    this.setInternal(RegisterId.D, value >>> 8, false);
                    this.setInternal(RegisterId.E, value & 0x00FF, false);
                    break;
                case E:
                    this.setInternal(RegisterId.DE, this.get(RegisterId.DE) & 0xFF00 | value, false);
                    break;
                case H:
                    this.setInternal(RegisterId.HL, value << 8 | this.get(RegisterId.HL) & 0x00FF, false);
                    break;
                case HL:
                    this.setInternal(RegisterId.H, value >>> 8, false);
                    this.setInternal(RegisterId.L, value & 0x00FF, false);
                    break;
                case L:
                    this.setInternal(RegisterId.HL, this.get(RegisterId.HL) & 0xFF00 | value, false);
                    break;
                case A:
                    break;
                default:
                    break;
            }
        }
    }


    public void resetFlags() {
        Arrays.fill(this.flags, false);
    }


    public void setFlag(FlagId id, boolean value) {
        this.flags[id.index] = value;
    }


    public boolean isFlagSet(FlagId id) {
        return this.flags[id.index];
    }

}
