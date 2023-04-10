package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.Instruction;
import il.co.codeguru.corewars8086.cpu.InstructionResolver;
import il.co.codeguru.corewars8086.cpu.NgCpuState;
import il.co.codeguru.corewars8086.cpu.NgOpcodeFetcher;
import il.co.codeguru.corewars8086.memory.MemoryException;

import static il.co.codeguru.corewars8086.cpu.instructions.FlowUtils.callFar;

public class FlowInstructions {
    public static final InstructionResolver FLOW_INSTRUCTIONS = new InstructionResolver();

    /**
     * 0x70 - JO
     */
    private static final Instruction JO = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, state.getOverflowFlag());

    /**
     * 0x71 - JNO
     */
    private static final Instruction JNO = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, !state.getOverflowFlag());

    /**
     * 0x72 - JC, JB, JNAE
     */
    private static final Instruction JC_JB_JNAE = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, state.getCarryFlag());

    /**
     * 0x73 - JNC, JNB, JAE
     */
    private static final Instruction JNC_JNB_JAE = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, !state.getCarryFlag());

    /**
     * 0x74 - JE, JZ
     */
    private static final Instruction JE_JZ = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, state.getZeroFlag());

    /**
     * 0x75 - JNE, JNZ
     */
    private static final Instruction JNE_JNZ = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, !state.getZeroFlag());

    /**
     * 0x76 - JBE, JNA
     */
    private static final Instruction JBE_JNA = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, (state.getCarryFlag() || state.getZeroFlag()));

    /**
     * 0x77 - JNBE, JA
     */
    private static final Instruction JNBE_JA = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, !(state.getCarryFlag() || state.getZeroFlag()));

    /**
     * 0x78 - JS
     */
    private static final Instruction JS = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, state.getSignFlag());

    /**
     * 0x79 - JNS
     */
    private static final Instruction JNS = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, !state.getSignFlag());

    /**
     * 0x7A - JP, JPE
     */
    private static final Instruction JP_JPE = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, state.getParityFlag());

    /**
     * 0x7B - JNP, JPO
     */
    private static final Instruction JNP_JPO = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, !state.getParityFlag());

    /**
     * 0x7C - JL, JNGE
     */
    private static final Instruction JL_JNGE = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, (state.getSignFlag() != state.getOverflowFlag()));

    /**
     * 0x7D - JNL, JGE
     */
    private static final Instruction JNL_JGE = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, (state.getSignFlag() == state.getOverflowFlag()));

    /**
     * 0x7E - JLE, JNG
     */
    private static final Instruction JLE_JNG = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, state.getZeroFlag() || (state.getSignFlag() != state.getOverflowFlag()));

    /**
     * 0x7F - JNLE, JG
     */
    private static final Instruction JNLE_JG = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> conditionalJump(state, opcodeFetcher, !state.getZeroFlag() && (state.getSignFlag() == state.getOverflowFlag()));

    private static void conditionalJump(NgCpuState state,
                                        NgOpcodeFetcher opcodeFetcher,
                                        boolean branch) throws MemoryException {
        byte offset = opcodeFetcher.nextByte();

        if (branch) {
            state.setIp((short) (state.getIp() + offset));
        }
    }

    /**
     * 0x9A - CALL far imm16:imm16
     */
    private static final Instruction CALL_FAR_IMM32 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        short newIp = opcodeFetcher.nextWord();
        short newCs = opcodeFetcher.nextWord();
        callFar(state, memory, newCs, newIp);
    };

    static {
        FLOW_INSTRUCTIONS.add((byte) 0x70, JO);
        FLOW_INSTRUCTIONS.add((byte) 0x71, JNO);
        FLOW_INSTRUCTIONS.add((byte) 0x72, JC_JB_JNAE);
        FLOW_INSTRUCTIONS.add((byte) 0x73, JNC_JNB_JAE);
        FLOW_INSTRUCTIONS.add((byte) 0x74, JE_JZ);
        FLOW_INSTRUCTIONS.add((byte) 0x75, JNE_JNZ);
        FLOW_INSTRUCTIONS.add((byte) 0x76, JBE_JNA);
        FLOW_INSTRUCTIONS.add((byte) 0x77, JNBE_JA);
        FLOW_INSTRUCTIONS.add((byte) 0x78, JS);
        FLOW_INSTRUCTIONS.add((byte) 0x79, JNS);
        FLOW_INSTRUCTIONS.add((byte) 0x7A, JP_JPE);
        FLOW_INSTRUCTIONS.add((byte) 0x7B, JNP_JPO);
        FLOW_INSTRUCTIONS.add((byte) 0x7C, JL_JNGE);
        FLOW_INSTRUCTIONS.add((byte) 0x7D, JNL_JGE);
        FLOW_INSTRUCTIONS.add((byte) 0x7E, JLE_JNG);
        FLOW_INSTRUCTIONS.add((byte) 0x7F, JNLE_JG);

        FLOW_INSTRUCTIONS.add((byte) 0x9A, CALL_FAR_IMM32);
    }
}
