package il.co.codeguru.corewars8086.cpu;

import il.co.codeguru.corewars8086.memory.MemoryException;
import il.co.codeguru.corewars8086.memory.RealModeMemory;

/**
 * A fresh implementation of the 8086 CPU.
 *
 * @Beta This class is experimental and should not be considered stable. For now, keep using {@link Cpu}
 */
public class NgCpu {
    private final NgCpuState state;
    private final RealModeMemory memory;

    private final NgOpcodeFetcher opcodeFetcher;
    private final InstructionResolver instructionResolver;

    private final NgRegisterIndexingDecoder registers;
    private final NgIndirectAddressingDecoder addressingDecoder;


    public NgCpu(NgCpuState state, RealModeMemory memory, InstructionResolver instructionResolver) {
        this.state = state;
        this.memory = memory;
        this.instructionResolver = instructionResolver;
        this.opcodeFetcher = new NgOpcodeFetcher(state, memory);
        this.registers = new NgRegisterIndexingDecoder(state);
        this.addressingDecoder = new NgIndirectAddressingDecoder(state, memory, opcodeFetcher);
    }

    public void runNextInstruction() throws CpuException, MemoryException {
        byte opcode = opcodeFetcher.nextByte();
        Instruction instruction = instructionResolver.resolve(opcode);
        instruction.execute(state, memory, opcodeFetcher, registers, addressingDecoder);
    }
}
