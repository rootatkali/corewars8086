package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.*;
import il.co.codeguru.corewars8086.memory.RealModeAddress;

import static il.co.codeguru.corewars8086.cpu.instructions.ArithmeticUtils.*;
import static il.co.codeguru.corewars8086.cpu.instructions.BitwiseUtils.*;
import static il.co.codeguru.corewars8086.cpu.instructions.FlowUtils.callFar;
import static il.co.codeguru.corewars8086.cpu.instructions.FlowUtils.callNear;
import static il.co.codeguru.corewars8086.cpu.instructions.StackUtils.push;
import static il.co.codeguru.corewars8086.utils.Unsigned.*;

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
    
    /**
     * 0xF6 - <?> byte ptr [X]
     */
    private static final Instruction OMNI_MEM8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
    
        if (addressingDecoder.getRegisterIndex() < 0 || addressingDecoder.getRegisterIndex() >= 8) {
            throw new IllegalStateException();
        }
        
        switch (addressingDecoder.getRegisterIndex()) {
            case 0:  // TEST imm8
                and8(state, addressingDecoder.getMemory8(), opcodeFetcher.nextByte());
                break;
            case 1:
                throw new InvalidOpcodeException("0xF6 1 invalid");
            case 2:  // NOT
                addressingDecoder.setMemory8((byte) (addressingDecoder.getMemory8() ^ 0xFF));
                break;
            case 3:  // NEG
                addressingDecoder.setMemory8(sub8(state, (byte) 0, addressingDecoder.getMemory8()));
                break;
            case 4:  // MUL
                short result = (short) (unsignedByte(state.getAl()) * unsignedByte(addressingDecoder.getMemory8()));
                state.setAh((byte) (result >> 8));
                state.setAl((byte) result);
                
                state.setOverflowFlag(state.getAh() != 0);
                state.setCarryFlag(state.getAh() != 0);
                break;
            case 5:  // IMUL
                throw new UnimplementedOpcodeException();
            case 6:  // DIV
                int ax = unsignedShort(state.getAx());
                short divisor = unsignedByte(addressingDecoder.getMemory8());
                if (divisor == 0) {
                    throw new DivisionException();
                }
                
                short quotient = (short) (ax / divisor);
                if (quotient > 0xFF) {  // divide overflow ?
                    throw new DivisionException();
                }
                
                state.setAl((byte) quotient);
                state.setAh((byte) (ax % divisor));
                break;
            case 7:  // IDIV
                throw new UnimplementedOpcodeException();
        }
    };
    
    /**
     * 0xF7 - <?> word ptr [X]
     */
    private static final Instruction OMNI_MEM16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
    
        if (addressingDecoder.getRegisterIndex() < 0 || addressingDecoder.getRegisterIndex() >= 8) {
            throw new IllegalStateException();
        }
        
        switch (addressingDecoder.getRegisterIndex()) {
            case 0:  // TEST imm16
                and16(state, addressingDecoder.getMemory16(), opcodeFetcher.nextWord());
                break;
            case 1:
                throw new InvalidOpcodeException("0xF7 1 invalid");
            case 2:  // NOT
                addressingDecoder.setMemory16((short) (addressingDecoder.getMemory16() ^ 0xFFFF));
                break;
            case 3:  // NEG
                addressingDecoder.setMemory16(sub16(state, (short) 0, addressingDecoder.getMemory16()));
                break;
            case 4:  // MUL
                int result = unsignedShort(state.getAx()) * unsignedShort(addressingDecoder.getMemory16());
                state.setDx((short) (result >> 16));
                state.setAx((short) result);
                
                state.setOverflowFlag(state.getDx() != 0);
                state.setCarryFlag(state.getDx() != 0);
                break;
            case 5:  // IMUL
                throw new UnimplementedOpcodeException();
            case 6:  // DIV
                long dxax = unsignedInt(unsignedShort(state.getDx()) << 16 + unsignedShort(state.getAx()));
                int divisor = unsignedShort(addressingDecoder.getMemory16());
                if (divisor == 0) {
                    throw new DivisionException();
                }
                
                int quotient = (int) (dxax / divisor);
                if (quotient > 0xFFFF) {  // divide overflow ?
                    throw new DivisionException();
                }
                
                state.setAx((short) quotient);
                state.setDx((short) (dxax % divisor));
                break;
            case 7:  // IDIV
                throw new UnimplementedOpcodeException();
        }
    };
    
    /**
     * 0xFE - <?> byte ptr [X]
     */
    private static final Instruction OMNI_INC_DEC_MEM8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
    
        if (addressingDecoder.getRegisterIndex() < 0 || addressingDecoder.getRegisterIndex() >= 2) {
            throw new IllegalStateException();
        }
        
        if (addressingDecoder.getRegisterIndex() == 0) {  // INC
            addressingDecoder.setMemory8(inc8(state, addressingDecoder.getMemory8()));
        } else if (addressingDecoder.getRegisterIndex() == 1) {  // DEC
            addressingDecoder.setMemory8(dec8(state, addressingDecoder.getMemory8()));
        }
    };
    
    /**
     * 0xFF - <?> word ptr [X]
     */
    private static final Instruction OMNI_INC_DEC_ETC_MEM16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
    
        if (addressingDecoder.getRegisterIndex() < 0 || addressingDecoder.getRegisterIndex() >= 8) {
            throw new IllegalStateException();
        }
        
        switch (addressingDecoder.getRegisterIndex()) {
            case 0:  // INC
                addressingDecoder.setMemory16(inc16(state, addressingDecoder.getMemory16()));
                break;
            case 1:  // DEC
                addressingDecoder.setMemory16(dec16(state, addressingDecoder.getMemory16()));
                break;
            case 2:  // CALL near
                callNear(state, memory, addressingDecoder.getMemory16());
                break;
            case 3:  // CALL far
            {
                RealModeAddress address = addressingDecoder.getMemoryAddress();
                if (address == null) {
                    throw new InvalidOpcodeException();
                }
    
                short newIp = memory.readWord(address);
    
                address = new RealModeAddress(address.getSegment(), (short) (address.getOffset() + 2));
                short newCs = memory.readWord(address);
                callFar(state, memory, newCs, newIp);
            }
            break;
            case 4:  // JMP near
                // FIXME: JMP SP bug?
                state.setIp(addressingDecoder.getMemory16());
                break;
            case 5:  // JMP far
            {
                RealModeAddress address = addressingDecoder.getMemoryAddress();
                if (address == null) {
                    throw new InvalidOpcodeException();
                }
    
                short newIp = memory.readWord(address);
                address = new RealModeAddress(address.getSegment(), (short) (address.getOffset() + 2));
                short newCs = memory.readWord(address);
                
                state.setCs(newCs);
                state.setIp(newIp);
            }
            break;
            case 6:  // PUSH
                push(state, memory, addressingDecoder.getMemory16());
                break;
            case 7:
                throw new InvalidOpcodeException("0xFF 7 invalid");
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
        
        OMNIBUS_INSTRUCTIONS.add((byte) 0xF6, OMNI_MEM8);
        OMNIBUS_INSTRUCTIONS.add((byte) 0xF7, OMNI_MEM16);
        OMNIBUS_INSTRUCTIONS.add((byte) 0xFE, OMNI_INC_DEC_MEM8);
        OMNIBUS_INSTRUCTIONS.add((byte) 0xFF, OMNI_INC_DEC_ETC_MEM16);
    }
}
