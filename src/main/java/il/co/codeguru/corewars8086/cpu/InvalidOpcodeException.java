package il.co.codeguru.corewars8086.cpu;

/**
 * Thrown when attempting to execute an invalid opcode.
 * 
 * @author DL
 */
public class InvalidOpcodeException extends CpuException {
	private static final long serialVersionUID = 1L;

	public InvalidOpcodeException() {
		super();
	}

	public InvalidOpcodeException(String message) {
		super(message);
	}

	public InvalidOpcodeException(int opcode) {
		this(String.format("Invalid opcode %x", opcode));
	}
}
