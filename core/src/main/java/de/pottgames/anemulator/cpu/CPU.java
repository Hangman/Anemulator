package de.pottgames.anemulator.cpu;

import com.badlogic.gdx.utils.IntMap;

import de.pottgames.anemulator.cpu.instructions.Calla16;
import de.pottgames.anemulator.cpu.instructions.CpA;
import de.pottgames.anemulator.cpu.instructions.CpB;
import de.pottgames.anemulator.cpu.instructions.CpC;
import de.pottgames.anemulator.cpu.instructions.CpD;
import de.pottgames.anemulator.cpu.instructions.CpE;
import de.pottgames.anemulator.cpu.instructions.CpH;
import de.pottgames.anemulator.cpu.instructions.CpL;
import de.pottgames.anemulator.cpu.instructions.Cp_HL_;
import de.pottgames.anemulator.cpu.instructions.Cpd8;
import de.pottgames.anemulator.cpu.instructions.DecA;
import de.pottgames.anemulator.cpu.instructions.DecB;
import de.pottgames.anemulator.cpu.instructions.DecC;
import de.pottgames.anemulator.cpu.instructions.DecD;
import de.pottgames.anemulator.cpu.instructions.DecE;
import de.pottgames.anemulator.cpu.instructions.DecH;
import de.pottgames.anemulator.cpu.instructions.DecL;
import de.pottgames.anemulator.cpu.instructions.Dec_HL_;
import de.pottgames.anemulator.cpu.instructions.Di;
import de.pottgames.anemulator.cpu.instructions.Ei;
import de.pottgames.anemulator.cpu.instructions.IncA;
import de.pottgames.anemulator.cpu.instructions.IncB;
import de.pottgames.anemulator.cpu.instructions.IncBC;
import de.pottgames.anemulator.cpu.instructions.IncC;
import de.pottgames.anemulator.cpu.instructions.IncD;
import de.pottgames.anemulator.cpu.instructions.IncDE;
import de.pottgames.anemulator.cpu.instructions.IncE;
import de.pottgames.anemulator.cpu.instructions.IncH;
import de.pottgames.anemulator.cpu.instructions.IncHL;
import de.pottgames.anemulator.cpu.instructions.IncL;
import de.pottgames.anemulator.cpu.instructions.IncSP;
import de.pottgames.anemulator.cpu.instructions.Inc_HL_;
import de.pottgames.anemulator.cpu.instructions.Jpa16;
import de.pottgames.anemulator.cpu.instructions.JrCr8;
import de.pottgames.anemulator.cpu.instructions.JrNCr8;
import de.pottgames.anemulator.cpu.instructions.JrNZr8;
import de.pottgames.anemulator.cpu.instructions.JrZr8;
import de.pottgames.anemulator.cpu.instructions.Jrr8;
import de.pottgames.anemulator.cpu.instructions.LdAA;
import de.pottgames.anemulator.cpu.instructions.LdAE;
import de.pottgames.anemulator.cpu.instructions.LdA_BC_;
import de.pottgames.anemulator.cpu.instructions.LdA_DE_;
import de.pottgames.anemulator.cpu.instructions.LdA_HL_;
import de.pottgames.anemulator.cpu.instructions.LdAd8;
import de.pottgames.anemulator.cpu.instructions.LdBA;
import de.pottgames.anemulator.cpu.instructions.LdBCd16;
import de.pottgames.anemulator.cpu.instructions.LdBd8;
import de.pottgames.anemulator.cpu.instructions.LdCA;
import de.pottgames.anemulator.cpu.instructions.LdCd8;
import de.pottgames.anemulator.cpu.instructions.LdDA;
import de.pottgames.anemulator.cpu.instructions.LdDEd16;
import de.pottgames.anemulator.cpu.instructions.LdDd8;
import de.pottgames.anemulator.cpu.instructions.LdEA;
import de.pottgames.anemulator.cpu.instructions.LdEd8;
import de.pottgames.anemulator.cpu.instructions.LdHA;
import de.pottgames.anemulator.cpu.instructions.LdHLd16;
import de.pottgames.anemulator.cpu.instructions.LdHd8;
import de.pottgames.anemulator.cpu.instructions.LdLA;
import de.pottgames.anemulator.cpu.instructions.LdLd8;
import de.pottgames.anemulator.cpu.instructions.LdSPd16;
import de.pottgames.anemulator.cpu.instructions.Ld_BC_A;
import de.pottgames.anemulator.cpu.instructions.Ld_C_A;
import de.pottgames.anemulator.cpu.instructions.Ld_DE_A;
import de.pottgames.anemulator.cpu.instructions.Ld_HL_A;
import de.pottgames.anemulator.cpu.instructions.Ld_HLminus_A;
import de.pottgames.anemulator.cpu.instructions.Ld_HLplus_A;
import de.pottgames.anemulator.cpu.instructions.Ld_a16_A;
import de.pottgames.anemulator.cpu.instructions.LdhA_a8_;
import de.pottgames.anemulator.cpu.instructions.Ldh_a8_A;
import de.pottgames.anemulator.cpu.instructions.Nop;
import de.pottgames.anemulator.cpu.instructions.PopBC;
import de.pottgames.anemulator.cpu.instructions.PopDE;
import de.pottgames.anemulator.cpu.instructions.PopHL;
import de.pottgames.anemulator.cpu.instructions.PrefixCB;
import de.pottgames.anemulator.cpu.instructions.PushBC;
import de.pottgames.anemulator.cpu.instructions.PushDE;
import de.pottgames.anemulator.cpu.instructions.PushHL;
import de.pottgames.anemulator.cpu.instructions.RLA;
import de.pottgames.anemulator.cpu.instructions.Ret;
import de.pottgames.anemulator.cpu.instructions.Rst00H;
import de.pottgames.anemulator.cpu.instructions.Rst08H;
import de.pottgames.anemulator.cpu.instructions.Rst10H;
import de.pottgames.anemulator.cpu.instructions.Rst18H;
import de.pottgames.anemulator.cpu.instructions.Rst20H;
import de.pottgames.anemulator.cpu.instructions.Rst28H;
import de.pottgames.anemulator.cpu.instructions.Rst30H;
import de.pottgames.anemulator.cpu.instructions.Rst38H;
import de.pottgames.anemulator.cpu.instructions.XorA;
import de.pottgames.anemulator.cpu.instructions.XorB;
import de.pottgames.anemulator.cpu.instructions.XorC;
import de.pottgames.anemulator.cpu.instructions.XorD;
import de.pottgames.anemulator.cpu.instructions.XorE;
import de.pottgames.anemulator.cpu.instructions.XorH;
import de.pottgames.anemulator.cpu.instructions.XorL;
import de.pottgames.anemulator.cpu.instructions.Xor_HL_;
import de.pottgames.anemulator.cpu.instructions.Xord8;
import de.pottgames.anemulator.error.UnsupportedFeatureException;
import de.pottgames.anemulator.memory.MemoryController;

