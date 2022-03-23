package de.pottgames.anemulator.cpu.instructions;

import com.badlogic.gdx.utils.IntMap;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.extendedinstructions.Bit7H;
import de.pottgames.anemulator.cpu.extendedinstructions.Res0A;
import de.pottgames.anemulator.cpu.extendedinstructions.Res0B;
import de.pottgames.anemulator.cpu.extendedinstructions.Res0C;
import de.pottgames.anemulator.cpu.extendedinstructions.Res0D;
import de.pottgames.anemulator.cpu.extendedinstructions.Res0E;
import de.pottgames.anemulator.cpu.extendedinstructions.Res0H;
import de.pottgames.anemulator.cpu.extendedinstructions.Res0L;
import de.pottgames.anemulator.cpu.extendedinstructions.Res0_HL_;
import de.pottgames.anemulator.cpu.extendedinstructions.Res1A;
import de.pottgames.anemulator.cpu.extendedinstructions.Res1B;
import de.pottgames.anemulator.cpu.extendedinstructions.Res1C;
import de.pottgames.anemulator.cpu.extendedinstructions.Res1D;
import de.pottgames.anemulator.cpu.extendedinstructions.Res1E;
import de.pottgames.anemulator.cpu.extendedinstructions.Res1H;
import de.pottgames.anemulator.cpu.extendedinstructions.Res1L;
import de.pottgames.anemulator.cpu.extendedinstructions.Res1_HL_;
import de.pottgames.anemulator.cpu.extendedinstructions.Res2A;
import de.pottgames.anemulator.cpu.extendedinstructions.Res2B;
import de.pottgames.anemulator.cpu.extendedinstructions.Res2C;
import de.pottgames.anemulator.cpu.extendedinstructions.Res2D;
import de.pottgames.anemulator.cpu.extendedinstructions.Res2E;
import de.pottgames.anemulator.cpu.extendedinstructions.Res2H;
import de.pottgames.anemulator.cpu.extendedinstructions.Res2L;
import de.pottgames.anemulator.cpu.extendedinstructions.Res2_HL_;
import de.pottgames.anemulator.cpu.extendedinstructions.Res3A;
import de.pottgames.anemulator.cpu.extendedinstructions.Res3B;
import de.pottgames.anemulator.cpu.extendedinstructions.Res3C;
import de.pottgames.anemulator.cpu.extendedinstructions.Res3D;
import de.pottgames.anemulator.cpu.extendedinstructions.Res3E;
import de.pottgames.anemulator.cpu.extendedinstructions.Res3H;
import de.pottgames.anemulator.cpu.extendedinstructions.Res3L;
import de.pottgames.anemulator.cpu.extendedinstructions.Res3_HL_;
import de.pottgames.anemulator.cpu.extendedinstructions.Res4A;
import de.pottgames.anemulator.cpu.extendedinstructions.Res4B;
import de.pottgames.anemulator.cpu.extendedinstructions.Res4C;
import de.pottgames.anemulator.cpu.extendedinstructions.Res4D;
import de.pottgames.anemulator.cpu.extendedinstructions.Res4E;
import de.pottgames.anemulator.cpu.extendedinstructions.Res4H;
import de.pottgames.anemulator.cpu.extendedinstructions.Res4L;
import de.pottgames.anemulator.cpu.extendedinstructions.Res4_HL_;
import de.pottgames.anemulator.cpu.extendedinstructions.Res5A;
import de.pottgames.anemulator.cpu.extendedinstructions.Res5B;
import de.pottgames.anemulator.cpu.extendedinstructions.Res5C;
import de.pottgames.anemulator.cpu.extendedinstructions.Res5D;
import de.pottgames.anemulator.cpu.extendedinstructions.Res5E;
import de.pottgames.anemulator.cpu.extendedinstructions.Res5H;
import de.pottgames.anemulator.cpu.extendedinstructions.Res5L;
import de.pottgames.anemulator.cpu.extendedinstructions.Res5_HL_;
import de.pottgames.anemulator.cpu.extendedinstructions.Res6A;
import de.pottgames.anemulator.cpu.extendedinstructions.Res6B;
import de.pottgames.anemulator.cpu.extendedinstructions.Res6C;
import de.pottgames.anemulator.cpu.extendedinstructions.Res6D;
import de.pottgames.anemulator.cpu.extendedinstructions.Res6E;
import de.pottgames.anemulator.cpu.extendedinstructions.Res6H;
import de.pottgames.anemulator.cpu.extendedinstructions.Res6L;
import de.pottgames.anemulator.cpu.extendedinstructions.Res6_HL_;
import de.pottgames.anemulator.cpu.extendedinstructions.Res7A;
import de.pottgames.anemulator.cpu.extendedinstructions.Res7B;
import de.pottgames.anemulator.cpu.extendedinstructions.Res7C;
import de.pottgames.anemulator.cpu.extendedinstructions.Res7D;
import de.pottgames.anemulator.cpu.extendedinstructions.Res7E;
import de.pottgames.anemulator.cpu.extendedinstructions.Res7H;
import de.pottgames.anemulator.cpu.extendedinstructions.Res7L;
import de.pottgames.anemulator.cpu.extendedinstructions.Res7_HL_;
import de.pottgames.anemulator.cpu.extendedinstructions.RlA;
import de.pottgames.anemulator.cpu.extendedinstructions.RlB;
import de.pottgames.anemulator.cpu.extendedinstructions.RlC;
import de.pottgames.anemulator.cpu.extendedinstructions.RlD;
import de.pottgames.anemulator.cpu.extendedinstructions.RlE;
import de.pottgames.anemulator.cpu.extendedinstructions.RlH;
import de.pottgames.anemulator.cpu.extendedinstructions.RlL;
import de.pottgames.anemulator.cpu.extendedinstructions.Rl_HL_;
import de.pottgames.anemulator.error.UnsupportedFeatureException;
import de.pottgames.anemulator.memory.MemoryController;

