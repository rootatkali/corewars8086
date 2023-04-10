package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.NgCpuState;
import il.co.codeguru.corewars8086.memory.MemoryException;
import il.co.codeguru.corewars8086.memory.RealModeMemory;

public class FlowUtils {
    static void callFar(NgCpuState state, RealModeMemory memory, short segment, short offset) throws MemoryException {
        StackUtils.push(state, memory, state.getCs());
        state.setCs(segment);
        callNear(state, memory, offset);
    }

    static void callNear(NgCpuState state, RealModeMemory memory, short offset) throws MemoryException {
        StackUtils.push(state, memory, state.getIp());
        state.setIp(offset);
    }
}
