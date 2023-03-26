package il.co.codeguru.corewars8086.cpu;

import il.co.codeguru.corewars8086.memory.MemoryException;
import il.co.codeguru.corewars8086.memory.RealModeMemory;

public interface Instruction {
    void execute(NgCpuState state,
                 RealModeMemory memory,
                 NgOpcodeFetcher opcodeFetcher,
                 NgRegisterIndexingDecoder registers,
                 NgIndirectAddressingDecoder addressingDecoder) throws CpuException, MemoryException;
}
