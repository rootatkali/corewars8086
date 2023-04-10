package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.Instruction;
import il.co.codeguru.corewars8086.cpu.InstructionResolver;
import il.co.codeguru.corewars8086.cpu.InvalidOpcodeException;
import il.co.codeguru.corewars8086.memory.RealModeAddress;

import static il.co.codeguru.corewars8086.cpu.instructions.Utils.*;

public class SetInstructions {
    public static final InstructionResolver SET_INSTRUCTIONS = new InstructionResolver();

    /**
     * 0x86 - XCHG reg8, [X]
     */
    private static final Instruction XCHG_REG_8_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        byte tmp = addressingDecoder.getRegister8();
        addressingDecoder.setRegister8(addressingDecoder.getMemory8());
        addressingDecoder.setMemory8(tmp);
    };

    /**
     * 0x87 - XCHG reg16, [X]
     */
    private static final Instruction XCHG_REG_16_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        short tmp = addressingDecoder.getRegister16();
        addressingDecoder.setRegister16(addressingDecoder.getMemory16());
        addressingDecoder.setMemory16(tmp);
    };

    /**
     * 0x88 - MOV [X], reg8
     */
    private static final Instruction MOV_MEM_REG_8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory8(addressingDecoder.getRegister8());
    };

    /**
     * 0x89 - MOV [X], reg16
     */
    private static final Instruction MOV_MEM_REG_16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory16(addressingDecoder.getRegister16());
    };

    /**
     * 0x8A - MOV reg8, [X]
     */
    private static final Instruction MOV_REG_8_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister8(addressingDecoder.getMemory8());
    };

    /**
     * 0x8B - MOV reg16, [X]
     */
    private static final Instruction MOV_REG_16_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setRegister16(addressingDecoder.getMemory16());
    };

    /**
     * 0x8C - MOV [X], seg
     */
    private static final Instruction MOV_MEM_16_SEG = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory16(addressingDecoder.getSegment());
    };

    /**
     * 0x8D - LEA reg16, [X]
     */
    private static final Instruction LEA_REG_16_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        RealModeAddress address = addressingDecoder.getMemoryAddress();

        if (address == null) throw new InvalidOpcodeException("Invalid instruction: LEA reg16, reg16");

        addressingDecoder.setRegister16(address.getOffset());
    };

    /**
     * 0x8E - MOV seg, [X]
     */
    private static final Instruction MOV_SEG_MEM_16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setSegment(addressingDecoder.getMemory16());
    };

    /**
     * 0x90-0x97 - XCHG reg16, AX
     * <p>
     * This method generates the relevant instruction for each register dynamically.
     */
    private static Instruction makeXchgReg16Ax(byte opcode) {
        return (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
            byte index = (byte) (opcode & 0x07);

            short tmp = registers.getRegister16(index);
            registers.setRegister16(index, state.getAx());
            state.setAx(tmp);
        };
    }

    /**
     * 0x98 - CBW
     */
    private static final Instruction CBW = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        if (state.getAl() < 0) {
            state.setAh((byte) 0xFF);
        } else {
            state.setAh((byte) 0x00);
        }
    };

    /**
     * 0x99 - CWD
     */
    private static final Instruction CWD = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        if (state.getAx() < 0) {
            state.setDx((short) 0xFFFF);
        } else {
            state.setDx((short) 0x0000);
        }
    };

    /**
     * 0x9E - ???? (TODO Find name of instruction)
     */
    private static final Instruction OPCODE_0x9E = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        short flags = state.getFlags();
        flags &= 0xFF00;
        flags |= state.getAh();
        state.setFlags(flags);
    };

    /**
     * 0x9F - LAHF
     */
    private static final Instruction LAHF = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setAh((byte) state.getFlags());

    /**
     * 0xA0 - MOV AL, [imm16]
     */
    private static final Instruction MOV_AL_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        RealModeAddress address = new RealModeAddress(state.getDs(), opcodeFetcher.nextWord());
        state.setAl(memory.readByte(address));
    };

    /**
     * 0xA1 - MOV AX, [imm16]
     */
    private static final Instruction MOV_AX_MEM = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        RealModeAddress address = new RealModeAddress(state.getDs(), opcodeFetcher.nextWord());
        state.setAx(memory.readWord(address));
    };

    /**
     * 0xA2 - MOV [imm16], AL
     */
    private static final Instruction MOV_MEM_AL = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        RealModeAddress address = new RealModeAddress(state.getDs(), opcodeFetcher.nextWord());
        memory.writeByte(address, state.getAl());
    };

    /**
     * 0xA3 - MOV [imm16], AX
     */
    private static final Instruction MOV_MEM_AX = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        RealModeAddress address = new RealModeAddress(state.getDs(), opcodeFetcher.nextWord());
        memory.writeWord(address, state.getAx());
    };

    /**
     * 0xA4 - MOVSB
     */
    private static final Instruction MOVSB = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> movsb(state, memory);

    /**
     * 0xA5 - MOVSW
     */
    private static final Instruction MOVSW = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> movsw(state, memory);

    /**
     * 0xAA - STOSB
     */
    private static final Instruction STOSB = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> stosb(state, memory);

    /**
     * 0xAB - STOSW
     */
    private static final Instruction STOSW = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> stosw(state, memory);

    /**
     * 0xAC - LODSB
     */
    private static final Instruction LODSB = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> lodsb(state, memory);

    /**
     * 0xAD - LODSW
     */
    private static final Instruction LODSW = (state, memory, opcodeFetcher, registers, addressingDecoder)
            ->  lodsw(state, memory);

    static {
        SET_INSTRUCTIONS.add((byte) 0x86, XCHG_REG_8_MEM);
        SET_INSTRUCTIONS.add((byte) 0x87, XCHG_REG_16_MEM);

        SET_INSTRUCTIONS.add((byte) 0x88, MOV_MEM_REG_8);
        SET_INSTRUCTIONS.add((byte) 0x89, MOV_MEM_REG_16);
        SET_INSTRUCTIONS.add((byte) 0x8A, MOV_REG_8_MEM);
        SET_INSTRUCTIONS.add((byte) 0x8B, MOV_REG_16_MEM);
        SET_INSTRUCTIONS.add((byte) 0x8C, MOV_MEM_16_SEG);
        SET_INSTRUCTIONS.add((byte) 0x8E, MOV_SEG_MEM_16);
        SET_INSTRUCTIONS.add((byte) 0xA0, MOV_AL_MEM);
        SET_INSTRUCTIONS.add((byte) 0xA1, MOV_AX_MEM);
        SET_INSTRUCTIONS.add((byte) 0xA2, MOV_MEM_AL);
        SET_INSTRUCTIONS.add((byte) 0xA3, MOV_MEM_AX);

        SET_INSTRUCTIONS.add((byte) 0x8D, LEA_REG_16_MEM);

        for (byte opcode = (byte) 0x90; opcode < (byte) 0x98; opcode++) {
            SET_INSTRUCTIONS.add(opcode, makeXchgReg16Ax(opcode));
        }

        SET_INSTRUCTIONS.add((byte) 0x98, CBW);
        SET_INSTRUCTIONS.add((byte) 0x99, CWD);

        SET_INSTRUCTIONS.add((byte) 0x9E, OPCODE_0x9E);
        SET_INSTRUCTIONS.add((byte) 0x9F, LAHF);

        SET_INSTRUCTIONS.add((byte) 0xA4, MOVSB);
        SET_INSTRUCTIONS.add((byte) 0xA5, MOVSW);
        SET_INSTRUCTIONS.add((byte) 0xAA, STOSB);
        SET_INSTRUCTIONS.add((byte) 0xAB, STOSW);
        SET_INSTRUCTIONS.add((byte) 0xAC, LODSB);
        SET_INSTRUCTIONS.add((byte) 0xAD, LODSW);
    }
}