public class CPU {
    /**
     * Interrupt Flag Register
     */
    private static final int IF_ADDRESS = 0xFF0F;

    /**
     * Interrupt Enable Register
     */
    private static final int IE_ADDRESS = 0xFFFF;

    private final MemoryController    memory;
    private final Register            register;
    private final IntMap<Instruction> instructions     = new IntMap<>();
    private int                       cycleAccumulator = 0;


    public CPU(MemoryController memory) {
        System.out.println("CPU INIT");
        this.memory = memory;
        this.register = new Register();
        this.register.programCounter = memory.isBootstrap() ? 0 : 0x100;

        this.initInstructions();
    }


    private void initInstructions() {
        this.instructions.put(0x00, new Nop(this.register, this.memory));
        this.instructions.put(0x01, new LdBCd16(this.register, this.memory));
        this.instructions.put(0x02, new Ld_BC_A(this.register, this.memory));
        this.instructions.put(0x03, new IncBC(this.register, this.memory));
        this.instructions.put(0x04, new IncB(this.register, this.memory));
        this.instructions.put(0x05, new DecB(this.register, this.memory));
        this.instructions.put(0x06, new LdBd8(this.register, this.memory));
        this.instructions.put(0x07, null);
        this.instructions.put(0x08, null);
        this.instructions.put(0x09, null);
        this.instructions.put(0x0A, new LdA_BC_(this.register, this.memory));
        this.instructions.put(0x0B, null);
        this.instructions.put(0x0C, new IncC(this.register, this.memory));
        this.instructions.put(0x0D, new DecC(this.register, this.memory));
        this.instructions.put(0x0E, new LdCd8(this.register, this.memory));
        this.instructions.put(0x0F, null);

        this.instructions.put(0x10, null);
        this.instructions.put(0x11, new LdDEd16(this.register, this.memory));
        this.instructions.put(0x12, new Ld_DE_A(this.register, this.memory));
        this.instructions.put(0x13, new IncDE(this.register, this.memory));
        this.instructions.put(0x14, new IncD(this.register, this.memory));
        this.instructions.put(0x15, new DecD(this.register, this.memory));
        this.instructions.put(0x16, new LdDd8(this.register, this.memory));
        this.instructions.put(0x17, new RLA(this.register, this.memory));
        this.instructions.put(0x18, new Jrr8(this.register, this.memory));
        this.instructions.put(0x19, null);
        this.instructions.put(0x1A, new LdA_DE_(this.register, this.memory));
        this.instructions.put(0x1B, null);
        this.instructions.put(0x1C, new IncE(this.register, this.memory));
        this.instructions.put(0x1D, new DecE(this.register, this.memory));
        this.instructions.put(0x1E, new LdEd8(this.register, this.memory));
        this.instructions.put(0x1F, null);

        this.instructions.put(0x20, new JrNZr8(this.register, this.memory));
        this.instructions.put(0x21, new LdHLd16(this.register, this.memory));
        this.instructions.put(0x22, new Ld_HLplus_A(this.register, this.memory));
        this.instructions.put(0x23, new IncHL(this.register, this.memory));
        this.instructions.put(0x24, new IncH(this.register, this.memory));
        this.instructions.put(0x25, new DecH(this.register, this.memory));
        this.instructions.put(0x26, new LdHd8(this.register, this.memory));
        this.instructions.put(0x27, null);
        this.instructions.put(0x28, new JrZr8(this.register, this.memory));
        this.instructions.put(0x29, null);
        this.instructions.put(0x2A, null);
        this.instructions.put(0x2B, null);
        this.instructions.put(0x2C, new IncL(this.register, this.memory));
        this.instructions.put(0x2D, new DecL(this.register, this.memory));
        this.instructions.put(0x2E, new LdLd8(this.register, this.memory));
        this.instructions.put(0x2F, null);

        this.instructions.put(0x30, new JrNCr8(this.register, this.memory));
        this.instructions.put(0x31, new LdSPd16(this.register, this.memory));
        this.instructions.put(0x32, new Ld_HLminus_A(this.register, this.memory));
        this.instructions.put(0x33, new IncSP(this.register, this.memory));
        this.instructions.put(0x34, new Inc_HL_(this.register, this.memory));
        this.instructions.put(0x35, new Dec_HL_(this.register, this.memory));
        this.instructions.put(0x36, null);
        this.instructions.put(0x37, null);
        this.instructions.put(0x38, new JrCr8(this.register, this.memory));
        this.instructions.put(0x39, null);
        this.instructions.put(0x3A, null);
        this.instructions.put(0x3B, null);
        this.instructions.put(0x3C, new IncA(this.register, this.memory));
        this.instructions.put(0x3D, new DecA(this.register, this.memory));
        this.instructions.put(0x3E, new LdAd8(this.register, this.memory));
        this.instructions.put(0x3F, null);

        this.instructions.put(0x40, null);
        this.instructions.put(0x41, null);
        this.instructions.put(0x42, null);
        this.instructions.put(0x43, null);
        this.instructions.put(0x44, null);
        this.instructions.put(0x45, null);
        this.instructions.put(0x46, null);
        this.instructions.put(0x47, new LdBA(this.register, this.memory));
        this.instructions.put(0x48, null);
        this.instructions.put(0x49, null);
        this.instructions.put(0x4A, null);
        this.instructions.put(0x4B, null);
        this.instructions.put(0x4C, null);
        this.instructions.put(0x4D, null);
        this.instructions.put(0x4E, null);
        this.instructions.put(0x4F, new LdCA(this.register, this.memory));

        this.instructions.put(0x50, null);
        this.instructions.put(0x51, null);
        this.instructions.put(0x52, null);
        this.instructions.put(0x53, null);
        this.instructions.put(0x54, null);
        this.instructions.put(0x55, null);
        this.instructions.put(0x56, null);
        this.instructions.put(0x57, new LdDA(this.register, this.memory));
        this.instructions.put(0x58, null);
        this.instructions.put(0x59, null);
        this.instructions.put(0x5A, null);
        this.instructions.put(0x5B, null);
        this.instructions.put(0x5C, null);
        this.instructions.put(0x5D, null);
        this.instructions.put(0x5E, null);
        this.instructions.put(0x5F, new LdEA(this.register, this.memory));

        this.instructions.put(0x60, null);
        this.instructions.put(0x61, null);
        this.instructions.put(0x62, null);
        this.instructions.put(0x63, null);
        this.instructions.put(0x64, null);
        this.instructions.put(0x65, null);
        this.instructions.put(0x66, null);
        this.instructions.put(0x67, new LdHA(this.register, this.memory));
        this.instructions.put(0x68, null);
        this.instructions.put(0x69, null);
        this.instructions.put(0x6A, null);
        this.instructions.put(0x6B, null);
        this.instructions.put(0x6C, null);
        this.instructions.put(0x6D, null);
        this.instructions.put(0x6E, null);
        this.instructions.put(0x6F, new LdLA(this.register, this.memory));

        this.instructions.put(0x70, null);
        this.instructions.put(0x71, null);
        this.instructions.put(0x72, null);
        this.instructions.put(0x73, null);
        this.instructions.put(0x74, null);
        this.instructions.put(0x75, null);
        this.instructions.put(0x76, null);
        this.instructions.put(0x77, new Ld_HL_A(this.register, this.memory));
        this.instructions.put(0x78, null);
        this.instructions.put(0x79, null);
        this.instructions.put(0x7A, null);
        this.instructions.put(0x7B, new LdAE(this.register, this.memory));
        this.instructions.put(0x7C, null);
        this.instructions.put(0x7D, null);
        this.instructions.put(0x7E, new LdA_HL_(this.register, this.memory));
        this.instructions.put(0x7F, new LdAA(this.register, this.memory));

        this.instructions.put(0x80, null);
        this.instructions.put(0x81, null);
        this.instructions.put(0x82, null);
        this.instructions.put(0x83, null);
        this.instructions.put(0x84, null);
        this.instructions.put(0x85, null);
        this.instructions.put(0x86, null);
        this.instructions.put(0x87, null);
        this.instructions.put(0x88, null);
        this.instructions.put(0x89, null);
        this.instructions.put(0x8A, null);
        this.instructions.put(0x8B, null);
        this.instructions.put(0x8C, null);
        this.instructions.put(0x8D, null);
        this.instructions.put(0x8E, null);
        this.instructions.put(0x8F, null);

        this.instructions.put(0x90, null);
        this.instructions.put(0x91, null);
        this.instructions.put(0x92, null);
        this.instructions.put(0x93, null);
        this.instructions.put(0x94, null);
        this.instructions.put(0x95, null);
        this.instructions.put(0x96, null);
        this.instructions.put(0x97, null);
        this.instructions.put(0x98, null);
        this.instructions.put(0x99, null);
        this.instructions.put(0x9A, null);
        this.instructions.put(0x9B, null);
        this.instructions.put(0x9C, null);
        this.instructions.put(0x9D, null);
        this.instructions.put(0x9E, null);
        this.instructions.put(0x9F, null);

        this.instructions.put(0xA0, null);
        this.instructions.put(0xA1, null);
        this.instructions.put(0xA2, null);
        this.instructions.put(0xA3, null);
        this.instructions.put(0xA4, null);
        this.instructions.put(0xA5, null);
        this.instructions.put(0xA6, null);
        this.instructions.put(0xA7, null);
        this.instructions.put(0xA8, new XorB(this.register, this.memory));
        this.instructions.put(0xA9, new XorC(this.register, this.memory));
        this.instructions.put(0xAA, new XorD(this.register, this.memory));
        this.instructions.put(0xAB, new XorE(this.register, this.memory));
        this.instructions.put(0xAC, new XorH(this.register, this.memory));
        this.instructions.put(0xAD, new XorL(this.register, this.memory));
        this.instructions.put(0xAE, new Xor_HL_(this.register, this.memory));
        this.instructions.put(0xAF, new XorA(this.register, this.memory));

        this.instructions.put(0xB0, null);
        this.instructions.put(0xB1, null);
        this.instructions.put(0xB2, null);
        this.instructions.put(0xB3, null);
        this.instructions.put(0xB4, null);
        this.instructions.put(0xB5, null);
        this.instructions.put(0xB6, null);
        this.instructions.put(0xB7, null);
        this.instructions.put(0xB8, new CpB(this.register, this.memory));
        this.instructions.put(0xB9, new CpC(this.register, this.memory));
        this.instructions.put(0xBA, new CpD(this.register, this.memory));
        this.instructions.put(0xBB, new CpE(this.register, this.memory));
        this.instructions.put(0xBC, new CpH(this.register, this.memory));
        this.instructions.put(0xBD, new CpL(this.register, this.memory));
        this.instructions.put(0xBE, new Cp_HL_(this.register, this.memory));
        this.instructions.put(0xBF, new CpA(this.register, this.memory));

        this.instructions.put(0xC0, null);
        this.instructions.put(0xC1, new PopBC(this.register, this.memory));
        this.instructions.put(0xC2, null);
        this.instructions.put(0xC3, new Jpa16(this.register, this.memory));
        this.instructions.put(0xC4, null);
        this.instructions.put(0xC5, new PushBC(this.register, this.memory));
        this.instructions.put(0xC6, null);
        this.instructions.put(0xC7, new Rst00H(this.register, this.memory));
        this.instructions.put(0xC8, null);
        this.instructions.put(0xC9, new Ret(this.register, this.memory));
        this.instructions.put(0xCA, null);
        this.instructions.put(0xCB, new PrefixCB(this.register, this.memory));
        this.instructions.put(0xCC, null);
        this.instructions.put(0xCD, new Calla16(this.register, this.memory));
        this.instructions.put(0xCE, null);
        this.instructions.put(0xCF, new Rst08H(this.register, this.memory));

        this.instructions.put(0xD0, null);
        this.instructions.put(0xD1, new PopDE(this.register, this.memory));
        this.instructions.put(0xD2, null);
        this.instructions.put(0xD4, null);
        this.instructions.put(0xD5, new PushDE(this.register, this.memory));
        this.instructions.put(0xD6, null);
        this.instructions.put(0xD7, new Rst10H(this.register, this.memory));
        this.instructions.put(0xD8, null);
        this.instructions.put(0xD9, null);
        this.instructions.put(0xDA, null);
        this.instructions.put(0xDC, null);
        this.instructions.put(0xDE, null);
        this.instructions.put(0xDF, new Rst18H(this.register, this.memory));

        this.instructions.put(0xE0, new Ldh_a8_A(this.register, this.memory));
        this.instructions.put(0xE1, new PopHL(this.register, this.memory));
        this.instructions.put(0xE2, new Ld_C_A(this.register, this.memory));
        this.instructions.put(0xE5, new PushHL(this.register, this.memory));
        this.instructions.put(0xE6, null);
        this.instructions.put(0xE7, new Rst20H(this.register, this.memory));
        this.instructions.put(0xE8, null);
        this.instructions.put(0xE9, null);
        this.instructions.put(0xEA, new Ld_a16_A(this.register, this.memory));
        this.instructions.put(0xEE, new Xord8(this.register, this.memory));
        this.instructions.put(0xEF, new Rst28H(this.register, this.memory));

        this.instructions.put(0xF0, new LdhA_a8_(this.register, this.memory));
        this.instructions.put(0xF1, null);
        this.instructions.put(0xF2, null);
        this.instructions.put(0xF3, new Di(this.register, this.memory));
        this.instructions.put(0xF5, null);
        this.instructions.put(0xF6, null);
        this.instructions.put(0xF7, new Rst30H(this.register, this.memory));
        this.instructions.put(0xF8, null);
        this.instructions.put(0xF9, null);
        this.instructions.put(0xFA, null);
        this.instructions.put(0xFB, new Ei(this.register, this.memory));
        this.instructions.put(0xFE, new Cpd8(this.register, this.memory));
        this.instructions.put(0xFF, new Rst38H(this.register, this.memory));
    }


