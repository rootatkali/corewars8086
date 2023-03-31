package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.Instruction;
import il.co.codeguru.corewars8086.cpu.InstructionResolver;

import static il.co.codeguru.corewars8086.cpu.instructions.Utils.*;

public class SubtractionInstructions {
    public static final InstructionResolver SUBTRACTION_INSTRUCTIONS = new InstructionResolver();

    /**
     * 0x18 - SBB [X], reg8
     */
    private static final Instruction SBB_MEM_REG_8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory8(sbb8(state, addressingDecoder.getMemory8(), addressingDecoder.getRegister8()));
    };

    /**
     * 0x19 - SBB [X], reg16
     */
    private static final Instruction SBB_MEM_REG_16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory16(sbb16(state, addressingDecoder.getMemory16(), addressingDecoder.getRegister16()));
    };

    /**
     * 0x1A - SBB reg8, [X]
     */
    private static final Instruction SBB_REG_8_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister8(sbb8(state, addressingDecoder.getRegister8(), addressingDecoder.getMemory8()));
    };

    /**
     * 0x1B - SBB reg16, [X]
     */
    private static final Instruction SBB_REG_16_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister16(sbb16(state, addressingDecoder.getRegister16(), addressingDecoder.getMemory16()));
    };

    /**
     * 0x1C - SBB AL, imm8
     */
    private static final Instruction SBB_AL_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAl(sbb8(state, state.getAl(), opcodeFetcher.nextByte()));

    /**
     * 0x1D - SBB AX, imm16
     */
    private static final Instruction SBB_AX_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAx(sbb16(state, state.getAx(), opcodeFetcher.nextWord()));

    /**
     * 0x28 - SUB [X], reg8
     */
    private static final Instruction SUB_MEM_REG_8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory8(sub8(state, addressingDecoder.getMemory8(), addressingDecoder.getRegister8()));
    };

    /**
     * 0x29 - SUB [X], reg16
     */
    private static final Instruction SUB_MEM_REG_16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory16(sub16(state, addressingDecoder.getMemory16(), addressingDecoder.getRegister16()));
    };

    /**
     * 0x2A - SUB reg8, [X]
     */
    private static final Instruction SUB_REG_8_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister8(sub8(state, addressingDecoder.getRegister8(), addressingDecoder.getMemory8()));
    };

    /**
     * 0x2B - SUB reg16, [X]
     */
    private static final Instruction SUB_REG_16_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister16(sub16(state, addressingDecoder.getRegister16(), addressingDecoder.getMemory16()));
    };

    /**
     * 0x2C - SUB AL, imm8
     */
    private static final Instruction SUB_AL_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAl(sub8(state, state.getAl(), opcodeFetcher.nextByte()));

    /**
     * 0x2D - SUB AX, IMM16
     */
    private static final Instruction SUB_AX_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAx(sub16(state, state.getAx(), opcodeFetcher.nextWord()));

    static {
        SUBTRACTION_INSTRUCTIONS.add((byte) 0x18, SBB_MEM_REG_8);
        SUBTRACTION_INSTRUCTIONS.add((byte) 0x19, SBB_MEM_REG_16);
        SUBTRACTION_INSTRUCTIONS.add((byte) 0x1A, SBB_REG_8_MEM);
        SUBTRACTION_INSTRUCTIONS.add((byte) 0x1B, SBB_REG_16_MEM);
        SUBTRACTION_INSTRUCTIONS.add((byte) 0x1C, SBB_AL_IMM8);
        SUBTRACTION_INSTRUCTIONS.add((byte) 0x1D, SBB_AX_IMM16);

        SUBTRACTION_INSTRUCTIONS.add((byte) 0x28, SUB_MEM_REG_8);
        SUBTRACTION_INSTRUCTIONS.add((byte) 0x29, SUB_MEM_REG_16);
        SUBTRACTION_INSTRUCTIONS.add((byte) 0x2A, SUB_REG_8_MEM);
        SUBTRACTION_INSTRUCTIONS.add((byte) 0x2B, SUB_REG_16_MEM);
        SUBTRACTION_INSTRUCTIONS.add((byte) 0x2C, SUB_AL_IMM8);
        SUBTRACTION_INSTRUCTIONS.add((byte) 0x2D, SUB_AX_IMM16);
    }
}
