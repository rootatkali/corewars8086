package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.Instruction;
import il.co.codeguru.corewars8086.cpu.InstructionResolver;
import il.co.codeguru.corewars8086.cpu.InvalidOpcodeException;

import static il.co.codeguru.corewars8086.cpu.instructions.ArithmeticUtils.*;
import static il.co.codeguru.corewars8086.cpu.instructions.BitwiseUtils.*;

/**
 * Omnibus instructions - the same opcode represents multiple instructions, based on the operands.
 * They are ugly but a necessary evil. Please forgive this implementation (or improve it!).
 */
public class OmnibusInstructions {
    public static final InstructionResolver OMNIBUS_INSTRUCTIONS = new InstructionResolver();

    /**
     * 0x80, 0x82 - <?> byte ptr [X], imm8
     */
    private static final Instruction OMNI_ARITHMETIC_MEM_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
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
    private static final Instruction OMNI_ARITHMETIC_MEM_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
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
    private static final Instruction OMNI_ARITHMETIC_MEM_SEIMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
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

    /**
     * 0xD0 - <?> byte ptr [X], 1
     */
    private static final Instruction OMNI_BITWISE_MEM8_1 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();

        if (addressingDecoder.getRegisterIndex() < 0 || addressingDecoder.getRegisterIndex() >= 8) {
            throw new IllegalStateException();
        }

        switch (addressingDecoder.getRegisterIndex()) {
            case 0:
                rol8(state, addressingDecoder, 1);
                break;
            case 1:
                ror8(state, addressingDecoder, 1);
                break;
            case 2:
                rcl8(state, addressingDecoder, 1);
                break;
            case 3:
                rcr8(state, addressingDecoder, 1);
                break;
            case 4:
                shl8(state, addressingDecoder, 1);
                break;
            case 5:
                shr8(state, addressingDecoder, 1);
                break;
            case 6:
                throw new InvalidOpcodeException("Opcode 0xD0 6 invalid");
            case 7:
                sar8(state, addressingDecoder, 1);
                break;
        }
    };

    /**
     * 0xD1 - <?> word ptr [X], 1
     */
    private static final Instruction OMNI_BITWISE_MEM16_1 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();

        if (addressingDecoder.getRegisterIndex() < 0 || addressingDecoder.getRegisterIndex() >= 8) {
            throw new IllegalStateException();
        }

        switch (addressingDecoder.getRegisterIndex()) {
            case 0:
                rol16(state, addressingDecoder, 1);
                break;
            case 1:
                ror16(state, addressingDecoder, 1);
                break;
            case 2:
                rcl16(state, addressingDecoder, 1);
                break;
            case 3:
                rcr16(state, addressingDecoder, 1);
                break;
            case 4:
                shl16(state, addressingDecoder, 1);
                break;
            case 5:
                shr16(state, addressingDecoder, 1);
                break;
            case 6:
                throw new InvalidOpcodeException("Opcode 0xD1 6 invalid");
            case 7:
                sar16(state, addressingDecoder, 1);
                break;
        }
    };
    
    /**
     * 0xD2 - <?> byte ptr [X], CL
     */
    private static final Instruction OMNI_BITWISE_MEM8_CL = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        
        if (addressingDecoder.getRegisterIndex() < 0 || addressingDecoder.getRegisterIndex() >= 8) {
            throw new IllegalStateException();
        }
        
        switch (addressingDecoder.getRegisterIndex()) {
            case 0:
                rol8(state, addressingDecoder, state.getCl());
                break;
            case 1:
                ror8(state, addressingDecoder, state.getCl());
                break;
            case 2:
                rcl8(state, addressingDecoder, state.getCl());
                break;
            case 3:
                rcr8(state, addressingDecoder, state.getCl());
                break;
            case 4:
                shl8(state, addressingDecoder, state.getCl());
                break;
            case 5:
                shr8(state, addressingDecoder, state.getCl());
                break;
            case 6:
                throw new InvalidOpcodeException("Opcode 0xD2 6 invalid");
            case 7:
                sar8(state, addressingDecoder, state.getCl());
                break;
        }
    };
    
    /**
     * 0xD3 - <?> byte ptr [X], CL
     */
    private static final Instruction OMNI_BITWISE_MEM16_CL = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        
        if (addressingDecoder.getRegisterIndex() < 0 || addressingDecoder.getRegisterIndex() >= 8) {
            throw new IllegalStateException();
        }
        
        switch (addressingDecoder.getRegisterIndex()) {
            case 0:
                rol16(state, addressingDecoder, state.getCl());
                break;
            case 1:
                ror16(state, addressingDecoder, state.getCl());
                break;
            case 2:
                rcl16(state, addressingDecoder, state.getCl());
                break;
            case 3:
                rcr16(state, addressingDecoder, state.getCl());
                break;
            case 4:
                shl16(state, addressingDecoder, state.getCl());
                break;
            case 5:
                shr16(state, addressingDecoder, state.getCl());
                break;
            case 6:
                throw new InvalidOpcodeException("Opcode 0xD3 6 invalid");
            case 7:
                sar16(state, addressingDecoder, state.getCl());
                break;
        }
    };
    
    static {
        OMNIBUS_INSTRUCTIONS.add((byte) 0x80, OMNI_ARITHMETIC_MEM_IMM8);
        OMNIBUS_INSTRUCTIONS.add((byte) 0x81, OMNI_ARITHMETIC_MEM_IMM16);
        OMNIBUS_INSTRUCTIONS.add((byte) 0x82, OMNI_ARITHMETIC_MEM_IMM8);
        OMNIBUS_INSTRUCTIONS.add((byte) 0x83, OMNI_ARITHMETIC_MEM_SEIMM8);

        OMNIBUS_INSTRUCTIONS.add((byte) 0xD0, OMNI_BITWISE_MEM8_1);
        OMNIBUS_INSTRUCTIONS.add((byte) 0xD1, OMNI_BITWISE_MEM16_1);
        OMNIBUS_INSTRUCTIONS.add((byte) 0xD2, OMNI_BITWISE_MEM8_CL);
        OMNIBUS_INSTRUCTIONS.add((byte) 0xD3, OMNI_BITWISE_MEM16_CL);
    }
}
