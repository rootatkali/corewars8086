package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.*;
import il.co.codeguru.corewars8086.memory.MemoryException;
import il.co.codeguru.corewars8086.memory.RealModeAddress;
import il.co.codeguru.corewars8086.memory.RealModeMemory;

public class InterruptInstructions {
    private static final InstructionResolver IVT = new InstructionResolver();
    public static final InstructionResolver INTERRUPT_INSTRUCTIONS = new InstructionResolver();

    /**
     * 0xCC - INT 3
     */
    private static final Instruction INT3 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        throw new IntOpcodeException("Interrupt 3 not supported");
    };


    // --== CUSTOM INTERRUPTS GO HERE ==--
    private static final Instruction INT86 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        byte bombCount = state.getBruteBombCount();

        if (bombCount > 0) {
            state.setBruteBombCount((byte) (bombCount - 1));

            for (int i = 0; i < 64; i++) {
                stosdw(state, memory);
            }
        }
    };
    private static final Instruction INT87 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        byte bombCount = state.getSmartBombCount();
        if (bombCount != 0) {
            state.setSmartBombCount((byte) (bombCount - 1));

            for (int i = 0; i <= 0xFFFF; i++) {
                int diff = state.getDirectionFlag() ? -i : i;

                RealModeAddress address1 = new RealModeAddress(state.getEs(), (short) (state.getDi() + diff));

                if (memory.readWord(address1) == state.getAx()) {
                    RealModeAddress address2 = new RealModeAddress(state.getEs(), (short) (state.getDi() + diff + 2));

                    if (memory.readWord(address2) == state.getDx()) {
                        memory.writeWord(address1, state.getBx());
                        memory.writeWord(address2, state.getCx());

                        break;
                    }
                }
            }
        }
    };
    // --== END OF CUSTOM INTERRUPTS ==--

    static {
        IVT.add((byte) 0x86, INT86);
        IVT.add((byte) 0x87, INT87);
    }

    /**
     * 0xCD - INT imm8
     */
    private static final Instruction INT_IMM8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        byte interruptIndex = opcodeFetcher.nextByte();
        Instruction interrupt = resolveInterrupt(interruptIndex);

        interrupt.execute(state, memory, opcodeFetcher, registers, addressingDecoder);
    };

    /**
     * 0xCE - INT 0
     */
    private static final Instruction INT0 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        throw new IntOpcodeException("Interrupt 0 not supported");
    };

    private static Instruction resolveInterrupt(byte index) throws IntOpcodeException {
        try {
            return IVT.resolve(index);
        } catch (CpuException e) {
            throw new IntOpcodeException(String.format("Interrupt %x not supported", index));
        }
    }

    static {
        INTERRUPT_INSTRUCTIONS.add((byte) 0xCC, INT3);
        INTERRUPT_INSTRUCTIONS.add((byte) 0xCD, INT_IMM8);
        INTERRUPT_INSTRUCTIONS.add((byte) 0xCE, INT0);
    }

    private static void stosdw(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address1 = new RealModeAddress(state.getEs(), state.getDi());
        memory.writeWord(address1, state.getAx());

        RealModeAddress address2 = new RealModeAddress(state.getEs(), (short) (state.getDi() + 2));
        memory.writeWord(address2, state.getDx());

        byte diff = state.getDirectionFlag() ? (byte) -4 : (byte) 4;
        state.setDi((short) (state.getDi() + diff));
    }
}
