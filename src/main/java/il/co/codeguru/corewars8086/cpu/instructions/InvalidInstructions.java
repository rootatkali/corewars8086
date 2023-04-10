package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.InstructionResolver;
import il.co.codeguru.corewars8086.cpu.InvalidOpcodeException;
import il.co.codeguru.corewars8086.cpu.UnimplementedOpcodeException;

public class InvalidInstructions {
    public static final InstructionResolver INVALID_INSTRUCTIONS = new InstructionResolver();

    /**
     * Contains all officially invalid opcodes
     */
    private static final int[] INVALID_OPCODES = {
            0x0F,
            0x60, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
            0xC0, 0xC1, 0xC8, 0xC9
    };

    /**
     * Contains all opcodes that are valid but were not implemented in the CoreWars8086 CPU.
     * <p>Implementations for these opcodes from the community are welcome! <3
     */
    private static final int[] UNIMPLEMENTED_OPCODES = {
            0x26, 0x27, 0x2E, 0x2F,
            0x36, 0x37, 0x3E, 0x3F
    };

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