public class PrefixCB extends Instruction {
    private IntMap<Instruction> extendedInstructions = new IntMap<>();


    public PrefixCB(Register register, MemoryController memory) {
        super(register, memory);
        this.initExtendedInstructions();
    }


    private void initExtendedInstructions() {
        this.extendedInstructions.put(0x00, null);
        this.extendedInstructions.put(0x01, null);
        this.extendedInstructions.put(0x02, null);
        this.extendedInstructions.put(0x03, null);
        this.extendedInstructions.put(0x04, null);
        this.extendedInstructions.put(0x05, null);
        this.extendedInstructions.put(0x06, null);
        this.extendedInstructions.put(0x07, null);
        this.extendedInstructions.put(0x08, null);
        this.extendedInstructions.put(0x09, null);
        this.extendedInstructions.put(0x0A, null);
        this.extendedInstructions.put(0x0B, null);
        this.extendedInstructions.put(0x0C, null);
        this.extendedInstructions.put(0x0D, null);
        this.extendedInstructions.put(0x0E, null);
        this.extendedInstructions.put(0x0F, null);

        this.extendedInstructions.put(0x10, new RlB(this.register, this.memory));
        this.extendedInstructions.put(0x11, new RlC(this.register, this.memory));
        this.extendedInstructions.put(0x12, new RlD(this.register, this.memory));
        this.extendedInstructions.put(0x13, new RlE(this.register, this.memory));
        this.extendedInstructions.put(0x14, new RlH(this.register, this.memory));
        this.extendedInstructions.put(0x15, new RlL(this.register, this.memory));
        this.extendedInstructions.put(0x16, new Rl_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x17, new RlA(this.register, this.memory));
        this.extendedInstructions.put(0x18, null);
        this.extendedInstructions.put(0x19, null);
        this.extendedInstructions.put(0x1A, null);
        this.extendedInstructions.put(0x1B, null);
        this.extendedInstructions.put(0x1C, null);
        this.extendedInstructions.put(0x1D, null);
        this.extendedInstructions.put(0x1E, null);
        this.extendedInstructions.put(0x1F, null);

        this.extendedInstructions.put(0x20, null);
        this.extendedInstructions.put(0x21, null);
        this.extendedInstructions.put(0x22, null);
        this.extendedInstructions.put(0x23, null);
        this.extendedInstructions.put(0x24, null);
        this.extendedInstructions.put(0x25, null);
        this.extendedInstructions.put(0x26, null);
        this.extendedInstructions.put(0x27, null);
        this.extendedInstructions.put(0x28, null);
        this.extendedInstructions.put(0x29, null);
        this.extendedInstructions.put(0x2A, null);
        this.extendedInstructions.put(0x2B, null);
        this.extendedInstructions.put(0x2C, null);
        this.extendedInstructions.put(0x2D, null);
        this.extendedInstructions.put(0x2E, null);
        this.extendedInstructions.put(0x2F, null);

        this.extendedInstructions.put(0x30, null);
        this.extendedInstructions.put(0x31, null);
        this.extendedInstructions.put(0x32, null);
        this.extendedInstructions.put(0x33, null);
        this.extendedInstructions.put(0x34, null);
        this.extendedInstructions.put(0x35, null);
        this.extendedInstructions.put(0x36, null);
        this.extendedInstructions.put(0x37, null);
        this.extendedInstructions.put(0x38, null);
        this.extendedInstructions.put(0x39, null);
        this.extendedInstructions.put(0x3A, null);
        this.extendedInstructions.put(0x3B, null);
        this.extendedInstructions.put(0x3C, null);
        this.extendedInstructions.put(0x3D, null);
        this.extendedInstructions.put(0x3E, null);
        this.extendedInstructions.put(0x3F, null);

        this.extendedInstructions.put(0x40, null);
        this.extendedInstructions.put(0x41, null);
        this.extendedInstructions.put(0x42, null);
        this.extendedInstructions.put(0x43, null);
        this.extendedInstructions.put(0x44, null);
        this.extendedInstructions.put(0x45, null);
        this.extendedInstructions.put(0x46, null);
        this.extendedInstructions.put(0x47, null);
        this.extendedInstructions.put(0x48, null);
        this.extendedInstructions.put(0x49, null);
        this.extendedInstructions.put(0x4A, null);
        this.extendedInstructions.put(0x4B, null);
        this.extendedInstructions.put(0x4C, null);
        this.extendedInstructions.put(0x4D, null);
        this.extendedInstructions.put(0x4E, null);
        this.extendedInstructions.put(0x4F, null);

        this.extendedInstructions.put(0x50, null);
        this.extendedInstructions.put(0x51, null);
        this.extendedInstructions.put(0x52, null);
        this.extendedInstructions.put(0x53, null);
        this.extendedInstructions.put(0x54, null);
        this.extendedInstructions.put(0x55, null);
        this.extendedInstructions.put(0x56, null);
        this.extendedInstructions.put(0x57, null);
        this.extendedInstructions.put(0x58, null);
        this.extendedInstructions.put(0x59, null);
        this.extendedInstructions.put(0x5A, null);
        this.extendedInstructions.put(0x5B, null);
        this.extendedInstructions.put(0x5C, null);
        this.extendedInstructions.put(0x5D, null);
        this.extendedInstructions.put(0x5E, null);
        this.extendedInstructions.put(0x5F, null);

        this.extendedInstructions.put(0x60, null);
        this.extendedInstructions.put(0x61, null);
        this.extendedInstructions.put(0x62, null);
        this.extendedInstructions.put(0x63, null);
        this.extendedInstructions.put(0x64, null);
        this.extendedInstructions.put(0x65, null);
        this.extendedInstructions.put(0x66, null);
        this.extendedInstructions.put(0x67, null);
        this.extendedInstructions.put(0x68, null);
        this.extendedInstructions.put(0x69, null);
        this.extendedInstructions.put(0x6A, null);
        this.extendedInstructions.put(0x6B, null);
        this.extendedInstructions.put(0x6C, null);
        this.extendedInstructions.put(0x6D, null);
        this.extendedInstructions.put(0x6E, null);
        this.extendedInstructions.put(0x6F, null);

        this.extendedInstructions.put(0x70, null);
        this.extendedInstructions.put(0x71, null);
        this.extendedInstructions.put(0x72, null);
        this.extendedInstructions.put(0x73, null);
        this.extendedInstructions.put(0x74, null);
        this.extendedInstructions.put(0x75, null);
        this.extendedInstructions.put(0x76, null);
        this.extendedInstructions.put(0x77, null);
        this.extendedInstructions.put(0x78, null);
        this.extendedInstructions.put(0x79, null);
        this.extendedInstructions.put(0x7A, null);
        this.extendedInstructions.put(0x7B, null);
        this.extendedInstructions.put(0x7C, new Bit7H(this.register, this.memory));
        this.extendedInstructions.put(0x7D, null);
        this.extendedInstructions.put(0x7E, null);
        this.extendedInstructions.put(0x7F, null);

        this.extendedInstructions.put(0x80, new Res0B(this.register, this.memory));
        this.extendedInstructions.put(0x81, new Res0C(this.register, this.memory));
        this.extendedInstructions.put(0x82, new Res0D(this.register, this.memory));
        this.extendedInstructions.put(0x83, new Res0E(this.register, this.memory));
        this.extendedInstructions.put(0x84, new Res0H(this.register, this.memory));
        this.extendedInstructions.put(0x85, new Res0L(this.register, this.memory));
        this.extendedInstructions.put(0x86, new Res0_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x87, new Res0A(this.register, this.memory));
        this.extendedInstructions.put(0x88, new Res1B(this.register, this.memory));
        this.extendedInstructions.put(0x89, new Res1C(this.register, this.memory));
        this.extendedInstructions.put(0x8A, new Res1D(this.register, this.memory));
        this.extendedInstructions.put(0x8B, new Res1E(this.register, this.memory));
        this.extendedInstructions.put(0x8C, new Res1H(this.register, this.memory));
        this.extendedInstructions.put(0x8D, new Res1L(this.register, this.memory));
        this.extendedInstructions.put(0x8E, new Res1_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x8F, new Res1A(this.register, this.memory));

        this.extendedInstructions.put(0x90, new Res2B(this.register, this.memory));
        this.extendedInstructions.put(0x91, new Res2C(this.register, this.memory));
        this.extendedInstructions.put(0x92, new Res2D(this.register, this.memory));
        this.extendedInstructions.put(0x93, new Res2E(this.register, this.memory));
        this.extendedInstructions.put(0x94, new Res2H(this.register, this.memory));
        this.extendedInstructions.put(0x95, new Res2L(this.register, this.memory));
        this.extendedInstructions.put(0x96, new Res2_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x97, new Res2A(this.register, this.memory));
        this.extendedInstructions.put(0x98, new Res3B(this.register, this.memory));
        this.extendedInstructions.put(0x99, new Res3C(this.register, this.memory));
        this.extendedInstructions.put(0x9A, new Res3D(this.register, this.memory));
        this.extendedInstructions.put(0x9B, new Res3E(this.register, this.memory));
        this.extendedInstructions.put(0x9C, new Res3H(this.register, this.memory));
        this.extendedInstructions.put(0x9D, new Res3L(this.register, this.memory));
        this.extendedInstructions.put(0x9E, new Res3_HL_(this.register, this.memory));
        this.extendedInstructions.put(0x9F, new Res3A(this.register, this.memory));

        this.extendedInstructions.put(0xA0, new Res4B(this.register, this.memory));
        this.extendedInstructions.put(0xA1, new Res4C(this.register, this.memory));
        this.extendedInstructions.put(0xA2, new Res4D(this.register, this.memory));
        this.extendedInstructions.put(0xA3, new Res4E(this.register, this.memory));
        this.extendedInstructions.put(0xA4, new Res4H(this.register, this.memory));
        this.extendedInstructions.put(0xA5, new Res4L(this.register, this.memory));
        this.extendedInstructions.put(0xA6, new Res4_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xA7, new Res4A(this.register, this.memory));
        this.extendedInstructions.put(0xA8, new Res5B(this.register, this.memory));
        this.extendedInstructions.put(0xA9, new Res5C(this.register, this.memory));
        this.extendedInstructions.put(0xAA, new Res5D(this.register, this.memory));
        this.extendedInstructions.put(0xAB, new Res5E(this.register, this.memory));
        this.extendedInstructions.put(0xAC, new Res5H(this.register, this.memory));
        this.extendedInstructions.put(0xAD, new Res5L(this.register, this.memory));
        this.extendedInstructions.put(0xAE, new Res5_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xAF, new Res5A(this.register, this.memory));

        this.extendedInstructions.put(0xB0, new Res6B(this.register, this.memory));
        this.extendedInstructions.put(0xB1, new Res6C(this.register, this.memory));
        this.extendedInstructions.put(0xB2, new Res6D(this.register, this.memory));
        this.extendedInstructions.put(0xB3, new Res6E(this.register, this.memory));
        this.extendedInstructions.put(0xB4, new Res6H(this.register, this.memory));
        this.extendedInstructions.put(0xB5, new Res6L(this.register, this.memory));
        this.extendedInstructions.put(0xB6, new Res6_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xB7, new Res6A(this.register, this.memory));
        this.extendedInstructions.put(0xB8, new Res7B(this.register, this.memory));
        this.extendedInstructions.put(0xB9, new Res7C(this.register, this.memory));
        this.extendedInstructions.put(0xBA, new Res7D(this.register, this.memory));
        this.extendedInstructions.put(0xBB, new Res7E(this.register, this.memory));
        this.extendedInstructions.put(0xBC, new Res7H(this.register, this.memory));
        this.extendedInstructions.put(0xBD, new Res7L(this.register, this.memory));
        this.extendedInstructions.put(0xBE, new Res7_HL_(this.register, this.memory));
        this.extendedInstructions.put(0xBF, new Res7A(this.register, this.memory));

        this.extendedInstructions.put(0xC0, null);
        this.extendedInstructions.put(0xC1, null);
        this.extendedInstructions.put(0xC2, null);
        this.extendedInstructions.put(0xC3, null);
        this.extendedInstructions.put(0xC4, null);
        this.extendedInstructions.put(0xC5, null);
        this.extendedInstructions.put(0xC6, null);
        this.extendedInstructions.put(0xC7, null);
        this.extendedInstructions.put(0xC8, null);
        this.extendedInstructions.put(0xC9, null);
        this.extendedInstructions.put(0xCA, null);
        this.extendedInstructions.put(0xCB, null);
        this.extendedInstructions.put(0xCC, null);
        this.extendedInstructions.put(0xCD, null);
        this.extendedInstructions.put(0xCE, null);
        this.extendedInstructions.put(0xCF, null);

        this.extendedInstructions.put(0xD0, null);
        this.extendedInstructions.put(0xD1, null);
        this.extendedInstructions.put(0xD2, null);
        this.extendedInstructions.put(0xD3, null);
        this.extendedInstructions.put(0xD4, null);
        this.extendedInstructions.put(0xD5, null);
        this.extendedInstructions.put(0xD6, null);
        this.extendedInstructions.put(0xD7, null);
        this.extendedInstructions.put(0xD8, null);
        this.extendedInstructions.put(0xD9, null);
        this.extendedInstructions.put(0xDA, null);
        this.extendedInstructions.put(0xDB, null);
        this.extendedInstructions.put(0xDC, null);
        this.extendedInstructions.put(0xDD, null);
        this.extendedInstructions.put(0xDE, null);
        this.extendedInstructions.put(0xDF, null);

        this.extendedInstructions.put(0xE0, null);
        this.extendedInstructions.put(0xE1, null);
        this.extendedInstructions.put(0xE2, null);
        this.extendedInstructions.put(0xE3, null);
        this.extendedInstructions.put(0xE4, null);
        this.extendedInstructions.put(0xE5, null);
        this.extendedInstructions.put(0xE6, null);
        this.extendedInstructions.put(0xE7, null);
        this.extendedInstructions.put(0xE8, null);
        this.extendedInstructions.put(0xE9, null);
        this.extendedInstructions.put(0xEA, null);
        this.extendedInstructions.put(0xEB, null);
        this.extendedInstructions.put(0xEC, null);
        this.extendedInstructions.put(0xED, null);
        this.extendedInstructions.put(0xEE, null);
        this.extendedInstructions.put(0xEF, null);

        this.extendedInstructions.put(0xF0, null);
        this.extendedInstructions.put(0xF1, null);
        this.extendedInstructions.put(0xF2, null);
        this.extendedInstructions.put(0xF3, null);
        this.extendedInstructions.put(0xF4, null);
        this.extendedInstructions.put(0xF5, null);
        this.extendedInstructions.put(0xF6, null);
        this.extendedInstructions.put(0xF7, null);
        this.extendedInstructions.put(0xF8, null);
        this.extendedInstructions.put(0xF9, null);
        this.extendedInstructions.put(0xFA, null);
        this.extendedInstructions.put(0xFB, null);
        this.extendedInstructions.put(0xFC, null);
        this.extendedInstructions.put(0xFD, null);
        this.extendedInstructions.put(0xFE, null);
        this.extendedInstructions.put(0xFF, null);
    }


    @Override
    public int run() {
        final int opCode = this.memory.read8Bit(this.register.pc);
        this.register.pc++;
        final Instruction instruction = this.extendedInstructions.get(opCode);

        if (instruction == null) {
            throw new UnsupportedFeatureException("Unsupported extended opCode: " + Integer.toHexString(opCode));
        }

        final int cycles = instruction.run();

        return cycles;
    }

}
