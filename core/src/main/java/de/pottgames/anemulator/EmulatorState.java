package de.pottgames.anemulator;

import java.util.Objects;

public class EmulatorState {
    private final int a;
    private final int f;
    private final int c;
    private final int d;
    private final int e;
    private final int h;
    private final int l;
    private final int af;
    private final int bc;
    private final int de;
    private final int hl;
    private final int opcode;
    private final int pc;
    private final int sp;


    public EmulatorState(int a, int f, int c, int d, int e, int h, int l, int af, int bc, int de, int hl, int opcode, int pc, int sp) {
        this.a = a;
        this.f = f;
        this.c = c;
        this.d = d;
        this.e = e;
        this.h = h;
        this.l = l;
        this.af = af;
        this.bc = bc;
        this.de = de;
        this.hl = hl;
        this.opcode = opcode;
        this.pc = pc;
        this.sp = sp;
    }


    public int getA() {
        return this.a;
    }


    public int getF() {
        return this.f;
    }


    public int getC() {
        return this.c;
    }


    public int getD() {
        return this.d;
    }


    public int getE() {
        return this.e;
    }


    public int getH() {
        return this.h;
    }


    public int getL() {
        return this.l;
    }


    public int getAf() {
        return this.af;
    }


    public int getBc() {
        return this.bc;
    }


    public int getDe() {
        return this.de;
    }


    public int getHl() {
        return this.hl;
    }


    public int getOpcode() {
        return this.opcode;
    }


    public int getPC() {
        return this.pc;
    }


    public int getSP() {
        return this.sp;
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.a, this.af, this.bc, this.c, this.d, this.de, this.e, this.f, this.h, this.hl, this.l, this.opcode, this.pc, this.sp);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final EmulatorState other = (EmulatorState) obj;
        if (this.a != other.a) {
            return false;
        }
        if (this.af != other.af) {
            return false;
        }
        if (this.bc != other.bc) {
            return false;
        }
        if (this.c != other.c) {
            return false;
        }
        if (this.d != other.d) {
            return false;
        }
        if (this.de != other.de) {
            return false;
        }
        if (this.e != other.e) {
            return false;
        }
        if (this.f != other.f) {
            return false;
        }
        if (this.h != other.h) {
            return false;
        }
        if (this.hl != other.hl) {
            return false;
        }
        if (this.l != other.l) {
            return false;
        }
        if (this.opcode != other.opcode) {
            return false;
        }
        if (this.pc != other.pc) {
            return false;
        }
        if (this.sp != other.sp) {
            return false;
        }
        return true;
    }


    public void printDifferences(EmulatorState other) {
        if (this.equals(other)) {
            System.out.println("EmulatorStates are equal");
            return;
        }

        if (this.a != other.a) {
            System.out.println("a different! this: " + Integer.toHexString(this.a) + ", other: " + Integer.toHexString(other.a));
        }
        if (this.f != other.f) {
            System.out.println("f different! this: " + Integer.toBinaryString(this.f) + ", other: " + Integer.toBinaryString(other.f));
        }
        if (this.c != other.c) {
            System.out.println("c different! this: " + Integer.toHexString(this.c) + ", other: " + Integer.toHexString(other.c));
        }
        if (this.d != other.d) {
            System.out.println("d different! this: " + Integer.toHexString(this.d) + ", other: " + Integer.toHexString(other.d));
        }
        if (this.e != other.e) {
            System.out.println("e different! this: " + Integer.toHexString(this.e) + ", other: " + Integer.toHexString(other.e));
        }
        if (this.h != other.h) {
            System.out.println("h different! this: " + Integer.toHexString(this.h) + ", other: " + Integer.toHexString(other.h));
        }
        if (this.l != other.l) {
            System.out.println("l different! this: " + Integer.toHexString(this.l) + ", other: " + Integer.toHexString(other.l));
        }
        if (this.af != other.af) {
            System.out.println("af different! this: " + Integer.toHexString(this.af) + ", other: " + Integer.toHexString(other.af));
        }
        if (this.bc != other.bc) {
            System.out.println("bc different! this: " + Integer.toHexString(this.bc) + ", other: " + Integer.toHexString(other.bc));
        }
        if (this.de != other.de) {
            System.out.println("de different! this: " + Integer.toHexString(this.de) + ", other: " + Integer.toHexString(other.de));
        }
        if (this.hl != other.hl) {
            System.out.println("hl different! this: " + Integer.toHexString(this.hl) + ", other: " + Integer.toHexString(other.hl));
        }
        if (this.opcode != other.opcode) {
            System.out.println("opcode different! this: " + Integer.toHexString(this.opcode) + ", other: " + Integer.toHexString(other.opcode));
        }
        if (this.pc != other.pc) {
            System.out.println("pc different! this: " + Integer.toHexString(this.pc) + ", other: " + Integer.toHexString(other.pc));
        }
        if (this.sp != other.sp) {
            System.out.println("sp different! this: " + Integer.toHexString(this.sp) + ", other: " + Integer.toHexString(other.sp));
        }
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append("a: ");
        builder.append(Integer.toHexString(this.a));
        builder.append(" \n ");

        builder.append("f: ");
        builder.append(Integer.toHexString(this.f));
        builder.append(" \n ");

        builder.append("c: ");
        builder.append(Integer.toHexString(this.c));
        builder.append(" \n ");

        builder.append("d: ");
        builder.append(Integer.toHexString(this.d));
        builder.append(" \n ");

        builder.append("e: ");
        builder.append(Integer.toHexString(this.e));
        builder.append(" \n ");

        builder.append("h: ");
        builder.append(Integer.toHexString(this.h));
        builder.append(" \n ");

        builder.append("l: ");
        builder.append(Integer.toHexString(this.l));
        builder.append(" \n ");

        builder.append("af: ");
        builder.append(Integer.toHexString(this.af));
        builder.append(" \n ");

        builder.append("bc: ");
        builder.append(Integer.toHexString(this.bc));
        builder.append(" \n ");

        builder.append("de: ");
        builder.append(Integer.toHexString(this.de));
        builder.append(" \n ");

        builder.append("hl: ");
        builder.append(Integer.toHexString(this.hl));
        builder.append(" \n ");

        builder.append("opcode: ");
        builder.append(Integer.toHexString(this.opcode));
        builder.append(" \n ");

        builder.append("pc: ");
        builder.append(Integer.toHexString(this.pc));
        builder.append(" \n ");

        builder.append("sp: ");
        builder.append(Integer.toHexString(this.sp));
        builder.append(" \n ");

        return builder.toString();
    }

}
