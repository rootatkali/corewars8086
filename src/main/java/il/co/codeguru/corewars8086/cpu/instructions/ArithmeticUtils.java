package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.NgCpuState;

import static il.co.codeguru.corewars8086.cpu.instructions.Utils.updateFlags16;
import static il.co.codeguru.corewars8086.cpu.instructions.Utils.updateFlags8;
import static il.co.codeguru.corewars8086.utils.Unsigned.unsignedByte;
import static il.co.codeguru.corewars8086.utils.Unsigned.unsignedShort;

public class ArithmeticUtils {
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
}
