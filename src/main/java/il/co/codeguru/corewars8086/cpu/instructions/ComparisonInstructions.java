package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.Instruction;
import il.co.codeguru.corewars8086.cpu.InstructionResolver;

import static il.co.codeguru.corewars8086.cpu.instructions.Utils.*;

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

    /**
     * 0x84 - TEST reg8, [X]
     */
    private static final Instruction TEST_REG_8_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        and8(state, addressingDecoder.getRegister8(), addressingDecoder.getMemory8());
    };

    /**
     * 0x85 - TEST reg16, [X]
     */
    private static final Instruction TEST_REG_16_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        and16(state, addressingDecoder.getRegister16(), addressingDecoder.getMemory16());
    };

    /**
     * 0xA6 - CMPSB
     */
    private static final Instruction CMPSB = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> cmpsb(state, memory);

    /**
     * 0xA7 - CMPSW
     */
    private static final Instruction CMPSW = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> cmpsw(state, memory);

    /**
     * 0xA8 - TEST AL, imm8
     */
    private static final Instruction TEST_AL_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> and8(state, state.getAl(), opcodeFetcher.nextByte());

    /**
     * 0xA9 - TEST AX, imm16
     */
    private static final Instruction TEST_AX_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> and16(state, state.getAx(), opcodeFetcher.nextWord());

    /**
     * 0xAE - SCASB
     */
    private static final Instruction SCASB = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> scasb(state, memory);

    /**
     * 0xAF - SCASW
     */
    private static final Instruction SCASW = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> scasw(state, memory);

    static {
        COMPARISON_INSTRUCTIONS.add((byte) 0x38, CMP_MEM_REG_8);
        COMPARISON_INSTRUCTIONS.add((byte) 0x39, CMP_MEM_REG_16);
        COMPARISON_INSTRUCTIONS.add((byte) 0x3A, CMP_REG_8_MEM);
        COMPARISON_INSTRUCTIONS.add((byte) 0x3B, CMP_REG_16_MEM);
        COMPARISON_INSTRUCTIONS.add((byte) 0x3C, CMP_AL_IMM8);
        COMPARISON_INSTRUCTIONS.add((byte) 0x3D, CMP_AX_IMM16);

        COMPARISON_INSTRUCTIONS.add((byte) 0x84, TEST_REG_8_MEM);
        COMPARISON_INSTRUCTIONS.add((byte) 0x85, TEST_REG_16_MEM);
        COMPARISON_INSTRUCTIONS.add((byte) 0xA8, TEST_AL_IMM8);
        COMPARISON_INSTRUCTIONS.add((byte) 0xA9, TEST_AX_IMM16);

        COMPARISON_INSTRUCTIONS.add((byte) 0xA6, CMPSB);
        COMPARISON_INSTRUCTIONS.add((byte) 0xA7, CMPSW);
        COMPARISON_INSTRUCTIONS.add((byte) 0xAE, SCASB);
        COMPARISON_INSTRUCTIONS.add((byte) 0xAF, SCASW);
    }
}
