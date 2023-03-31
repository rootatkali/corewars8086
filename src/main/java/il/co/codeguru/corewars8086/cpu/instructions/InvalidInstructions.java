package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.InstructionResolver;
import il.co.codeguru.corewars8086.cpu.InvalidOpcodeException;
import il.co.codeguru.corewars8086.cpu.UnimplementedOpcodeException;

public class InvalidInstructions {
    public static final InstructionResolver INVALID_INSTRUCTIONS = new InstructionResolver();

    /**
     * Contains all officially invalid opcodes
     */
    private static final int[] INVALID_OPCODES = { 0x0F };

    /**
     * Contains all opcodes that are valid but were not implemented in the CoreWars8086 CPU.
     * <p>Implementations for these opcodes from the community are welcome! <3
     */
    private static final int[] UNIMPLEMENTED_OPCODES = { 0x26, 0x27, 0x2E, 0x2F };

    static {
        for (int opcode : INVALID_OPCODES) {
            INVALID_INSTRUCTIONS.add((byte) opcode, (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
                throw new InvalidOpcodeException(opcode);
            });
        }

        for (int opcode : UNIMPLEMENTED_OPCODES) {
            INVALID_INSTRUCTIONS.add((byte) opcode, (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
                throw new UnimplementedOpcodeException(opcode);
            });
        }
    }
}
