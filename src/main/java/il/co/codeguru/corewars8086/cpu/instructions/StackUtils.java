package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.NgCpuState;
import il.co.codeguru.corewars8086.memory.MemoryException;
import il.co.codeguru.corewars8086.memory.RealModeAddress;
import il.co.codeguru.corewars8086.memory.RealModeMemory;

public class StackUtils {
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
}
