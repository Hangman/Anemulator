package de.pottgames.anemulator.cpu;

import com.badlogic.gdx.utils.IntMap;

import de.pottgames.anemulator.cpu.instructions.*;
import de.pottgames.anemulator.error.UnsupportedFeatureException;
import de.pottgames.anemulator.input.JoypadKey;
import de.pottgames.anemulator.input.JoypadStateChangeListener;
import de.pottgames.anemulator.memory.MemoryBankController;

public class CPU implements JoypadStateChangeListener {
    private final MemoryBankController memory;
    private final Register             register;
    private final IntMap<Instruction>  instructions        = new IntMap<>();
    private final DividerTimer         dividerTimer;
    private final Timer                timer;
    private int                        cycleAccumulator    = 0;
    private boolean                    halted              = false;
    private boolean                    skipNextInstruction = false;


    public CPU(MemoryBankController memory) {
        System.out.println("CPU INIT");
        this.memory = memory;
        this.register = new Register();
        this.dividerTimer = new DividerTimer(memory);
        this.timer = new Timer(memory);
        this.register.pc = 0x100;

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
        this.instructions.put(0x07, new RlcA(this.register, this.memory));
        this.instructions.put(0x08, new Ld_a16_SP(this.register, this.memory));
        this.instructions.put(0x09, new AddHLBC(this.register, this.memory));
        this.instructions.put(0x0A, new LdA_BC_(this.register, this.memory));
        this.instructions.put(0x0B, new DecBC(this.register, this.memory));
        this.instructions.put(0x0C, new IncC(this.register, this.memory));
        this.instructions.put(0x0D, new DecC(this.register, this.memory));
        this.instructions.put(0x0E, new LdCd8(this.register, this.memory));
        this.instructions.put(0x0F, new RrcA(this.register, this.memory));

        this.instructions.put(0x10, null);
        this.instructions.put(0x11, new LdDEd16(this.register, this.memory));
        this.instructions.put(0x12, new Ld_DE_A(this.register, this.memory));
        this.instructions.put(0x13, new IncDE(this.register, this.memory));
        this.instructions.put(0x14, new IncD(this.register, this.memory));
        this.instructions.put(0x15, new DecD(this.register, this.memory));
        this.instructions.put(0x16, new LdDd8(this.register, this.memory));
        this.instructions.put(0x17, new RLA(this.register, this.memory));
        this.instructions.put(0x18, new Jrr8(this.register, this.memory));
        this.instructions.put(0x19, new AddHLDE(this.register, this.memory));
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
        this.instructions.put(0x29, new AddHLHL(this.register, this.memory));
        this.instructions.put(0x2A, new LdA_HLplus_(this.register, this.memory));
        this.instructions.put(0x2B, null);
        this.instructions.put(0x2C, new IncL(this.register, this.memory));
        this.instructions.put(0x2D, new DecL(this.register, this.memory));
        this.instructions.put(0x2E, new LdLd8(this.register, this.memory));
        this.instructions.put(0x2F, new CplA(this.register, this.memory));

        this.instructions.put(0x30, new JrNCr8(this.register, this.memory));
        this.instructions.put(0x31, new LdSPd16(this.register, this.memory));
        this.instructions.put(0x32, new Ld_HLminus_A(this.register, this.memory));
        this.instructions.put(0x33, new IncSP(this.register, this.memory));
        this.instructions.put(0x34, new Inc_HL_(this.register, this.memory));
        this.instructions.put(0x35, new Dec_HL_(this.register, this.memory));
        this.instructions.put(0x36, new Ld_HL_d8(this.register, this.memory));
        this.instructions.put(0x37, null);
        this.instructions.put(0x38, new JrCr8(this.register, this.memory));
        this.instructions.put(0x39, new AddHLSP(this.register, this.memory));
        this.instructions.put(0x3A, new LdA_HLminus_(this.register, this.memory));
        this.instructions.put(0x3B, null);
        this.instructions.put(0x3C, new IncA(this.register, this.memory));
        this.instructions.put(0x3D, new DecA(this.register, this.memory));
        this.instructions.put(0x3E, new LdAd8(this.register, this.memory));
        this.instructions.put(0x3F, null);

        this.instructions.put(0x40, null);
        this.instructions.put(0x41, null);
        this.instructions.put(0x42, null);
        this.instructions.put(0x43, null);
        this.instructions.put(0x44, new LdBH(this.register, this.memory));
        this.instructions.put(0x45, null);
        this.instructions.put(0x46, null);
        this.instructions.put(0x47, new LdBA(this.register, this.memory));
        this.instructions.put(0x48, null);
        this.instructions.put(0x49, null);
        this.instructions.put(0x4A, null);
        this.instructions.put(0x4B, null);
        this.instructions.put(0x4C, null);
        this.instructions.put(0x4D, new LdCL(this.register, this.memory));
        this.instructions.put(0x4E, null);
        this.instructions.put(0x4F, new LdCA(this.register, this.memory));

        this.instructions.put(0x50, null);
        this.instructions.put(0x51, null);
        this.instructions.put(0x52, null);
        this.instructions.put(0x53, null);
        this.instructions.put(0x54, null);
        this.instructions.put(0x55, null);
        this.instructions.put(0x56, new LdD_HL_(this.register, this.memory));
        this.instructions.put(0x57, new LdDA(this.register, this.memory));
        this.instructions.put(0x58, null);
        this.instructions.put(0x59, null);
        this.instructions.put(0x5A, null);
        this.instructions.put(0x5B, null);
        this.instructions.put(0x5C, null);
        this.instructions.put(0x5D, null);
        this.instructions.put(0x5E, new LdE_HL_(this.register, this.memory));
        this.instructions.put(0x5F, new LdEA(this.register, this.memory));

        this.instructions.put(0x60, null);
        this.instructions.put(0x61, null);
        this.instructions.put(0x62, null);
        this.instructions.put(0x63, null);
        this.instructions.put(0x64, null);
        this.instructions.put(0x65, null);
        this.instructions.put(0x66, new LdH_HL_(this.register, this.memory));
        this.instructions.put(0x67, new LdHA(this.register, this.memory));
        this.instructions.put(0x68, new LdLB(this.register, this.memory));
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
        this.instructions.put(0x76, new Halt(this.register, this.memory, this));
        this.instructions.put(0x77, new Ld_HL_A(this.register, this.memory));
        this.instructions.put(0x78, new LdAB(this.register, this.memory));
        this.instructions.put(0x79, new LdAC(this.register, this.memory));
        this.instructions.put(0x7A, new LdAD(this.register, this.memory));
        this.instructions.put(0x7B, new LdAE(this.register, this.memory));
        this.instructions.put(0x7C, new LdAH(this.register, this.memory));
        this.instructions.put(0x7D, new LdAL(this.register, this.memory));
        this.instructions.put(0x7E, new LdA_HL_(this.register, this.memory));
        this.instructions.put(0x7F, new LdAA(this.register, this.memory));

        this.instructions.put(0x80, new AddAB(this.register, this.memory));
        this.instructions.put(0x81, new AddAC(this.register, this.memory));
        this.instructions.put(0x82, new AddAD(this.register, this.memory));
        this.instructions.put(0x83, new AddAE(this.register, this.memory));
        this.instructions.put(0x84, new AddAH(this.register, this.memory));
        this.instructions.put(0x85, new AddAL(this.register, this.memory));
        this.instructions.put(0x86, new AddA_HL_(this.register, this.memory));
        this.instructions.put(0x87, new AddAA(this.register, this.memory));
        this.instructions.put(0x88, null);
        this.instructions.put(0x89, null);
        this.instructions.put(0x8A, null);
        this.instructions.put(0x8B, null);
        this.instructions.put(0x8C, null);
        this.instructions.put(0x8D, null);
        this.instructions.put(0x8E, null);
        this.instructions.put(0x8F, null);

        this.instructions.put(0x90, new SubB(this.register, this.memory));
        this.instructions.put(0x91, new SubC(this.register, this.memory));
        this.instructions.put(0x92, new SubD(this.register, this.memory));
        this.instructions.put(0x93, new SubE(this.register, this.memory));
        this.instructions.put(0x94, new SubH(this.register, this.memory));
        this.instructions.put(0x95, new SubL(this.register, this.memory));
        this.instructions.put(0x96, new Sub_HL_(this.register, this.memory));
        this.instructions.put(0x97, new SubA(this.register, this.memory));
        this.instructions.put(0x98, null);
        this.instructions.put(0x99, null);
        this.instructions.put(0x9A, null);
        this.instructions.put(0x9B, null);
        this.instructions.put(0x9C, null);
        this.instructions.put(0x9D, null);
        this.instructions.put(0x9E, null);
        this.instructions.put(0x9F, null);

        this.instructions.put(0xA0, new AndB(this.register, this.memory));
        this.instructions.put(0xA1, new AndC(this.register, this.memory));
        this.instructions.put(0xA2, new AndD(this.register, this.memory));
        this.instructions.put(0xA3, new AndE(this.register, this.memory));
        this.instructions.put(0xA4, new AndH(this.register, this.memory));
        this.instructions.put(0xA5, new AndL(this.register, this.memory));
        this.instructions.put(0xA6, new And_HL_(this.register, this.memory));
        this.instructions.put(0xA7, new AndA(this.register, this.memory));
        this.instructions.put(0xA8, new XorB(this.register, this.memory));
        this.instructions.put(0xA9, new XorC(this.register, this.memory));
        this.instructions.put(0xAA, new XorD(this.register, this.memory));
        this.instructions.put(0xAB, new XorE(this.register, this.memory));
        this.instructions.put(0xAC, new XorH(this.register, this.memory));
        this.instructions.put(0xAD, new XorL(this.register, this.memory));
        this.instructions.put(0xAE, new Xor_HL_(this.register, this.memory));
        this.instructions.put(0xAF, new XorA(this.register, this.memory));

        this.instructions.put(0xB0, new OrB(this.register, this.memory));
        this.instructions.put(0xB1, new OrC(this.register, this.memory));
        this.instructions.put(0xB2, new OrD(this.register, this.memory));
        this.instructions.put(0xB3, new OrE(this.register, this.memory));
        this.instructions.put(0xB4, new OrH(this.register, this.memory));
        this.instructions.put(0xB5, new OrL(this.register, this.memory));
        this.instructions.put(0xB6, new Or_HL_(this.register, this.memory));
        this.instructions.put(0xB7, new OrA(this.register, this.memory));
        this.instructions.put(0xB8, new CpB(this.register, this.memory));
        this.instructions.put(0xB9, new CpC(this.register, this.memory));
        this.instructions.put(0xBA, new CpD(this.register, this.memory));
        this.instructions.put(0xBB, new CpE(this.register, this.memory));
        this.instructions.put(0xBC, new CpH(this.register, this.memory));
        this.instructions.put(0xBD, new CpL(this.register, this.memory));
        this.instructions.put(0xBE, new Cp_HL_(this.register, this.memory));
        this.instructions.put(0xBF, new CpA(this.register, this.memory));

        this.instructions.put(0xC0, new RetNZ(this.register, this.memory));
        this.instructions.put(0xC1, new PopBC(this.register, this.memory));
        this.instructions.put(0xC2, new JpNZa16(this.register, this.memory));
        this.instructions.put(0xC3, new Jpa16(this.register, this.memory));
        this.instructions.put(0xC4, new CallNZa16(this.register, this.memory));
        this.instructions.put(0xC5, new PushBC(this.register, this.memory));
        this.instructions.put(0xC6, new AddAd8(this.register, this.memory));
        this.instructions.put(0xC7, new Rst00H(this.register, this.memory));
        this.instructions.put(0xC8, new RetZ(this.register, this.memory));
        this.instructions.put(0xC9, new Ret(this.register, this.memory));
        this.instructions.put(0xCA, new JpZa16(this.register, this.memory));
        this.instructions.put(0xCB, new PrefixCB(this.register, this.memory));
        this.instructions.put(0xCC, new CallZa16(this.register, this.memory));
        this.instructions.put(0xCD, new Calla16(this.register, this.memory));
        this.instructions.put(0xCE, null);
        this.instructions.put(0xCF, new Rst08H(this.register, this.memory));

        this.instructions.put(0xD0, new RetNC(this.register, this.memory));
        this.instructions.put(0xD1, new PopDE(this.register, this.memory));
        this.instructions.put(0xD2, new JpNCa16(this.register, this.memory));
        this.instructions.put(0xD4, new CallNCa16(this.register, this.memory));
        this.instructions.put(0xD5, new PushDE(this.register, this.memory));
        this.instructions.put(0xD6, null);
        this.instructions.put(0xD7, new Rst10H(this.register, this.memory));
        this.instructions.put(0xD8, new RetC(this.register, this.memory));
        this.instructions.put(0xD9, new RetI(this.register, this.memory));
        this.instructions.put(0xDA, new JpCa16(this.register, this.memory));
        this.instructions.put(0xDC, new CallCa16(this.register, this.memory));
        this.instructions.put(0xDE, null);
        this.instructions.put(0xDF, new Rst18H(this.register, this.memory));

        this.instructions.put(0xE0, new Ldh_a8_A(this.register, this.memory));
        this.instructions.put(0xE1, new PopHL(this.register, this.memory));
        this.instructions.put(0xE2, new Ld_C_A(this.register, this.memory));
        this.instructions.put(0xE5, new PushHL(this.register, this.memory));
        this.instructions.put(0xE6, new Andd8(this.register, this.memory));
        this.instructions.put(0xE7, new Rst20H(this.register, this.memory));
        this.instructions.put(0xE8, null);
        this.instructions.put(0xE9, new Jp_HL_(this.register, this.memory));
        this.instructions.put(0xEA, new Ld_a16_A(this.register, this.memory));
        this.instructions.put(0xEE, new Xord8(this.register, this.memory));
        this.instructions.put(0xEF, new Rst28H(this.register, this.memory));

        this.instructions.put(0xF0, new LdhA_a8_(this.register, this.memory));
        this.instructions.put(0xF1, new PopAF(this.register, this.memory));
        this.instructions.put(0xF2, null);
        this.instructions.put(0xF3, new Di(this.register, this.memory));
        this.instructions.put(0xF5, new PushAF(this.register, this.memory));
        this.instructions.put(0xF6, null);
        this.instructions.put(0xF7, new Rst30H(this.register, this.memory));
        this.instructions.put(0xF8, null);
        this.instructions.put(0xF9, null);
        this.instructions.put(0xFA, new LdA_a16_(this.register, this.memory));
        this.instructions.put(0xFB, new Ei(this.register, this.memory));
        this.instructions.put(0xFE, new Cpd8(this.register, this.memory));
        this.instructions.put(0xFF, new Rst38H(this.register, this.memory));
    }


