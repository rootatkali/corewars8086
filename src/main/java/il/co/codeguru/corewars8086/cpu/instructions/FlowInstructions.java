package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.Instruction;
import il.co.codeguru.corewars8086.cpu.InstructionResolver;

import static il.co.codeguru.corewars8086.cpu.instructions.FlowUtils.callFar;
import static il.co.codeguru.corewars8086.cpu.instructions.FlowUtils.callNear;

public class FlowInstructions {
    public static final InstructionResolver FLOW_INSTRUCTIONS = new InstructionResolver();

    /**
     * 0x70 - JO
     */
    private static final Instruction JO = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, state.getOverflowFlag());

    /**
     * 0x71 - JNO
     */
    private static final Instruction JNO = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, !state.getOverflowFlag());

    /**
     * 0x72 - JC, JB, JNAE
     */
    private static final Instruction JC_JB_JNAE = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, state.getCarryFlag());

    /**
     * 0x73 - JNC, JNB, JAE
     */
    private static final Instruction JNC_JNB_JAE = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, !state.getCarryFlag());

    /**
     * 0x74 - JE, JZ
     */
    private static final Instruction JE_JZ = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, state.getZeroFlag());

    /**
     * 0x75 - JNE, JNZ
     */
    private static final Instruction JNE_JNZ = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, !state.getZeroFlag());

    /**
     * 0x76 - JBE, JNA
     */
    private static final Instruction JBE_JNA = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, (state.getCarryFlag() || state.getZeroFlag()));

    /**
     * 0x77 - JNBE, JA
     */
    private static final Instruction JNBE_JA = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, !(state.getCarryFlag() || state.getZeroFlag()));

    /**
     * 0x78 - JS
     */
    private static final Instruction JS = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, state.getSignFlag());

    /**
     * 0x79 - JNS
     */
    private static final Instruction JNS = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, !state.getSignFlag());

    /**
     * 0x7A - JP, JPE
     */
    private static final Instruction JP_JPE = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, state.getParityFlag());

    /**
     * 0x7B - JNP, JPO
     */
    private static final Instruction JNP_JPO = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, !state.getParityFlag());

    /**
     * 0x7C - JL, JNGE
     */
    private static final Instruction JL_JNGE = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, (state.getSignFlag() != state.getOverflowFlag()));

    /**
     * 0x7D - JNL, JGE
     */
    private static final Instruction JNL_JGE = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, (state.getSignFlag() == state.getOverflowFlag()));

    /**
     * 0x7E - JLE, JNG
     */
    private static final Instruction JLE_JNG = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, state.getZeroFlag() || (state.getSignFlag() != state.getOverflowFlag()));

    /**
     * 0x7F - JNLE, JG
     */
    private static final Instruction JNLE_JG = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, !state.getZeroFlag() && (state.getSignFlag() == state.getOverflowFlag()));
    
    /**
     * 0x9A - CALL far imm16:imm16
     */
    private static final Instruction CALL_FAR_IMM32 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        short newIp = opcodeFetcher.nextWord();
        short newCs = opcodeFetcher.nextWord();
        callFar(state, memory, newCs, newIp);
    };
    
    /**
     * 0xE0 - LOOPNZ, LOOPNE
     */
    private static final Instruction LOOPNZ_LOOPNE = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalLoop(state, opcodeFetcher, !state.getZeroFlag());
    
    /**
     * 0xE1 - LOOPZ, LOOPE
     */
    private static final Instruction LOOPZ_LOOPE = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalLoop(state, opcodeFetcher, state.getZeroFlag());
    
    /**
     * 0xE2 - LOOP
     */
    private static final Instruction LOOP = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.loop(state, opcodeFetcher);
    
    /**
     * 0xE3 - JCXZ
     */
    private static final Instruction JCXZ = (state, memory, opcodeFetcher, registers, addressingDecoder)
            -> FlowUtils.conditionalJump(state, opcodeFetcher, state.getCx() == 0);
    
    /**
     * 0xE8 - CALL near imm16
     */
    private static final Instruction CALL_NEAR_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        short offset = opcodeFetcher.nextWord();
        callNear(state, memory, (short) (state.getIp() + offset));
    };
    
    /**
     * 0xE9 - JMP near imm16
     */
    private static final Instruction JMP_NEAR_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        short offset = opcodeFetcher.nextWord();
        state.setIp((short) (state.getIp() + offset));
    };
    
    /**
     * 0xEA - JMP far imm16:imm16
     */
    private static final Instruction JMP_FAR_IMM16_IMM16 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        short newIp = opcodeFetcher.nextWord();
        short newCs = opcodeFetcher.nextWord();
        state.setIp(newIp);
        state.setCs(newCs);
    };
    
    /**
     * 0xEB - JMP short imm8
     */
    private static final Instruction JMP_SHORT_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        byte offset = opcodeFetcher.nextByte();
        state.setIp((short) (state.getIp() + offset));
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
        
        FLOW_INSTRUCTIONS.add((byte) 0xE0, LOOPNZ_LOOPNE);
        FLOW_INSTRUCTIONS.add((byte) 0xE1, LOOPZ_LOOPE);
        FLOW_INSTRUCTIONS.add((byte) 0xE2, LOOP);
        FLOW_INSTRUCTIONS.add((byte) 0xE3, JCXZ);
        FLOW_INSTRUCTIONS.add((byte) 0xE8, CALL_NEAR_IMM16);
        FLOW_INSTRUCTIONS.add((byte) 0xE9, JMP_NEAR_IMM16);
        FLOW_INSTRUCTIONS.add((byte) 0xEA, JMP_FAR_IMM16_IMM16);
        FLOW_INSTRUCTIONS.add((byte) 0xEB, JMP_SHORT_IMM8);
    }
}
