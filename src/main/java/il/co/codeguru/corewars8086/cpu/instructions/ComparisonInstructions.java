package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.Instruction;
import il.co.codeguru.corewars8086.cpu.InstructionResolver;

import static il.co.codeguru.corewars8086.cpu.instructions.Utils.sub16;
import static il.co.codeguru.corewars8086.cpu.instructions.Utils.sub8;

public class ComparisonInstructions {
    public static final InstructionResolver COMPARISON_INSTRUCTIONS = new InstructionResolver();

    /**
     * 0x38 - CMP [X], reg8
     */
    private static final Instruction CMP_MEM_REG_8 = (state, memory, opcodeFetcher, registers, addressingDecoder) 
            -> sub8(state, addressingDecoder.getMemory8(), addressingDecoder.getRegister8());

    /**
     * 0x39 - CMP [X], reg16
     */
    private static final Instruction CMP_MEM_REG_16 = (state, memory, opcodeFetcher, registers, addressingDecoder) 
            -> sub16(state, addressingDecoder.getMemory16(), addressingDecoder.getRegister16());

    /**
     * 0x3A - CMP reg8, [X]
     */
    private static final Instruction CMP_REG_8_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) 
            -> sub8(state, addressingDecoder.getRegister8(), addressingDecoder.getMemory8());

    /**
     * 0x3B - CMP reg16, [X]
     */
    private static final Instruction CMP_REG_16_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) 
            -> sub16(state, addressingDecoder.getRegister16(), addressingDecoder.getMemory16());

    /**
     * 0x3C - CMP AL, imm8
     */
    private static final Instruction CMP_AL_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> sub8(state, state.getAl(), opcodeFetcher.nextByte());

    /**
     * 0x3D - CMP AX, imm16
     */
    private static final Instruction CMP_AX_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> sub16(state, state.getAx(), opcodeFetcher.nextWord());


    static {
        COMPARISON_INSTRUCTIONS.add((byte) 0x38, CMP_MEM_REG_8);
        COMPARISON_INSTRUCTIONS.add((byte) 0x39, CMP_MEM_REG_16);
        COMPARISON_INSTRUCTIONS.add((byte) 0x3A, CMP_REG_8_MEM);
        COMPARISON_INSTRUCTIONS.add((byte) 0x3B, CMP_REG_16_MEM);
        COMPARISON_INSTRUCTIONS.add((byte) 0x3C, CMP_AL_IMM8);
        COMPARISON_INSTRUCTIONS.add((byte) 0x3D, CMP_AX_IMM16);

    }
}
