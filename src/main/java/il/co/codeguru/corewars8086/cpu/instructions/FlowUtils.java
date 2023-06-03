package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.NgCpuState;
import il.co.codeguru.corewars8086.cpu.NgOpcodeFetcher;
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
    
    static void conditionalJump(NgCpuState state,
                                NgOpcodeFetcher opcodeFetcher,
                                boolean branch) throws MemoryException {
        byte offset = opcodeFetcher.nextByte();

        if (branch) {
            state.setIp((short) (state.getIp() + offset));
        }
    }
    
    static void conditionalLoop(NgCpuState state,
                                NgOpcodeFetcher opcodeFetcher,
                                boolean loop) throws MemoryException {
        byte offset = opcodeFetcher.nextByte();
        short newCx = (short) (state.getCx() - 1);
        state.setCx(newCx);
        
        if (newCx != 0 && loop) {
            state.setIp((short) (state.getIp() + offset));
        }
    }
    
    static void loop(NgCpuState state, NgOpcodeFetcher opcodeFetcher) throws MemoryException {
        conditionalLoop(state, opcodeFetcher, true);
    }
}
