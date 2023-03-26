package il.co.codeguru.corewars8086.cpu;

/**
 * Thrown on various interrupt opcodes.
 * 
 * @author DL
 */
public class IntOpcodeException extends CpuException {
	private static final long serialVersionUID = 1L;

	public IntOpcodeException() {

	}

	public IntOpcodeException(String message) {
		super(message);
	}
}
