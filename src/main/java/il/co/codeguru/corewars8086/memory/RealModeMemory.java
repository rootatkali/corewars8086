package il.co.codeguru.corewars8086.memory;

/**
 * Interface for 16bit Real-Mode memory.
 * 
 * @author DL
 */
public interface RealModeMemory {

    /**
     * Reads a single byte from the specified address.
     *
     * @param address    Real-mode address to read from.
     * @return the read byte.
     * 
     * @throws MemoryException  on any error. 
     */
    byte readByte(RealModeAddress address) throws MemoryException;

    /**
     * Reads a single word from the specified address.
     *
     * @param address    Real-mode address to read from.
     * @return the read word.
     * 
     * @throws MemoryException  on any error. 
     */
    short readWord(RealModeAddress address) throws MemoryException;

    /**
     * Writes a single byte to the specified address.
     *
     * @param address    Real-mode address to write to.
     * @param value      Data to write.
     * 
     * @throws MemoryException  on any error. 
     */
    void writeByte(RealModeAddress address, byte value)
        throws MemoryException;

    /**
     * Writes a single word to the specified address.
     *
     * @param address    Real-mode address to write to.
     * @param value      Data to write.
     * 
     * @throws MemoryException  on any error. 
     */
    void writeWord(RealModeAddress address, short value)
        throws MemoryException;

    /**
     * Reads a single byte from the specified address, in order to execute it.
     *
     * @param address    Real-mode address to read from.
     * @return the read byte.
     * 
     * @throws MemoryException  on any error. 
     */
    byte readExecuteByte(RealModeAddress address)
        throws MemoryException;

    /**
     * Reads a single word from the specified address, in order to execute it.
     *
     * @param address    Real-mode address to read from.
     * @return the read word.
     * 
     * @throws MemoryException  on any error. 
     */
    short readExecuteWord(RealModeAddress address)
        throws MemoryException;	
}
