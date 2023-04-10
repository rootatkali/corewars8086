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

    static byte inc8(NgCpuState state, byte value) {
        boolean carryFlag = state.getCarryFlag();
        byte result = add8(state, value, (byte) 0x01);
        state.setCarryFlag(carryFlag);

        return result;
    }

    static short inc16(NgCpuState state, short value) {
        boolean carryFlag = state.getCarryFlag();
        short result = add16(state, value, (short) 0x01);
        state.setCarryFlag(carryFlag);

        return result;
    }

    static byte dec8(NgCpuState state, byte value) {
        boolean carryFlag = state.getCarryFlag();
        byte result = sub8(state, value, (byte) 0x01);
        state.setCarryFlag(carryFlag);

        return result;
    }

    static short dec16(NgCpuState state, short value) {
        boolean carryFlag = state.getCarryFlag();
        short result = sub16(state, value, (short) 0x01);
        state.setCarryFlag(carryFlag);

        return result;
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

    static byte xor8(NgCpuState state, byte a, byte b) {
        short result16 = (short) (unsignedByte(a) ^ unsignedByte(b));
        byte result8 = (byte) result16;
        updateFlags8(state, result16);

        return result8;
    }

    static short xor16(NgCpuState state, short a, short b) {
        int result32 = unsignedShort(a) ^ unsignedShort(b);
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

    static void callFar(NgCpuState state, RealModeMemory memory, short segment, short offset) throws MemoryException {
        push(state, memory, state.getCs());
        state.setCs(segment);
        callNear(state, memory, offset);
    }

    static void callNear(NgCpuState state, RealModeMemory memory, short offset) throws MemoryException {
        push(state, memory, state.getIp());
        state.setIp(offset);
    }

    static void movsb(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress src = new RealModeAddress(state.getDs(), state.getSi());
        RealModeAddress dst = new RealModeAddress(state.getEs(), state.getDi());
        memory.writeByte(dst, memory.readByte(src));

        byte diff = state.getDirectionFlag() ? (byte) -1 : (byte) 1;
        state.setSi((short) (state.getSi() + diff));
        state.setDi((short) (state.getDi() + diff));
    }

    static void movsw(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress src = new RealModeAddress(state.getDs(), state.getSi());
        RealModeAddress dst = new RealModeAddress(state.getEs(), state.getDi());
        memory.writeWord(dst, memory.readWord(src));

        byte diff = state.getDirectionFlag() ? (byte) -2 : (byte) 2;
        state.setSi((short) (state.getSi() + diff));
        state.setDi((short) (state.getDi() + diff));
    }

    static void cmpsb(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address1 = new RealModeAddress(state.getDs(), state.getSi());
        RealModeAddress address2 = new RealModeAddress(state.getEs(), state.getDi());
        sub8(state, memory.readByte(address1), memory.readByte(address2));

        byte diff = state.getDirectionFlag() ? (byte) -1 : (byte) 1;
        state.setSi((short) (state.getSi() + diff));
        state.setDi((short) (state.getDi() + diff));
    }

    static void cmpsw(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address1 = new RealModeAddress(state.getDs(), state.getSi());
        RealModeAddress address2 = new RealModeAddress(state.getEs(), state.getDi());
        sub16(state, memory.readWord(address1), memory.readWord(address2));

        byte diff = state.getDirectionFlag() ? (byte) -2 : (byte) 2;
        state.setSi((short) (state.getSi() + diff));
        state.setDi((short) (state.getDi() + diff));
    }

    static void stosb(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address = new RealModeAddress(state.getEs(), state.getDi());
        memory.writeByte(address, state.getAl());

        byte diff = state.getDirectionFlag() ? (byte) -1 : (byte) 1;
        state.setDi((short) (state.getDi() + diff));
    }

    static void stosw(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address = new RealModeAddress(state.getEs(), state.getDi());
        memory.writeWord(address, state.getAx());

        byte diff = state.getDirectionFlag() ? (byte) -2 : (byte) 2;
        state.setDi((short) (state.getDi() + diff));
    }

    static void stosdw(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address1 = new RealModeAddress(state.getEs(), state.getDi());
        RealModeAddress address2 = new RealModeAddress(state.getEs(), (short) (state.getDi() + 2));
        memory.writeWord(address1, state.getAx());
        memory.writeWord(address2, state.getDx());

        byte diff = state.getDirectionFlag() ? (byte) -4 : (byte) 4;
        state.setDi((short) (state.getDi() + diff));
    }

    static void lodsb(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address = new RealModeAddress(state.getDs(), state.getSi());
        state.setAl(memory.readByte(address));

        byte diff = state.getDirectionFlag() ? (byte) -1 : (byte) 1;
        state.setSi((short) (state.getSi() + diff));
    }

    static void lodsw(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address = new RealModeAddress(state.getDs(), state.getSi());
        state.setAx(memory.readWord(address));

        byte diff = state.getDirectionFlag() ? (byte) -1 : (byte) 1;
        state.setSi((short) (state.getSi() + diff));
    }

    static void scasb(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address = new RealModeAddress(state.getEs(), state.getDi());
        sub8(state, state.getAl(), memory.readByte(address));

        byte diff = state.getDirectionFlag() ? (byte) -1 : (byte) 1;
        state.setDi((short) (state.getDi() + diff));
    }

    static void scasw(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address = new RealModeAddress(state.getEs(), state.getDi());
        sub16(state, state.getAx(), memory.readWord(address));

        byte diff = state.getDirectionFlag() ? (byte) -2 : (byte) 2;
        state.setDi((short) (state.getDi() + diff));
    }
}
