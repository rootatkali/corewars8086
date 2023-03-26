package il.co.codeguru.corewars8086.cpu;

import java.util.Hashtable;

public class InstructionResolver {
    private final Hashtable<Byte, Instruction> instructions;

    public InstructionResolver() {
        this(new InstructionResolver[0]);
    }

    private InstructionResolver(InstructionResolver... resolvers) {
        this.instructions = new Hashtable<>();

        for (InstructionResolver resolver : resolvers) {
            instructions.putAll(resolver.instructions);
        }
    }

    public Instruction resolve(byte opcode) throws CpuException {
        if (!instructions.containsKey(opcode)) throw new UnsupportedOpcodeException(opcode);

        return instructions.get(opcode);
    }

    public void add(byte opcode, Instruction instruction) {
        instructions.put(opcode, instruction);
    }
}
