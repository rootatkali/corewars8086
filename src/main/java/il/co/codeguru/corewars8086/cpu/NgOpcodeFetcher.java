package il.co.codeguru.corewars8086.cpu;

import il.co.codeguru.corewars8086.memory.MemoryException;
import il.co.codeguru.corewars8086.memory.RealModeAddress;
import il.co.codeguru.corewars8086.memory.RealModeMemory;

/**
 * Wraps opcode fetching from CS:IP.
 * 
 * @author DL
 */
public class NgOpcodeFetcher {
    private static final int BYTES_PER_WORD = 2;

    private final NgCpuState state;
    private final RealModeMemory memory;

    /**
     * Constructor.
     * @param state   Used to read & update CS:IP.
     * @param memory  Used to actually read the fetched bytes.
     */
    public NgOpcodeFetcher(NgCpuState state, RealModeMemory memory) {
        this.state = state;
        this.memory = memory;
    }

    /**
     * @return the next byte pointed by CS:IP (and advances IP).
     * @throws MemoryException  on any error.
     */
    public byte nextByte() throws MemoryException {
        RealModeAddress address = new RealModeAddress(state.getCs(), state.getIp());
        state.setIp((short)(state.getIp() + 1));
        return memory.readExecuteByte(address);
    }

    /**
     * @return the next word pointed by CS:IP (and advances IP).
     * @throws MemoryException  on any error.
     */
    public short nextWord() throws MemoryException {
        RealModeAddress address = getCurrentAddress();
        state.setIp((short)(state.getIp() + BYTES_PER_WORD));
        return memory.readExecuteWord(address);
    }

    private RealModeAddress getCurrentAddress() {
        return new RealModeAddress(state.getCs(), state.getIp())
    }
}