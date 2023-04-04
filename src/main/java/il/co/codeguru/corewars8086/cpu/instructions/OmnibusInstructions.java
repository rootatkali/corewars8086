package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.CpuException;
import il.co.codeguru.corewars8086.cpu.Instruction;
import il.co.codeguru.corewars8086.cpu.InstructionResolver;

import static il.co.codeguru.corewars8086.cpu.instructions.Utils.*;

/**
 * Omnibus instructions - instructions that handle multiple commands depending on their operand.
 * They are ugly but a necessary evil. Please forgive this implementation.
 */
public class OmnibusInstructions {
    public static final InstructionResolver OMNIBUS_INSTRUCTIONS = new InstructionResolver();

    /**
     * 0x80, 0x82 - <?> byte ptr [X], imm8
     */
    private static final Instruction OMNIBUS_MEM_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();

        if (addressingDecoder.getRegisterIndex() < 0 || addressingDecoder.getRegisterIndex() >= 8) {
            throw new IllegalStateException();
        }

        byte currentValue = addressingDecoder.getMemory8();
        byte operand = opcodeFetcher.nextByte();
        byte result = currentValue;

        switch (addressingDecoder.getRegisterIndex()) {
            case 0: // ADD
                result = add8(state, currentValue, operand);
                break;
            case 1: // OR
                result = or8(state, currentValue, operand);
                break;
            case 2: // ADC
                result = adc8(state, currentValue, operand);
                break;
            case 3: // SBB
                result = sbb8(state, currentValue, operand);
                break;
            case 4: // AND
                result = and8(state, currentValue, operand);
                break;
            case 5: // SUB
                result = sub8(state, currentValue, operand);
                break;
            case 6: // XOR
                result = xor8(state, currentValue, operand);
                break;
            case 7: // CMP
                sub8(state, currentValue, operand);
                break;
        }

        addressingDecoder.setMemory8(result);
    };

    /**
     * 0x81 - <?> word ptr [X], imm16
     */
    private static final Instruction OMNIBUS_MEM_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();

        if (addressingDecoder.getRegisterIndex() < 0 || addressingDecoder.getRegisterIndex() >= 8) {
            throw new IllegalStateException();
        }

        short currentValue = addressingDecoder.getMemory16();
        short operand = opcodeFetcher.nextWord();
        short result = currentValue;

        switch (addressingDecoder.getRegisterIndex()) {
            case 0: // ADD
                result = add16(state, currentValue, operand);
                break;
            case 1: // OR
                result = or16(state, currentValue, operand);
                break;
            case 2: // ADC
                result = adc16(state, currentValue, operand);
                break;
            case 3: // SBB
                result = sbb16(state, currentValue, operand);
                break;
            case 4: // AND
                result = and16(state, currentValue, operand);
                break;
            case 5: // SUB
                result = sub16(state, currentValue, operand);
                break;
            case 6: // XOR
                result = xor16(state, currentValue, operand);
                break;
            case 7: // CMP
                sub16(state, currentValue, operand);
                break;
        }

        addressingDecoder.setMemory16(result);
    };

    /**
     * 0x83 - <?> word ptr [X], sign-extended imm8
     */
    private static final Instruction OMNIBUS_MEM_SIGN_EXTENDED_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();

        if (addressingDecoder.getRegisterIndex() < 0 || addressingDecoder.getRegisterIndex() >= 8) {
            throw new IllegalStateException();
        }

        short currentValue = addressingDecoder.getMemory16();
        short operand = opcodeFetcher.nextByte();
        short result = currentValue;

        switch (addressingDecoder.getRegisterIndex()) {
            case 0: // ADD
                result = add16(state, currentValue, operand);
                break;
            case 1: // OR
                result = or16(state, currentValue, operand);
                break;
            case 2: // ADC
                result = adc16(state, currentValue, operand);
                break;
            case 3: // SBB
                result = sbb16(state, currentValue, operand);
                break;
            case 4: // AND
                result = and16(state, currentValue, operand);
                break;
            case 5: // SUB
                result = sub16(state, currentValue, operand);
                break;
            case 6: // XOR
                result = xor16(state, currentValue, operand);
                break;
            case 7: // CMP
                sub16(state, currentValue, operand);
                break;
        }

        addressingDecoder.setMemory16(result);
    };

    static {
        OMNIBUS_INSTRUCTIONS.add((byte) 0x80, OMNIBUS_MEM_IMM8);
        OMNIBUS_INSTRUCTIONS.add((byte) 0x81, OMNIBUS_MEM_IMM16);
        OMNIBUS_INSTRUCTIONS.add((byte) 0x82, OMNIBUS_MEM_IMM8);
        OMNIBUS_INSTRUCTIONS.add((byte) 0x83, OMNIBUS_MEM_SIGN_EXTENDED_IMM8);
    }
}
