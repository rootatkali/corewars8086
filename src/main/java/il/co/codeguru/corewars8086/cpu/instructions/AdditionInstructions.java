package il.co.codeguru.corewars8086.cpu.instructions;

import il.co.codeguru.corewars8086.cpu.Instruction;
import il.co.codeguru.corewars8086.cpu.InstructionResolver;

import static il.co.codeguru.corewars8086.cpu.instructions.Utils.add8;

public class AdditionInstructions {
    public static final InstructionResolver ADDITION_INSTRUCTIONS = new InstructionResolver();

    private static final Instruction ADD_MEM_REG_8 = (state, memory, opcodeFetcher, registers, addressingDecoder) -> {
        addressingDecoder.reset();
        addressingDecoder.setMemory8(add8(state, addressingDecoder.getMemory8(), addressingDecoder.getRegister8()));
    };

    static {

    }
}
