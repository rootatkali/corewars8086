package il.co.codeguru.corewars8086.cpu;

/**
 * Decodes the CPU's internal register indexing to the actual registers.
 * 
 * @author DL
 */
public class NgRegisterIndexingDecoder {
    private final NgCpuState state;

    NgRegisterIndexingDecoder(NgCpuState state) {
        this.state = state;
    }

    /**
     * Returns the value of the 8bit register whose index is given.
     * @param index   CPU's internal register index.
     * @return the value of the 8bit register whose index is given.
     */
    public byte getRegister8(byte index) {
        switch (index) {
            case 0:
                return state.getAl();
            case 1:
                return state.getCl();
            case 2:
                return state.getDl();
            case 3:
                return state.getBl();
            case 4:
                return state.getAh();
            case 5:
                return state.getCh();
            case 6:
                return state.getDh();
            case 7:
                return state.getBh();
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Sets the value of the 8bit register whose index is given.
     * @param index   CPU's internal register index.
     * @param value   New value for above register.
     */
    public void setRegister8(byte index, byte value) {
        switch (index) {
            case 0:
                state.setAl(value);
                break;
            case 1:
                state.setCl(value);
                break;
            case 2:
                state.setDl(value);
                break;
            case 3:
                state.setBl(value);
                break;
            case 4:
                state.setAh(value);
                break;
            case 5:
                state.setCh(value);
                break;
            case 6:
                state.setDh(value);
                break;
            case 7:
                state.setBh(value);
                break;
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Returns the value of the 16bit register whose index is given.
     * @param index   CPU's internal register index.
     * @return the value of the 16bit register whose index is given.
     */
    public short getRegister16(byte index) {
        switch (index) {
            case 0:
                return state.getAx();
            case 1:
                return state.getCx();
            case 2:
                return state.getDx();
            case 3:
                return state.getBx();
            case 4:
                return state.getSp();
            case 5:
                return state.getBp();
            case 6:
                return state.getSi();
            case 7:
                return state.getDi();
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Sets the value of the 16bit register whose index is given.
     * @param index   CPU's internal register index.
     * @param value   New value for above register.
     */
    public void setRegister16(byte index, short value) {
        switch (index) {
            case 0:
                state.setAx(value);
                break;
            case 1:
                state.setCx(value);
                break;
            case 2:
                state.setDx(value);
                break;
            case 3:
                state.setBx(value);
                break;
            case 4:
                state.setSp(value);
                break;
            case 5:
                state.setBp(value);
                break;
            case 6:
                state.setSi(value);
                break;
            case 7:
                state.setDi(value);
                break;
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Returns the value of the segment register whose index is given.
     * @param index   CPU's internal register index.
     * @return the value of the segment register whose index is given.
     */
    public short getSegment(byte index) {
        if (index > 7) throw new RuntimeException();

        switch (index % 4) {
            case 0:
                return state.getEs();
            case 1:
                return state.getCs();
            case 2:
                return state.getSs();
            case 3:
                return state.getDs();
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Sets the value of the segment register whose index is given.
     * @param index   CPU's internal register index.
     * @param value   New value for above register.
     */
    public void setSegment(byte index, short value) {
        if (index > 7) throw new RuntimeException();

        switch (index % 4) {
            case 0:
                state.setEs(value);
                break;
            case 1:
                state.setCs(value);
                break;
            case 2:
                state.setSs(value);
                break;
            case 3:
                state.setDs(value);
                break;
            default:
                throw new RuntimeException();
        }
    }
}
