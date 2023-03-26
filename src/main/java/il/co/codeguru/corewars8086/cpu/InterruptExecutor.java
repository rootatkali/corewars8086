package il.co.codeguru.corewars8086.cpu;

import il.co.codeguru.corewars8086.memory.MemoryException;
import il.co.codeguru.corewars8086.memory.RealModeAddress;
import il.co.codeguru.corewars8086.memory.RealModeMemory;

public class InterruptExecutor {
    private static final InstructionResolver IVT = new InstructionResolver();

    private static void stosdw(NgCpuState state, RealModeMemory memory) throws MemoryException {
        RealModeAddress address1 = new RealModeAddress(state.getEs(), state.getDi());
        memory.writeWord(address1, state.getAx());

        RealModeAddress address2 = new RealModeAddress(state.getEs(), (short) (state.getDi() + 2));
        memory.writeWord(address2, state.getDx());

        byte diff = state.getDirectionFlag() ? (byte) -4 : (byte) 4;
        state.setDi((short) (state.getDi() + diff));
    }

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

    };

    public static final Instruction INTERRUPT = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        byte interruptIndex = opcodeFetcher.nextByte();
        Instruction interrupt = resolveInterrupt(interruptIndex);

        interrupt.execute(state, memory, opcodeFetcher, registers, addressingDecoder);
    };

    static {
        IVT.add((byte) 0x86, INT86);
        IVT.add((byte) 0x87, INT87);
    }

    public static Instruction resolveInterrupt(byte index) throws IntOpcodeException {
        try {
            return IVT.resolve(index);
        } catch (CpuException e) {
            throw new IntOpcodeException(String.format("Interrupt %x not supported", index));
        }
    }

    public static final InstructionResolver INTERRUPT_INSTRUCTIONS = new InstructionResolver();

    static {
        INTERRUPT_INSTRUCTIONS.add((byte) 0xCD, INTERRUPT);
    }
}
