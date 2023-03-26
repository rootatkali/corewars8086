package il.co.codeguru.corewars8086.cpu;

import il.co.codeguru.corewars8086.memory.MemoryException;
import il.co.codeguru.corewars8086.memory.RealModeAddress;
import il.co.codeguru.corewars8086.memory.RealModeMemory;

/**
 * Decodes indirect-addressing opcodes (translates between the CPU's internal
 * representation of indirect-addressing, to the actual real-mode address).
 * <p>
 * The CPU supports four indirect addressing modes:
 * (0) [BX+SI]         indirect
 * (1) [BX+SI+12h]     indirect + imm8
 * (2) [BX+SI+1234h]   indirect + imm16
 * (3) AX              direct register mode
 * <p>
 * Each indirect-addressing opcode has two operands: a register, and one of the
 * above. e.g:
 * ADD [BX+SI], AX
 *
 * @author DL
 */
public class NgIndirectAddressingDecoder {
    /**
     * CPU registers
     */
    private final NgCpuState state;

    /**
     * Memory
     */
    private final RealModeMemory memory;

    /**
     * Used to fetch additional opcode bytes.
     */
    private final NgOpcodeFetcher opcodeFetcher;

    /**
     * Used to decode the non-memory part of the opcode
     */
    private final NgRegisterIndexingDecoder indexingDecoder;

    private byte registerIndex;
    private byte memoryIndex;
    private RealModeAddress memoryAddress;

    /**
     * Constructor.
     *
     * @param state         CPU registers.
     * @param memory        Memory.
     * @param opcodeFetcher Used to fetch additional opcode bytes.
     */
    public NgIndirectAddressingDecoder(
            NgCpuState state, RealModeMemory memory, NgOpcodeFetcher opcodeFetcher) {

        this.state = state;
        this.memory = memory;
        this.opcodeFetcher = opcodeFetcher;
        indexingDecoder = new NgRegisterIndexingDecoder(this.state);

        registerIndex = 0;
        memoryIndex = 0;
        memoryAddress = null;
    }

    /**
     * Fetches & decodes the bytes currently pointed by the OpcodeFetcher.
     *
     * @throws MemoryException on any error while reading from memory.
     */
    public void reset() throws MemoryException {

        // read the 'mode' byte (MM RRR III)
        // M - indirect addressing mode mux
        // R - register indexing
        // I - indirect addressing indexing 
        byte modeByte = opcodeFetcher.nextByte();

        byte mode = (byte) ((modeByte >> 6) & 0x03);
        registerIndex = (byte) ((modeByte >> 3) & 0x07);
        memoryIndex = (byte) (modeByte & 0x07);

        // decode the opcode according to the indirect-addressing mode, and
        // retrieve the address operand
        switch (mode) {
            case 0:
                memoryAddress = getMode0Address();
                break;
            case 1:
                memoryAddress = getMode1Address();
                break;
            case 2:
                memoryAddress = getMode2Address();
                break;
            case 3:
                memoryAddress = getMode3Address();
                break;
            default:
                throw new RuntimeException();
        }
    }

    /**
     * @return 3 bits representing the internal register indexing.
     */
    public byte getRegisterIndex() {
        return registerIndex;
    }

    /**
     * @return The indirect memory operand's address (or null if the latter
     * refers to a register).
     */
    public RealModeAddress getMemoryAddress() {
        return memoryAddress;
    }

    /**
     * Assuming the opcode operand referred to an 8bit register, returns the
     * corresponding register's value.
     *
     * @return 8bit register value.
     */
    public byte getRegister8() {
        return indexingDecoder.getRegister8(registerIndex);
    }

    /**
     * Returns the 8bit value pointed by the indirect memory operand (or register,
     * depends on the indirect-addressing mode).
     *
     * @return Indirect address (or register) 8bit value.
     */
    public byte getMemory8() throws MemoryException {
        if (memoryAddress != null) {
            return memory.readByte(memoryAddress);
        }
        return indexingDecoder.getRegister8(memoryIndex);
    }

    /**
     * Assuming the opcode operand referred to a 16bit register, returns the
     * corresponding register's value.
     *
     * @return 16bit register value.
     */
    public short getRegister16() {
        return indexingDecoder.getRegister16(registerIndex);
    }

    /**
     * Assuming the opcode operand referred to a segment register, returns the
     * corresponding register's value.
     *
     * @return segment register value.
     */
    public short getSegment() {
        return indexingDecoder.getSegment(registerIndex);
    }

    /**
     * Returns the 16bit value pointed by the indirect memory operand (or register,
     * depands on the indirect-addressing mode).
     *
     * @return Indirect address (or register) 16bit value.
     */
    public short getMemory16() throws MemoryException {
        if (memoryAddress != null) {
            return memory.readWord(memoryAddress);
        }
        return indexingDecoder.getRegister16(memoryIndex);
    }

    /**
     * Assuming the opcode operand referred to an 8-bit register, sets the
     * corresponding register's value.
     *
     * @param value New value for the 8bit register.
     */
    public void setRegister8(byte value) {
        indexingDecoder.setRegister8(registerIndex, value);
    }

    /**
     * Sets the 8bit value pointed by the indirect memory operand (or register,
     * depends on the indirect-addressing mode).
     *
     * @param value Value to set.
     */
    public void setMemory8(byte value) throws MemoryException {
        if (memoryAddress != null) {
            memory.writeByte(memoryAddress, value);
        } else {
            indexingDecoder.setRegister8(memoryIndex, value);
        }
    }

