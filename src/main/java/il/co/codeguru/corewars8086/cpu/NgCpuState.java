package il.co.codeguru.corewars8086.cpu;

import il.co.codeguru.corewars8086.utils.Unsigned;

public class NgCpuState {
    private static final int BITS_PER_BYTE = 8;
    private static final int HIGHER_BYTE_MASK = 0xFF00;
    private static final int LOWER_BYTE_MASK = 0x00FF;
    
    private short ax;
    private short bx;
    private short cx;
    private short dx;
    
    private short sp;
    private short bp;
    private short ip;
    
    private short ss;
    private short cs;
    private short ds;
    private short es;
    private short si;
    private short di;
    
    private short flags;
    
    private short energy;
    private byte bruteBombCount; // TODO correct naming
    private byte smartBombCount;
    
    public NgCpuState() {
        
    }

    public short getAx() {
        return ax;
    }

    public void setAx(short ax) {
        this.ax = ax;
    }

    public byte getAl() {
        return (byte) ax;
    }

    public void setAl(byte al) {
        ax &= HIGHER_BYTE_MASK;
        ax |= Unsigned.unsignedByte(al);
    }

    public byte getAh() {
        return (byte) (ax >> BITS_PER_BYTE);
    }

    public void setAh(byte ah) {
        ax &= LOWER_BYTE_MASK;
        ax |= (Unsigned.unsignedByte(ah) << BITS_PER_BYTE);
    }

    public short getBx() {
        return bx;
    }

    public void setBx(short bx) {
        this.bx = bx;
    }

    public byte getBl() {
        return (byte) bx;
    }

    public void setBl(byte bl) {
        bx &= HIGHER_BYTE_MASK;
        bx |= Unsigned.unsignedByte(bl);
    }

    public byte getBh() {
        return (byte) (bx >> BITS_PER_BYTE);
    }

    public void setBh(byte bh) {
        bx &= LOWER_BYTE_MASK;
        bx |= (Unsigned.unsignedByte(bh) << BITS_PER_BYTE);
    }

    public short getCx() {
        return cx;
    }

    public void setCx(short cx) {
        this.cx = cx;
    }

    public byte getCl() {
        return (byte) cx;
    }

    public void setCl(byte cl) {
        cx &= HIGHER_BYTE_MASK;
        cx |= Unsigned.unsignedByte(cl);
    }

    public byte getCh() {
        return (byte) (cx >> BITS_PER_BYTE);
    }

    public void setCh(byte ch) {
        cx &= LOWER_BYTE_MASK;
        cx |= (Unsigned.unsignedByte(ch) << BITS_PER_BYTE);
    }

    public short getDx() {
        return dx;
    }

    public void setDx(short dx) {
        this.dx = dx;
    }

    public byte getDl() {
        return (byte) dx;
    }

    public void setDl(byte dl) {
        dx &= HIGHER_BYTE_MASK;
        dx |= Unsigned.unsignedByte(dl);
    }

    public byte getDh() {
        return (byte) (dx >> BITS_PER_BYTE);
    }

    public void setDh(byte dh) {
        dx &= LOWER_BYTE_MASK;
        dx |= (Unsigned.unsignedByte(dh) << BITS_PER_BYTE);
    }

    public short getSp() {
        return sp;
    }

    public void setSp(short sp) {
        this.sp = sp;
    }

    public short getBp() {
        return bp;
    }

    public void setBp(short bp) {
        this.bp = bp;
    }

    public short getIp() {
        return ip;
    }

    public void setIp(short ip) {
        this.ip = ip;
    }

    public short getSs() {
        return ss;
    }

    public void setSs(short ss) {
        this.ss = ss;
    }

    public short getCs() {
        return cs;
    }

    public void setCs(short cs) {
        this.cs = cs;
    }

    public short getDs() {
        return ds;
    }

    public void setDs(short ds) {
        this.ds = ds;
    }

    public short getEs() {
        return es;
    }

    public void setEs(short es) {
        this.es = es;
    }

    public short getSi() {
        return si;
    }

    public void setSi(short si) {
        this.si = si;
    }

    public short getDi() {
        return di;
    }

    public void setDi(short di) {
        this.di = di;
    }

    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    public short getEnergy() {
        return energy;
    }

    public void setEnergy(short energy) {
        this.energy = energy;
    }

    public byte getBruteBombCount() {
        return bruteBombCount;
    }

    public void setBruteBombCount(byte bruteBombCount) {
        this.bruteBombCount = bruteBombCount;
    }

    public byte getSmartBombCount() {
        return smartBombCount;
    }

    public void setSmartBombCount(byte smartBombCount) {
        this.smartBombCount = smartBombCount;
    }
}
