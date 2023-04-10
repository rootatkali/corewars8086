package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.NgCpuState;
import il.co.codeguru.corewars8086.cpu.NgIndirectAddressingDecoder;
import il.co.codeguru.corewars8086.memory.MemoryException;
import il.co.codeguru.corewars8086.memory.RealModeAddress;
import il.co.codeguru.corewars8086.memory.RealModeMemory;

import static il.co.codeguru.corewars8086.cpu.instructions.Utils.updateFlagsNoCarryOverflow16;
import static il.co.codeguru.corewars8086.cpu.instructions.Utils.updateFlagsNoCarryOverflow8;
import static il.co.codeguru.corewars8086.utils.Unsigned.unsignedByte;
import static il.co.codeguru.corewars8086.utils.Unsigned.unsignedShort;

public class BitwiseUtils {
    static byte or8(NgCpuState state, byte a, byte b) {
        short result16 = (short) (unsignedByte(a) | unsignedByte(b));
        byte result8 = (byte) result16;
        Utils.updateFlags8(state, result16);

        return result8;
    }

    static short or16(NgCpuState state, short a, short b) {
        int result32 = unsignedShort(a) | unsignedShort(b);
        short result16 = (short) result32;
        Utils.updateFlags16(state, result32);

        return result16;
    }

    static byte and8(NgCpuState state, byte a, byte b) {
        short result16 = (short) (unsignedByte(a) & unsignedByte(b));
        byte result8 = (byte) result16;
        Utils.updateFlags8(state, result16);

        return result8;
    }

    static short and16(NgCpuState state, short a, short b) {
        int result32 = unsignedShort(a) & unsignedShort(b);
        short result16 = (short) result32;
        Utils.updateFlags16(state, result32);

        return result16;
    }

    static byte xor8(NgCpuState state, byte a, byte b) {
        short result16 = (short) (unsignedByte(a) ^ unsignedByte(b));
        byte result8 = (byte) result16;
        Utils.updateFlags8(state, result16);

        return result8;
    }

    static short xor16(NgCpuState state, short a, short b) {
        int result32 = unsignedShort(a) ^ unsignedShort(b);
        short result16 = (short) result32;
        Utils.updateFlags16(state, result32);

        return result16;
    }

    static boolean getParity(byte value) {
        return Integer.bitCount(unsignedByte(value)) % 2 == 0;
    }

    static void cmpsb(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address1 = new RealModeAddress(state.getDs(), state.getSi());
        RealModeAddress address2 = new RealModeAddress(state.getEs(), state.getDi());
        ArithmeticUtils.sub8(state, memory.readByte(address1), memory.readByte(address2));

        byte diff = state.getDirectionFlag() ? (byte) -1 : (byte) 1;
        state.setSi((short) (state.getSi() + diff));
        state.setDi((short) (state.getDi() + diff));
    }

    static void cmpsw(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address1 = new RealModeAddress(state.getDs(), state.getSi());
        RealModeAddress address2 = new RealModeAddress(state.getEs(), state.getDi());
        ArithmeticUtils.sub16(state, memory.readWord(address1), memory.readWord(address2));

        byte diff = state.getDirectionFlag() ? (byte) -2 : (byte) 2;
        state.setSi((short) (state.getSi() + diff));
        state.setDi((short) (state.getDi() + diff));
    }

    static void scasb(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address = new RealModeAddress(state.getEs(), state.getDi());
        ArithmeticUtils.sub8(state, state.getAl(), memory.readByte(address));

        byte diff = state.getDirectionFlag() ? (byte) -1 : (byte) 1;
        state.setDi((short) (state.getDi() + diff));
    }

    static void scasw(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address = new RealModeAddress(state.getEs(), state.getDi());
        ArithmeticUtils.sub16(state, state.getAx(), memory.readWord(address));

        byte diff = state.getDirectionFlag() ? (byte) -2 : (byte) 2;
        state.setDi((short) (state.getDi() + diff));
    }

