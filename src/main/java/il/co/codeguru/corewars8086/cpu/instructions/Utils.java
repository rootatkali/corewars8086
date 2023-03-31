package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.NgCpuState;
import il.co.codeguru.corewars8086.memory.MemoryException;
import il.co.codeguru.corewars8086.memory.RealModeAddress;
import il.co.codeguru.corewars8086.memory.RealModeMemory;

import static il.co.codeguru.corewars8086.utils.Unsigned.*;

/**
 * Utility methods for all instructions.
 */
public class Utils {
    static void push(NgCpuState state, RealModeMemory memory, short value) throws MemoryException {
        state.setSp((short) (state.getSp() - 2));
        RealModeAddress stackPtr = new RealModeAddress(state.getSs(), state.getSp());
        memory.writeWord(stackPtr, value);
    }

    static short pop(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress stackPtr = new RealModeAddress(state.getSs(), state.getSp());
        short value = memory.readWord(stackPtr);
        state.setSp((short) (state.getSp() + 2));
        return value;
    }

    static byte add8(NgCpuState state, byte a, byte b) {
        short result16 = (short) (unsignedByte(a) + unsignedByte(b));
        byte result8 = (byte) result16;
        updateFlags8(state, result16);

        return result8;
    }

    static short add16(NgCpuState state, short a, short b) {
        int result32 = unsignedShort(a) + unsignedShort(b);
        short result16 = (short) result32;
        updateFlags16(state, result32);

        return result16;
    }

    static byte adc8(NgCpuState state, byte a, byte b) {
        short result16 = (short) (unsignedByte(a) + unsignedByte(b));
        if (state.getCarryFlag()) result16++;

        byte result8 = (byte) result16;
        updateFlags8(state, result16);

        return result8;
    }

    static short adc16(NgCpuState state, short a, short b) {
        int result32 = unsignedShort(a) + unsignedShort(b);
        if (state.getCarryFlag()) result32++;

        short result16 = (short) result32;
        updateFlags16(state, result32);

        return result16;
    }

    static byte sbb8(NgCpuState state, byte a, byte b) {
        short result16 = (short) (unsignedByte(a) - unsignedByte(b));
        if (state.getCarryFlag()) result16--;

        byte result8 = (byte) result16;
        updateFlags8(state, result16);

        return result8;
    }

    static short sbb16(NgCpuState state, short a, short b) {
        int result32 = unsignedShort(a) - unsignedShort(b);
        if (state.getCarryFlag()) result32--;

        short result16 = (short) result32;
        updateFlags16(state, result32);

        return result16;
    }

    static byte sub8(NgCpuState state, byte a, byte b) {
        short result16 = (short) (unsignedByte(a) - unsignedByte(b));
        byte result8 = (byte) result16;
        updateFlags8(state, result16);

        return result8;
    }

    static short sub16(NgCpuState state, short a, short b) {
        int result32 = unsignedShort(a) - unsignedShort(b);
        short result16 = (short) result32;
        updateFlags16(state, result32);

        return result16;
    }

    static byte or8(NgCpuState state, byte a, byte b) {
        short result16 = (short) (unsignedByte(a) | unsignedByte(b));
        byte result8 = (byte) result16;
        updateFlags8(state, result16);

        return result8;
    }

    static short or16(NgCpuState state, short a, short b) {
        int result32 = unsignedShort(a) | unsignedShort(b);
        short result16 = (short) result32;
        updateFlags16(state, result32);

        return result16;
    }

    static byte and8(NgCpuState state, byte a, byte b) {
        short result16 = (short) (unsignedByte(a) & unsignedByte(b));
        byte result8 = (byte) result16;
        updateFlags8(state, result16);

        return result8;
    }

    static short and16(NgCpuState state, short a, short b) {
        int result32 = unsignedShort(a) & unsignedShort(b);
        short result16 = (short) result32;
        updateFlags16(state, result32);

        return result16;
    }

    static void updateFlags8(NgCpuState state, short value) {
        state.setCarryFlag((value & 0xFF00) != 0);
        updateFlagsNoCarryOverflow8(state, (byte) value);
    }

    static void updateFlags16(NgCpuState state, int value) {
        state.setCarryFlag((value & 0xFFFF0000) != 0);
        updateFlagsNoCarryOverflow16(state, (short) value);
    }

    static void updateFlagsNoCarryOverflow8(NgCpuState state, byte value) {
        state.setParityFlag(getParity(value));
        state.setSignFlag((value & 0x80) != 0);
        state.setZeroFlag(value == 0);
    }

    static void updateFlagsNoCarryOverflow16(NgCpuState state, short value) {
        byte byteValue = (byte) value; // In 8086, parity flag is calculated only for lower byte
        state.setParityFlag(getParity(byteValue));
        state.setSignFlag((value & 0x8000) != 0);
        state.setZeroFlag(value == 0);
    }

    static boolean getParity(byte value) {
        return Integer.bitCount(unsignedByte(value)) % 2 == 0;
    }
}
