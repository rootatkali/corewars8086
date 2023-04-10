package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.NgCpuState;
import il.co.codeguru.corewars8086.memory.MemoryException;
import il.co.codeguru.corewars8086.memory.RealModeAddress;
import il.co.codeguru.corewars8086.memory.RealModeMemory;

public class SetUtils {
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

}