    /**
     * Assuming the opcode operand referred to a 16bit register, sets the
     * corresponding register's value.
     *
     * @param value New value for the segment register.
     */
    public void setRegister16(short value) {
        indexingDecoder.setRegister16(registerIndex, value);
    }

    /**
     * Assuming the opcode operand referred to a segment register, sets the
     * corresponding register's value.
     *
     * @param value New value for the segment register.
     */
    public void setSegment(short value) {
        indexingDecoder.setSegment(registerIndex, value);
    }

    /**
     * Sets the 16bit value pointed by the indirect memory operand (or register,
     * depends on the indirect-addressing mode).
     *
     * @param value Value to set.
     */
    public void setMemory16(short value) throws MemoryException {
        if (memoryAddress != null) {
            memory.writeWord(memoryAddress, value);
        } else {
            indexingDecoder.setRegister16(memoryIndex, value);
        }
    }

    /**
     * Decodes the indirect-memory operand corresponding to mode #0.
     *
     * @return the real-mode address to which the indirect-memory operand
     * refers to.
     * @throws MemoryException on any error while reading from memory.
     */
    private RealModeAddress getMode0Address() throws MemoryException {
        switch (memoryIndex) {
            case 0:
                return new RealModeAddress(
                        state.getDs(), (short) (state.getBx() + state.getSi()));
            case 1:
                return new RealModeAddress(
                        state.getDs(), (short) (state.getBx() + state.getDi()));
            case 2:
                return new RealModeAddress(
                        state.getSs(), (short) (state.getBp() + state.getSi()));
            case 3:
                return new RealModeAddress(
                        state.getSs(), (short) (state.getBp() + state.getDi()));
            case 4:
                return new RealModeAddress(state.getDs(), state.getSi());
            case 5:
                return new RealModeAddress(state.getDs(), state.getDi());
            case 6:
                return new RealModeAddress(state.getDs(), opcodeFetcher.nextWord());
            case 7:
                return new RealModeAddress(state.getDs(), state.getBx());
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Decodes the indirect-memory operand corresponding to mode #1.
     *
     * @return the real-mode address to which the indirect-memory operand
     * refers to.
     * @throws MemoryException on any error while reading from memory.
     */
    private RealModeAddress getMode1Address() throws MemoryException {
        switch (memoryIndex) {
            case 0:
                return new RealModeAddress(state.getDs(),
                        (short) (state.getBx() + state.getSi() + opcodeFetcher.nextByte()));
            case 1:
                return new RealModeAddress(state.getDs(),
                        (short) (state.getBx() + state.getDi() + opcodeFetcher.nextByte()));
            case 2:
                return new RealModeAddress(state.getSs(),
                        (short) (state.getBp() + state.getSi() + opcodeFetcher.nextByte()));
            case 3:
                return new RealModeAddress(state.getSs(),
                        (short) (state.getBp() + state.getDi() + opcodeFetcher.nextByte()));
            case 4:
                return new RealModeAddress(state.getDs(),
                        (short) (state.getSi() + opcodeFetcher.nextByte()));
            case 5:
                return new RealModeAddress(state.getDs(),
                        (short) (state.getDi() + opcodeFetcher.nextByte()));
            case 6:
                return new RealModeAddress(state.getSs(),
                        (short) (state.getBp() + opcodeFetcher.nextByte()));
            case 7:
                return new RealModeAddress(state.getDs(),
                        (short) (state.getBx() + opcodeFetcher.nextByte()));
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Decodes the indirect-memory operand corresponding to mode #2.
     *
     * @return the real-mode address to which the indirect-memory operand
     * refers to.
     * @throws MemoryException on any error while reading from memory.
     */
    private RealModeAddress getMode2Address() throws MemoryException {
        switch (memoryIndex) {
            case 0:
                return new RealModeAddress(state.getDs(),
                        (short) (state.getBx() + state.getSi() + opcodeFetcher.nextWord()));
            case 1:
                return new RealModeAddress(state.getDs(),
                        (short) (state.getBx() + state.getDi() + opcodeFetcher.nextWord()));
            case 2:
                return new RealModeAddress(state.getSs(),
                        (short) (state.getBp() + state.getSi() + opcodeFetcher.nextWord()));
            case 3:
                return new RealModeAddress(state.getSs(),
                        (short) (state.getBp() + state.getDi() + opcodeFetcher.nextWord()));
            case 4:
                return new RealModeAddress(state.getDs(),
                        (short) (state.getSi() + opcodeFetcher.nextWord()));
            case 5:
                return new RealModeAddress(state.getDs(),
                        (short) (state.getDi() + opcodeFetcher.nextWord()));
            case 6:
                return new RealModeAddress(state.getSs(),
                        (short) (state.getBp() + opcodeFetcher.nextWord()));
            case 7:
                return new RealModeAddress(state.getDs(),
                        (short) (state.getBx() + opcodeFetcher.nextWord()));
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Decodes the indirect-memory operand corresponding to mode #3.
     * Since in this mode the indirect-memory operand actually referes to one
     * of the registers, the method simply returns 'null'.
     *
     * @return null (meaning the indirect operand refers to a register).
     */
    private RealModeAddress getMode3Address() {
        return null;
    }
}
