package de.pottgames.anemulator.cpu;

public class Register {
    private int[]   register                     = new int[8];
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
        switch (id) {
            case A:
            case B:
            case C:
            case D:
            case E:
            case H:
            case L:
            case F:
                return this.register[id.index];
            case AF:
                return this.register[RegisterId.A.index] << 8 | this.register[RegisterId.F.index];
            case BC:
                return this.register[RegisterId.B.index] << 8 | this.register[RegisterId.C.index];
            case DE:
                return this.register[RegisterId.D.index] << 8 | this.register[RegisterId.E.index];
            case HL:
                return this.register[RegisterId.H.index] << 8 | this.register[RegisterId.L.index];
        }
        throw new RuntimeException("RegisterId must not be null.");
    }


    public void set(RegisterId id, int value) {
        switch (id) {
            case A:
            case B:
            case C:
            case D:
            case E:
            case H:
            case L:
                this.register[id.index] = value & 0xFF;
                break;
            case AF:
                this.register[RegisterId.A.index] = (value & 0xFF00) >>> 8;
                this.register[RegisterId.F.index] = value & 0xF0;
                break;
            case BC:
                this.register[RegisterId.B.index] = (value & 0xFF00) >>> 8;
                this.register[RegisterId.C.index] = value & 0xFF;
                break;
            case DE:
                this.register[RegisterId.D.index] = (value & 0xFF00) >>> 8;
                this.register[RegisterId.E.index] = value & 0xFF;
                break;
            case F:
                this.register[id.index] = value & 0xF0;
                break;
            case HL:
                this.register[RegisterId.H.index] = (value & 0xFF00) >>> 8;
                this.register[RegisterId.L.index] = value & 0xFF;
                break;
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
        int f = this.get(RegisterId.F);
        f &= 1 << id.bitnum;
        return f > 0;
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
