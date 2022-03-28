package de.pottgames.anemulator.cpu;

public class Register {
    private int[]   register                     = new int[RegisterId.values().length];
    public int      pc                           = 0;
    public int      sp                           = 0xfffe;
    private boolean interruptsEnabled            = false;
    private byte    enableInterruptsDelayCounter = 0;


    public enum RegisterId {
        A(0, false),
        F(1, false),
        B(2, false),
        C(3, false),
        D(4, false),
        E(5, false),
        H(6, false),
        L(7, false),
        AF(8, true),
        BC(9, true),
        DE(10, true),
        HL(11, true);


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
        Z(7), N(6), H(5), C(4);


        private final int bitnum;


        FlagId(int bitnum) {
            this.bitnum = bitnum;
        }

    }


    void step() {
        if (this.enableInterruptsDelayCounter > 0) {
            this.enableInterruptsDelayCounter--;
            if (this.enableInterruptsDelayCounter == 0) {
                this.interruptsEnabled = true;
            }
        }
    }


    public int get(RegisterId id) {
        return this.register[id.index];
    }


    public void set(RegisterId id, int value) {
        if (id.is16Bit()) {
            value &= 0xFFFF;
        } else {
            value &= 0xFF;
        }
        this.setInternal(id, value, true);
    }


    private void setInternal(RegisterId id, int value, boolean firstAccess) {
        if (id == RegisterId.F) {
            value &= 0xF0;
        } else if (id == RegisterId.AF) {
            value &= 0xFFF0;
        }
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
                    this.setInternal(RegisterId.L, value & 0xFF, false);
                    break;
                case L:
                    this.setInternal(RegisterId.HL, this.get(RegisterId.HL) & 0xFF00 | value, false);
                    break;
                case A:
                    this.setInternal(RegisterId.AF, value << 8 | this.get(RegisterId.AF) & 0x00FF, false);
                    break;
                case F:
                    this.setInternal(RegisterId.AF, this.get(RegisterId.AF) & 0xFF00 | value, false);
                    break;
                case AF:
                    this.setInternal(RegisterId.A, value >>> 8, false);
                    this.setInternal(RegisterId.F, value & 0x00FF, false);
                    break;
            }
        }
    }


    public void setFlag(FlagId id, boolean value) {
        int f = this.get(RegisterId.F);
        if (value) {
            f |= 1 << id.bitnum;
        } else {
            f &= ~(1 << id.bitnum);
        }
        this.set(RegisterId.F, f);
    }


    public boolean isFlagSet(FlagId id) {
        return (this.get(RegisterId.F) & 1 << id.bitnum) > 0;
    }


    public boolean isInterruptsEnabled() {
        return this.interruptsEnabled;
    }


    public void setInterruptsEnabled(boolean interruptsEnabled) {
        if (interruptsEnabled) {
            this.enableInterruptsDelayCounter = 2;
        } else {
            this.interruptsEnabled = false;
            this.enableInterruptsDelayCounter = 0;
        }
    }

}
