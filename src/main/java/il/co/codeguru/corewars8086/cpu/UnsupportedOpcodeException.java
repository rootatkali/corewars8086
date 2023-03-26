package il.co.codeguru.corewars8086.cpu;

/**
 * Thrown when attempting to execute an unsupported opcode.
 * 
 * @author DL
 */
public class UnsupportedOpcodeException extends CpuException {
	private static final long serialVersionUID = 1L;

	public UnsupportedOpcodeException() {
		super();
	}

	public UnsupportedOpcodeException(String message) {
		super(message);
	}

	public UnsupportedOpcodeException(int opcode) {
		this(String.format("Opcode %x is not supported", opcode));
	}
}
