package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.Instruction;
import il.co.codeguru.corewars8086.cpu.InstructionResolver;

import static il.co.codeguru.corewars8086.cpu.instructions.BitwiseUtils.*;

public class BitwiseInstructions {
    public static final InstructionResolver BITWISE_INSTRUCTIONS = new InstructionResolver();

    /**
     * 0x08 - OR [X], reg8
     */
    private static final Instruction OR_MEM_REG_8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory8(or8(state, addressingDecoder.getMemory8(), addressingDecoder.getRegister8()));
    };

    /**
     * 0x09 - OR [X], reg16
     */
    private static final Instruction OR_MEM_REG_16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory16(or16(state, addressingDecoder.getMemory16(), addressingDecoder.getRegister16()));
    };

    /**
     * 0x0A - OR reg8, [X]
     */
    private static final Instruction OR_REG_8_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister8(or8(state, addressingDecoder.getRegister8(), addressingDecoder.getMemory8()));
    };

    /**
     * 0x0B - OR reg16, [X]
     */
    private static final Instruction OR_REG_16_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister16(or16(state, addressingDecoder.getRegister16(), addressingDecoder.getMemory16()));
    };

    /**
     * 0x0C - OR AL, imm8
     */
    private static final Instruction OR_AL_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAl(or8(state, state.getAl(), opcodeFetcher.nextByte()));

    /**
     * 0x0D - OR AX, imm16
     */
    private static final Instruction OR_AX_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAx(or16(state, state.getAx(), opcodeFetcher.nextWord()));

    /**
     * 0x20 - AND [X], reg8
     */
    private static final Instruction AND_MEM_REG_8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory8(and8(state, addressingDecoder.getMemory8(), addressingDecoder.getRegister8()));
    };

    /**
     * 0x21 - AND [X], reg16
     */
    private static final Instruction AND_MEM_REG_16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory16(and16(state, addressingDecoder.getMemory16(), addressingDecoder.getRegister16()));
    };

    /**
     * 0x22 - AND reg8, [X]
     */
    private static final Instruction AND_REG_8_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister8(and8(state, addressingDecoder.getRegister8(), addressingDecoder.getMemory8()));
    };

    /**
     * 0x23 - AND reg16, [X]
     */
    private static final Instruction AND_REG_16_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister16(and16(state, addressingDecoder.getRegister16(), addressingDecoder.getMemory16()));
    };

    /**
     * 0x24 - AND AL, imm8
     */
    private static final Instruction AND_AL_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAl(and8(state, state.getAl(), opcodeFetcher.nextByte()));

    /**
     * 0x25 - AND AX, imm16
     */
    private static final Instruction AND_AX_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAx(and16(state, state.getAx(), opcodeFetcher.nextWord()));

    /**
     * 0x30 - XOR [X], reg8
     */
    private static final Instruction XOR_MEM_REG_8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory8(xor8(state, addressingDecoder.getMemory8(), addressingDecoder.getRegister8()));
    };

    /**
     * 0x31 - XOR [X], reg16
     */
    private static final Instruction XOR_MEM_REG_16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory16(xor16(state, addressingDecoder.getMemory16(), addressingDecoder.getRegister16()));
    };

    /**
     * 0x32 - XOR reg8, [X]
     */
    private static final Instruction XOR_REG_8_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister8(xor8(state, addressingDecoder.getRegister8(), addressingDecoder.getMemory8()));
    };

    /**
     * 0x33 - XOR reg16, [X]
     */
    private static final Instruction XOR_REG_16_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister16(xor16(state, addressingDecoder.getRegister16(), addressingDecoder.getMemory16()));
    };

    /**
     * 0x34 - XOR AL, imm8
     */
    private static final Instruction XOR_AL_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAl(xor8(state, state.getAl(), opcodeFetcher.nextByte()));

    /**
     * 0x35 - XOR AX, imm16
     */
    private static final Instruction XOR_AX_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAx(xor16(state, state.getAx(), opcodeFetcher.nextWord()));

    static {
        BITWISE_INSTRUCTIONS.add((byte) 0x08, OR_MEM_REG_8);
        BITWISE_INSTRUCTIONS.add((byte) 0x09, OR_MEM_REG_16);
        BITWISE_INSTRUCTIONS.add((byte) 0x0A, OR_REG_8_MEM);
        BITWISE_INSTRUCTIONS.add((byte) 0x0B, OR_REG_16_MEM);
        BITWISE_INSTRUCTIONS.add((byte) 0x0C, OR_AL_IMM8);
        BITWISE_INSTRUCTIONS.add((byte) 0x0D, OR_AX_IMM16);

        BITWISE_INSTRUCTIONS.add((byte) 0x20, AND_MEM_REG_8);
        BITWISE_INSTRUCTIONS.add((byte) 0x21, AND_MEM_REG_16);
        BITWISE_INSTRUCTIONS.add((byte) 0x22, AND_REG_8_MEM);
        BITWISE_INSTRUCTIONS.add((byte) 0x23, AND_REG_16_MEM);
        BITWISE_INSTRUCTIONS.add((byte) 0x24, AND_AL_IMM8);
        BITWISE_INSTRUCTIONS.add((byte) 0x25, AND_AX_IMM16);

        BITWISE_INSTRUCTIONS.add((byte) 0x30, XOR_MEM_REG_8);
        BITWISE_INSTRUCTIONS.add((byte) 0x31, XOR_MEM_REG_16);
        BITWISE_INSTRUCTIONS.add((byte) 0x32, XOR_REG_8_MEM);
        BITWISE_INSTRUCTIONS.add((byte) 0x33, XOR_REG_16_MEM);
        BITWISE_INSTRUCTIONS.add((byte) 0x34, XOR_AL_IMM8);
        BITWISE_INSTRUCTIONS.add((byte) 0x35, XOR_AX_IMM16);
    }
}
