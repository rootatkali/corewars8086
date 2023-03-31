package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.Instruction;
import il.co.codeguru.corewars8086.cpu.InstructionResolver;

import static il.co.codeguru.corewars8086.cpu.instructions.Utils.*;

public class AdditionInstructions {
    public static final InstructionResolver ADDITION_INSTRUCTIONS = new InstructionResolver();

    /**
     * 0x00 - ADD [X], reg8
     */
    private static final Instruction ADD_MEM_REG_8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory8(add8(state, addressingDecoder.getMemory8(), addressingDecoder.getRegister8()));
    };

    /**
     * 0x01 - ADD [X], reg16
     */
    private static final Instruction ADD_MEM_REG_16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory16(add16(state, addressingDecoder.getMemory16(), addressingDecoder.getRegister16()));
    };

    /**
     * 0x02 - ADD reg8, [X]
     */
    private static final Instruction ADD_REG_8_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister8(add8(state, addressingDecoder.getRegister8(), addressingDecoder.getMemory8()));
    };

    /**
     * 0x03 - ADD reg16, [X]
     */
    private static final Instruction ADD_REG_16_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister16(add16(state, addressingDecoder.getRegister16(), addressingDecoder.getMemory16()));
    };

    /**
     * 0x04 - ADD AL, imm8
     */
    private static final Instruction ADD_AL_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAl(add8(state, state.getAl(), opcodeFetcher.nextByte()));

    /**
     * 0x05 - ADD AX, imm16
     */
    private static final Instruction ADD_AX_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder) ->
            state.setAx(add16(state, state.getAx(), opcodeFetcher.nextWord()));

    /**
     * 0x10 - ADC [X], reg8
     */
    private static final Instruction ADC_MEM_REG_8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory8(adc8(state, addressingDecoder.getMemory8(), addressingDecoder.getRegister8()));
    };

    /**
     * 0x11 - ADC [X], reg16
     */
    private static final Instruction ADC_MEM_REG_16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory16(adc16(state, addressingDecoder.getMemory16(), addressingDecoder.getRegister16()));
    };

    /**
     * 0x12 - ADC reg8, [X]
     */
    private static final Instruction ADC_REG_8_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister8(adc8(state, addressingDecoder.getRegister8(), addressingDecoder.getMemory8()));
    };

    /**
     * 0x13 - ADC reg16, [X]
     */
    private static final Instruction ADC_REG_16_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister16(adc16(state, addressingDecoder.getRegister16(), addressingDecoder.getMemory16()));
    };

    /**
     * 0x14 - ADC AL, imm8
     */
    private static final Instruction ADC_AL_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAl(adc8(state, state.getAl(), opcodeFetcher.nextByte()));

    /**
     * 0x15 - ADC AX, imm16
     */
    private static final Instruction ADC_AX_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAx(adc16(state, state.getAx(), opcodeFetcher.nextWord()));

    static {
        ADDITION_INSTRUCTIONS.add((byte) 0x00, ADD_MEM_REG_8);
        ADDITION_INSTRUCTIONS.add((byte) 0x01, ADD_MEM_REG_16);
        ADDITION_INSTRUCTIONS.add((byte) 0x02, ADD_REG_8_MEM);
        ADDITION_INSTRUCTIONS.add((byte) 0x03, ADD_REG_16_MEM);
        ADDITION_INSTRUCTIONS.add((byte) 0x04, ADD_AL_IMM8);
        ADDITION_INSTRUCTIONS.add((byte) 0x05, ADD_AX_IMM16);

        ADDITION_INSTRUCTIONS.add((byte) 0x10, ADC_MEM_REG_8);
        ADDITION_INSTRUCTIONS.add((byte) 0x11, ADC_MEM_REG_16);
        ADDITION_INSTRUCTIONS.add((byte) 0x12, ADC_REG_8_MEM);
        ADDITION_INSTRUCTIONS.add((byte) 0x13, ADC_REG_16_MEM);
        ADDITION_INSTRUCTIONS.add((byte) 0x14, ADC_AL_IMM8);
        ADDITION_INSTRUCTIONS.add((byte) 0x15, ADC_AX_IMM16);
    }
}
