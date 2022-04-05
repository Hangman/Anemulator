package de.pottgames.anemulator.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;

class FlagRegisterTest {

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
    void test(int number) {
        final Register register = new Register();

        // Z N H C
        switch (number) {
            case 0b0000:
                register.setFlag(FlagId.Z, false);
                register.setFlag(FlagId.N, false);
                register.setFlag(FlagId.H, false);
                register.setFlag(FlagId.C, false);
                Assertions.assertEquals(register.get(RegisterId.F), 0b0000 << 4);
                break;
            case 0b0001:
                register.setFlag(FlagId.Z, false);
                register.setFlag(FlagId.N, false);
                register.setFlag(FlagId.H, false);
                register.setFlag(FlagId.C, true);
                Assertions.assertEquals(register.get(RegisterId.F), 0b0001 << 4);
                break;
            case 0b0010:
                register.setFlag(FlagId.Z, false);
                register.setFlag(FlagId.N, false);
                register.setFlag(FlagId.H, true);
                register.setFlag(FlagId.C, false);
                Assertions.assertEquals(register.get(RegisterId.F), 0b0010 << 4);
                break;
            case 0b0011:
                register.setFlag(FlagId.Z, false);
                register.setFlag(FlagId.N, false);
                register.setFlag(FlagId.H, true);
                register.setFlag(FlagId.C, true);
                Assertions.assertEquals(register.get(RegisterId.F), 0b0011 << 4);
                break;
            case 0b0100:
                register.setFlag(FlagId.Z, false);
                register.setFlag(FlagId.N, true);
                register.setFlag(FlagId.H, false);
                register.setFlag(FlagId.C, false);
                Assertions.assertEquals(register.get(RegisterId.F), 0b0100 << 4);
                break;
            case 0b0101:
                register.setFlag(FlagId.Z, false);
                register.setFlag(FlagId.N, true);
                register.setFlag(FlagId.H, false);
                register.setFlag(FlagId.C, true);
                Assertions.assertEquals(register.get(RegisterId.F), 0b0101 << 4);
                break;
            case 0b0110:
                register.setFlag(FlagId.Z, false);
                register.setFlag(FlagId.N, true);
                register.setFlag(FlagId.H, true);
                register.setFlag(FlagId.C, false);
                Assertions.assertEquals(register.get(RegisterId.F), 0b0110 << 4);
                break;
            case 0b0111:
                register.setFlag(FlagId.Z, false);
                register.setFlag(FlagId.N, true);
                register.setFlag(FlagId.H, true);
                register.setFlag(FlagId.C, true);
                Assertions.assertEquals(register.get(RegisterId.F), 0b0111 << 4);
                break;
            case 0b1000:
                register.setFlag(FlagId.Z, true);
                register.setFlag(FlagId.N, false);
                register.setFlag(FlagId.H, false);
                register.setFlag(FlagId.C, false);
                Assertions.assertEquals(register.get(RegisterId.F), 0b1000 << 4);
                break;
            case 0b1001:
                register.setFlag(FlagId.Z, true);
                register.setFlag(FlagId.N, false);
                register.setFlag(FlagId.H, false);
                register.setFlag(FlagId.C, true);
                Assertions.assertEquals(register.get(RegisterId.F), 0b1001 << 4);
                break;
            case 0b1010:
                register.setFlag(FlagId.Z, true);
                register.setFlag(FlagId.N, false);
                register.setFlag(FlagId.H, true);
                register.setFlag(FlagId.C, false);
                Assertions.assertEquals(register.get(RegisterId.F), 0b1010 << 4);
                break;
            case 0b1011:
                register.setFlag(FlagId.Z, true);
                register.setFlag(FlagId.N, false);
                register.setFlag(FlagId.H, true);
                register.setFlag(FlagId.C, true);
                Assertions.assertEquals(register.get(RegisterId.F), 0b1011 << 4);
                break;
            case 0b1100:
                register.setFlag(FlagId.Z, true);
                register.setFlag(FlagId.N, true);
                register.setFlag(FlagId.H, false);
                register.setFlag(FlagId.C, false);
                Assertions.assertEquals(register.get(RegisterId.F), 0b1100 << 4);
                break;
            case 0b1101:
                register.setFlag(FlagId.Z, true);
                register.setFlag(FlagId.N, true);
                register.setFlag(FlagId.H, false);
                register.setFlag(FlagId.C, true);
                Assertions.assertEquals(register.get(RegisterId.F), 0b1101 << 4);
                break;
            case 0b1110:
                register.setFlag(FlagId.Z, true);
                register.setFlag(FlagId.N, true);
                register.setFlag(FlagId.H, true);
                register.setFlag(FlagId.C, false);
                Assertions.assertEquals(register.get(RegisterId.F), 0b1110 << 4);
                break;
            case 0b1111:
                register.setFlag(FlagId.Z, true);
                register.setFlag(FlagId.N, true);
                register.setFlag(FlagId.H, true);
                register.setFlag(FlagId.C, true);
                Assertions.assertEquals(register.get(RegisterId.F), 0b1111 << 4);
                break;
            default:
                Assertions.fail("Test input invalid, must be an int in the range 0 - 15");
        }
    }

}