    public void step() {
        this.cycleAccumulator += 4;
        this.dividerTimer.step();
        if (this.timer.step()) {
            int ifRegister = this.memory.read8Bit(MemoryBankController.IF);
            this.memory.write(MemoryBankController.IF, ifRegister |= 1 << Interrupt.TIMER.getBitnum());
        }

        if (this.skipNextInstruction) {
            this.register.pc++;
            this.skipNextInstruction = false;
            return;
        }

        if (this.cycleAccumulator > 0) {
            if (this.register.isInterruptsEnabled()) {
                if (this.handleInterrupts()) {
                    this.halted = false;
                    this.register.step();
                    return;
                }
            }

            if (this.halted) {
                return;
            }

            final int opCode = this.memory.read8Bit(this.register.pc);
            this.register.pc++;

            this.cycleAccumulator -= this.runInstruction(opCode);
            this.register.step();
        }
    }


    private boolean handleInterrupts() {
        final int enabledRegister = this.memory.read8Bit(MemoryBankController.IE);
        final int flagRegister = this.memory.read8Bit(MemoryBankController.IF);
        if (enabledRegister > 0 && flagRegister > 0) {
            for (final Interrupt interrupt : Interrupt.values()) {
                final int mask = interrupt.getFlagMask();
                if ((enabledRegister & mask) > 0 && (flagRegister & mask) > 0) {
                    // PUSH PC TO THE STACK
                    this.register.sp--;
                    this.memory.write(this.register.sp, this.register.pc >>> 8);
                    this.register.sp--;
                    this.memory.write(this.register.sp, this.register.pc & 0xFF);

                    // JUMP
                    this.register.pc = interrupt.getJumpAddress();

                    // CLEAR IME AND IF
                    this.register.setInterruptsEnabled(false);
                    this.memory.setBit(MemoryBankController.IF, interrupt.getBitnum(), false);

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


    public void halt() {
        if (this.register.isInterruptsEnabled()) {
            this.halted = true;
        } else {
            this.skipNextInstruction = true;
        }
    }


    @Override
    public void onJoypadStateChange(JoypadKey key, boolean pressed) {
        this.memory.setBit(MemoryBankController.IF, Interrupt.JOYPAD.getBitnum(), true);
    }

}
