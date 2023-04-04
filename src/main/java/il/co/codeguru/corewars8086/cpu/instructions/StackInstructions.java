package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.Instruction;
import il.co.codeguru.corewars8086.cpu.InstructionResolver;

import static il.co.codeguru.corewars8086.cpu.instructions.Utils.push;
import static il.co.codeguru.corewars8086.cpu.instructions.Utils.pop;

public class StackInstructions {
    public static final InstructionResolver STACK_INSTRUCTIONS = new InstructionResolver();

    /**
     * 0x06 - PUSH ES
     */
    private static final Instruction PUSH_ES = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> push(state, memory, state.getEs());

    /**
     * 0x07 - POP ES
     */
    private static final Instruction POP_ES = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setEs(pop(state, memory));

    /**
     * 0x0E - PUSH CS
     */
    private static final Instruction PUSH_CS = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> push(state, memory, state.getCs());

    /**
     * 0x16 - PUSH SS
     */
    private static final Instruction PUSH_SS = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> push(state, memory, state.getSs());

    /**
     * 0x17 - POP SS
     */
    private static final Instruction POP_SS = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setSs(pop(state, memory));

    /**
     * 0x1E - PUSH DS
     */
    private static final Instruction PUSH_DS = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> push(state, memory, state.getDs());

    /**
     * 0x1F - POP DS
     */
    private static final Instruction POP_DS = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> state.setDs(pop(state, memory));

    /**
     * 0x50-0x57 - PUSH reg16
     * <p>
     * This method generates the relevant instruction for each register dynamically.
     */
    private static Instruction makePushReg16(byte opcode) {
        return (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
            byte index = (byte) (opcode & 0x07);

            push(state, memory, registers.getRegister16(index));
        };
    }

    /**
     * 58-0x5F - POP reg16
     * <p>
     * This function generates the relevant instruction for each register dynamically.
     */
    private static Instruction makePopReg16(byte opcode) {
        return (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
            byte index = (byte) (opcode & 0x07);

            registers.setRegister16(index, pop(state, memory));
        };
    }

    static {
        STACK_INSTRUCTIONS.add((byte) 0x06, PUSH_ES);
        STACK_INSTRUCTIONS.add((byte) 0x07, POP_ES);
        STACK_INSTRUCTIONS.add((byte) 0x0E, PUSH_CS);
        STACK_INSTRUCTIONS.add((byte) 0x16, PUSH_SS);
        STACK_INSTRUCTIONS.add((byte) 0x17, POP_SS);
        STACK_INSTRUCTIONS.add((byte) 0x1E, PUSH_DS);
        STACK_INSTRUCTIONS.add((byte) 0x1F, POP_DS);

        for (byte opcode = (byte) 0x50; opcode < (byte) 0x58; opcode++) {
            STACK_INSTRUCTIONS.add(opcode, makePushReg16(opcode));
        }

        for (byte opcode = (byte) 0x58; opcode < (byte) 0x60; opcode++) {
            STACK_INSTRUCTIONS.add(opcode, makePopReg16(opcode));
        }
    }
}
