package il.co.codeguru.corewars8086.cpu;

/**
 * Thrown when attempting to execute an unimplemented opcode.
 * 
 * @author DL
 */
public class UnimplementedOpcodeException extends CpuException {
	private static final long serialVersionUID = 1L;

	public UnimplementedOpcodeException() {
		super();
	}

	public UnimplementedOpcodeException(int opcode) {
		super(String.format("Opcode %x is not implemented", opcode));
	}
}