    public void step() {
        this.cycleAccumulator += 4;

        if (this.cycleAccumulator > 0) {
            if (this.register.isInterruptsEnabled()) {
                if (this.handleInterrupts()) {
                    this.register.step();
                    return;
                }
            }

            final int opCode = this.memory.read8Bit(this.register.programCounter);
            this.register.programCounter++;
            System.out.println("running opcode: " + Integer.toHexString(opCode));
            System.out.println("pc: " + Integer.toHexString(this.register.programCounter - 1));

            this.cycleAccumulator -= this.runInstruction(opCode);
            this.register.step();
        }
    }


    private boolean handleInterrupts() {
        final int enabledRegister = this.memory.read8Bit(CPU.IE_ADDRESS);
        int flagRegister = this.memory.read8Bit(CPU.IF_ADDRESS);
        if (enabledRegister > 0 && flagRegister > 0) {
            for (final Interrupt interrupt : Interrupt.values()) {
                final int mask = interrupt.getFlagMask();
                if ((enabledRegister & mask) > 0 && (flagRegister & mask) > 0) {
                    // PUSH PC TO THE STACK
                    this.register.stackPointer--;
                    this.memory.write(this.register.stackPointer, this.register.programCounter >>> 8);
                    this.register.stackPointer--;
                    this.memory.write(this.register.stackPointer, this.register.programCounter & 0xff);

                    // JUMP
                    this.register.programCounter = interrupt.getJumpAddress();

                    // CLEAR IME AND IF
                    this.register.setInterruptsEnabled(false);
                    this.memory.write(CPU.IF_ADDRESS, flagRegister &= ~(1 << mask));

                    this.cycleAccumulator -= 20;
                    return true;
                }
            }
        }

        return false;
    }


    private int runInstruction(int opCode) {
        final Instruction instruction = this.instructions.get(opCode);

        if (instruction == null) {
            throw new UnsupportedFeatureException("Unsupported opCode: " + Integer.toHexString(opCode));
        }

        return instruction.run();
    }

}