    static void rol8(NgCpuState state,
                     NgIndirectAddressingDecoder addressingDecoder,
                     int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; i++) {
            byte value = addressingDecoder.getMemory8();
            byte mostSignificantBit = (byte) ((value >> 7) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 6) & 0x01);

            value = (byte) ((value << 1) | mostSignificantBit);
            addressingDecoder.setMemory8(value);

            state.setCarryFlag(mostSignificantBit != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
        }
    }

    static void rol16(NgCpuState state,
                      NgIndirectAddressingDecoder addressingDecoder,
                      int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; ++i) {
            short value = addressingDecoder.getMemory16();
            byte mostSignificantBit = (byte) ((value >> 15) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 14) & 0x01);

            value = (short) ((value << 1) | mostSignificantBit);
            addressingDecoder.setMemory16(value);

            state.setCarryFlag(mostSignificantBit != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
            updateFlagsNoCarryOverflow16(state, value);
        }
    }

    static void ror8(NgCpuState state,
                     NgIndirectAddressingDecoder addressingDecoder,
                     int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; i++) {
            byte value = addressingDecoder.getMemory8();
            byte leastSignificantBit = (byte) (value & 0x01);

            value = (byte) (((value & 0xFF) >>> 1) | (leastSignificantBit << 7));
            addressingDecoder.setMemory8(value);

            byte mostSignificantBit = (byte) ((value >> 7) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 6) & 0x01);

            state.setCarryFlag(leastSignificantBit != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
        }
    }

    static void ror16(NgCpuState state,
               NgIndirectAddressingDecoder addressingDecoder,
               int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; ++i) {
            short value = addressingDecoder.getMemory16();
            byte leastSignificantBit = (byte) (value & 0x01);

            value = (short) (((value & 0xFFFF) >>> 1) | (leastSignificantBit << 15));
            addressingDecoder.setMemory16(value);

            byte mostSignificantBit = (byte) ((value >> 15) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 14) & 0x01);

            state.setCarryFlag(leastSignificantBit != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
            updateFlagsNoCarryOverflow16(state, value);
        }
    }

    static void rcl8(NgCpuState state,
                     NgIndirectAddressingDecoder addressingDecoder,
                     int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; i++) {
            byte value = addressingDecoder.getMemory8();
            byte mostSignificantBit = (byte) ((value >> 7) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 6) & 0x01);
            byte carryFlag = (byte) (state.getCarryFlag() ? 1 : 0);

            value = (byte) ((value << 1) | carryFlag);
            addressingDecoder.setMemory8(value);

            state.setCarryFlag(mostSignificantBit != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
        }
    }

    static void rcl16(NgCpuState state,
                      NgIndirectAddressingDecoder addressingDecoder,
                      int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; ++i) {
            short value = addressingDecoder.getMemory16();
            byte mostSignificantBit = (byte) ((value >> 15) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 14) & 0x01);
            byte carryFlag = (byte) (state.getCarryFlag() ? 1 : 0);

            value = (short) ((value << 1) | carryFlag);
            addressingDecoder.setMemory16(value);

            state.setCarryFlag(mostSignificantBit != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
            updateFlagsNoCarryOverflow16(state, value);
        }
    }

    static void rcr8(NgCpuState state,
                     NgIndirectAddressingDecoder addressingDecoder,
                     int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; i++) {
            byte value = addressingDecoder.getMemory8();
            byte leastSignificantBit = (byte) (value & 0x01);
            byte carryFlag = (byte) (state.getCarryFlag() ? 1 : 0);

            value = (byte) (((value & 0xFF) >>> 1) | (carryFlag << 7));
            addressingDecoder.setMemory8(value);

            byte mostSignificantBit = (byte) ((value >> 7) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 6) & 0x01);

            state.setCarryFlag(leastSignificantBit != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
        }
    }

    static void rcr16(NgCpuState state,
                      NgIndirectAddressingDecoder addressingDecoder,
                      int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; ++i) {
            short value = addressingDecoder.getMemory16();
            byte leastSignificantBit = (byte) (value & 0x01);
            byte carryFlag = (byte) (state.getCarryFlag() ? 1 : 0);

            value = (short) (((value & 0xFFFF) >>> 1) | (carryFlag << 15));
            addressingDecoder.setMemory16(value);

            byte mostSignificantBit = (byte) ((value >> 15) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 14) & 0x01);

            state.setCarryFlag(leastSignificantBit != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
        }
    }

    static void shl8(NgCpuState state,
                     NgIndirectAddressingDecoder addressingDecoder,
                     int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; i++) {
            byte value = addressingDecoder.getMemory8();
            byte mostSignificantBit = (byte) ((value >> 7) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 6) & 0x01);

            value <<= 1;
            addressingDecoder.setMemory8(value);

            state.setCarryFlag(mostSignificantBit != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
            updateFlagsNoCarryOverflow8(state, value);
        }
    }

    static void shl16(NgCpuState state,
                      NgIndirectAddressingDecoder addressingDecoder,
                      int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; ++i) {
            short value = addressingDecoder.getMemory16();
            byte mostSignificantBit = (byte) ((value >> 15) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 14) & 0x01);

            value = (short) (value << 1);
            addressingDecoder.setMemory16(value);

            state.setCarryFlag(mostSignificantBit != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
            state.setZeroFlag(value == 0);
        }
    }

    static void shr8(NgCpuState state,
                     NgIndirectAddressingDecoder addressingDecoder,
                     int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; ++i) {
            byte value = addressingDecoder.getMemory8();
            byte lsb = (byte) (value & 0x01);

            value >>>= 1;
            addressingDecoder.setMemory8(value);

            byte mostSignificantBit = (byte) ((value >> 7) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 6) & 0x01);

            state.setCarryFlag(lsb != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
            updateFlagsNoCarryOverflow8(state, value);
        }
    }

    static void shr16(NgCpuState state,
               NgIndirectAddressingDecoder addressingDecoder,
               int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; ++i) {
            short value = addressingDecoder.getMemory16();
            byte leastSignificantBit = (byte) (value & 0x01);

            value = (short) ((value & 0xFFFF) >>> 1);
            addressingDecoder.setMemory16(value);

            byte mostSignificantBit = (byte) ((value >> 15) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 14) & 0x01);

            state.setCarryFlag(leastSignificantBit != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
            state.setZeroFlag(value == 0);
        }
    }

    static void sar8(NgCpuState state,
                     NgIndirectAddressingDecoder addressingDecoder,
                     int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; ++i) {
            byte value = addressingDecoder.getMemory8();
            byte leastSignificantBit = (byte) (value & 0x01);

            value = (byte) (value >> 1);
            addressingDecoder.setMemory8(value);

            byte mostSignificantBit = (byte) ((value >> 7) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 6) & 0x01);

            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
            state.setCarryFlag(leastSignificantBit != 0);
            updateFlagsNoCarryOverflow8(state, value);
        }
    }

    static void sar16(NgCpuState state,
                      NgIndirectAddressingDecoder addressingDecoder,
                      int times) throws MemoryException {
        times &= 0x1F;

        for (int i = 0; i < times; ++i) {
            short value = addressingDecoder.getMemory16();
            byte leastSignificantBit = (byte) (value & 0x01);

            value = (short) (value >> 1);
            addressingDecoder.setMemory16(value);

            byte mostSignificantBit = (byte) ((value >> 15) & 0x01);
            byte secondSignificantBit = (byte) ((value >> 14) & 0x01);

            state.setCarryFlag(leastSignificantBit != 0);
            state.setOverflowFlag(mostSignificantBit != secondSignificantBit);
            state.setZeroFlag(value == 0);
        }
    }
}
