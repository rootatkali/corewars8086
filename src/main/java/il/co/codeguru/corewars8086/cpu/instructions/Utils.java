package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.NgCpuState;

/**
 * Utility methods for all instructions.
 */
public class Utils {

    static void updateFlags8(NgCpuState state, short value) {
        state.setCarryFlag((value & 0xFF00) != 0);
        updateFlagsNoCarryOverflow8(state, (byte) value);
    }

    static void updateFlags16(NgCpuState state, int value) {
        state.setCarryFlag((value & 0xFFFF0000) != 0);
        updateFlagsNoCarryOverflow16(state, (short) value);
    }

    static void updateFlagsNoCarryOverflow8(NgCpuState state, byte value) {
        state.setParityFlag(BitwiseUtils.getParity(value));
        state.setSignFlag((value & 0x80) != 0);
        state.setZeroFlag(value == 0);
    }

    static void updateFlagsNoCarryOverflow16(NgCpuState state, short value) {
        byte byteValue = (byte) value; // In 8086, parity flag is calculated only for lower byte
        state.setParityFlag(BitwiseUtils.getParity(byteValue));
        state.setSignFlag((value & 0x8000) != 0);
        state.setZeroFlag(value == 0);
    }

}
