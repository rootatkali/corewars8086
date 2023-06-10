package il.co.codeguru.corewars8086.war;

import il.co.codeguru.corewars8086.cpu.*;
import il.co.codeguru.corewars8086.cpu.instructions.InterruptInstructions;
import il.co.codeguru.corewars8086.memory.MemoryException;
import il.co.codeguru.corewars8086.memory.RealModeAddress;
import il.co.codeguru.corewars8086.memory.RealModeMemory;
import il.co.codeguru.corewars8086.memory.RealModeMemoryRegion;
import il.co.codeguru.corewars8086.memory.RestrictedAccessRealModeMemory;

import static il.co.codeguru.corewars8086.cpu.instructions.AdditionInstructions.ADDITION_INSTRUCTIONS;
import static il.co.codeguru.corewars8086.cpu.instructions.BitwiseInstructions.BITWISE_INSTRUCTIONS;
import static il.co.codeguru.corewars8086.cpu.instructions.ComparisonInstructions.COMPARISON_INSTRUCTIONS;
import static il.co.codeguru.corewars8086.cpu.instructions.FlowInstructions.FLOW_INSTRUCTIONS;
import static il.co.codeguru.corewars8086.cpu.instructions.InterruptInstructions.INTERRUPT_INSTRUCTIONS;
import static il.co.codeguru.corewars8086.cpu.instructions.InvalidInstructions.INVALID_INSTRUCTIONS;
import static il.co.codeguru.corewars8086.cpu.instructions.OmnibusInstructions.OMNIBUS_INSTRUCTIONS;
import static il.co.codeguru.corewars8086.cpu.instructions.SetInstructions.SET_INSTRUCTIONS;
import static il.co.codeguru.corewars8086.cpu.instructions.StackInstructions.STACK_INSTRUCTIONS;
import static il.co.codeguru.corewars8086.cpu.instructions.SubtractionInstructions.SUBTRACTION_INSTRUCTIONS;


/**
 * A single CoreWars warrior.
 * 
 * @author DL
 */
public class Warrior {

    /**
     * Constructor.
     * 
     * @param name	            Warrior's name.
     * @param codeSize          Warrior's code size.
     * @param core              Real mode memory used as core.
     * @param loadAddress       Warrior's load address in the core (initial CS:IP).
     * @param initialStack      Warrior's private stack in the core (initial SS:SP).
     * @param groupSharedMemory Warrior group's shared memroy address (initial ES).
     * @param groupSharedMemorySize Warrior group's shared memory size.
     * @param type              Warrior's player type
     */
    public Warrior(
        String name,
        int codeSize,
        RealModeMemory core,
        RealModeAddress loadAddress,
        RealModeAddress initialStack,
        RealModeAddress groupSharedMemory,
        short groupSharedMemorySize,
        WarriorType type,
        boolean useNgCpu) {

        this.type = type;
        this.useNgCpu = useNgCpu;
        m_name = name;
        m_codeSize = codeSize;
        m_loadAddress = loadAddress;
    
        if (useNgCpu) {
            ngCpuState = new NgCpuState();
        } else {
            m_state = new CpuState();
        }
    
        initializeCpuState(loadAddress, initialStack, groupSharedMemory);

        // initialize read-access regions
        RealModeAddress lowestStackAddress =
            new RealModeAddress(initialStack.getSegment(), (short)0); 
        RealModeAddress lowestCoreAddress =
            new RealModeAddress(loadAddress.getSegment(), (short)0);
        RealModeAddress highestCoreAddress =
            new RealModeAddress(loadAddress.getSegment(), (short)-1);
        RealModeAddress highestGroupSharedMemoryAddress =
            new RealModeAddress(groupSharedMemory.getSegment(),
            (short)(groupSharedMemorySize-1));

        RealModeMemoryRegion[] readAccessRegions =
            new RealModeMemoryRegion[] {
                new RealModeMemoryRegion(lowestStackAddress, initialStack),
                new RealModeMemoryRegion(lowestCoreAddress, highestCoreAddress),
                new RealModeMemoryRegion(groupSharedMemory, highestGroupSharedMemoryAddress)
            };

        // initialize write-access regions
        RealModeMemoryRegion[] writeAccessRegions =
            new RealModeMemoryRegion[] {
                new RealModeMemoryRegion(lowestStackAddress, initialStack),
                new RealModeMemoryRegion(lowestCoreAddress, highestCoreAddress),
                new RealModeMemoryRegion(groupSharedMemory, highestGroupSharedMemoryAddress)
            };

        // initialize execute-access regions
        RealModeMemoryRegion[] executeAccessRegions =
            new RealModeMemoryRegion[] {
                new RealModeMemoryRegion(lowestCoreAddress, highestCoreAddress)
            };

        m_memory = new RestrictedAccessRealModeMemory(
            core, readAccessRegions, writeAccessRegions, executeAccessRegions);

        if (useNgCpu) {
            InstructionResolver resolver = new InstructionResolver(
                    ADDITION_INSTRUCTIONS,
                    BITWISE_INSTRUCTIONS,
                    COMPARISON_INSTRUCTIONS,
                    FLOW_INSTRUCTIONS,
                    INTERRUPT_INSTRUCTIONS,
                    INVALID_INSTRUCTIONS,
                    OMNIBUS_INSTRUCTIONS,
                    SET_INSTRUCTIONS,
                    STACK_INSTRUCTIONS,
                    SUBTRACTION_INSTRUCTIONS
            );
            ngCpu = new NgCpu(ngCpuState, m_memory, resolver);
        }

        m_isAlive = true;
    }

    /**
     * @return whether or not the warrior is still alive.
     */
    public boolean isAlive() {
        return m_isAlive;
    }

    /**
     * Kills the warrior.
     */
    public void kill() {
        m_isAlive = false;
    }

    public boolean isZombie() {
        return type == WarriorType.ZOMBIE || type == WarriorType.ZOMBIE_H;
    }

    public WarriorType getType() {
        return type;
    }

    /**
     * @return the warrior's name.
     */
    public String getName() {
        return m_name;
    }

    /**
     * @return the warrior's load offset.
     */
    public short getLoadOffset() {
        return m_loadAddress.getOffset();
    }	

    /**
     * @return the warrior's initial code size.
     */
    public int getCodeSize() {
        return m_codeSize;
    }

    /**
     * Accessors for the warrior's Energy value (used to calculate
     * the warrior's speed).
     */
    public short getEnergy() {
        return useNgCpu ? ngCpuState.getEnergy() : m_state.getEnergy();
    }
    public void setEnergy(short value) {
        if (useNgCpu) {
            ngCpuState.setEnergy(value);
        } else {
            m_state.setEnergy(value);
        }
    }

    /**
     * Performs the warrior's next turn (= next opcode).
     * @throws CpuException     on any CPU error.
     * @throws MemoryException  on any Memory error.
     */
    public void nextOpcode() throws CpuException, MemoryException {
        if (useNgCpu) {
            ngCpu.runNextInstruction();
        } else {
            m_cpu.nextOpcode();
        }
    }

    /**
     * Initializes the Cpu registers & flags:
     *  CS,DS                    - set to the core's segment.
     *  ES                       - set to the group's shared memory segment.
     *  AX,IP                    - set to the load address.
     *  SS                       - set to the private stack's segment.
     *  SP                       - set to the private stack's offset.
     *  BX,CX,DX,SI,DI,BP, flags - set to zero.
     * 
     * @param loadAddress       Warrior's load address in the core.
     * @param initialStack      Warrior's private stack (initial SS:SP).
     * @param groupSharedMemory The warrior's group shared memory.
     */
    private void initializeCpuState(
        RealModeAddress loadAddress, RealModeAddress initialStack,
        RealModeAddress groupSharedMemory) {
        if (useNgCpu) {
            initializeNgCpuState(loadAddress, initialStack, groupSharedMemory);
        } else {
            initializeLegacyCpuState(loadAddress, initialStack, groupSharedMemory);
        }
    }
    
    private void initializeLegacyCpuState(RealModeAddress loadAddress, RealModeAddress initialStack,
                                          RealModeAddress groupSharedMemory) {
        // initialize registers
        m_state.setAX(loadAddress.getOffset());
        m_state.setBX((short)0);
        m_state.setCX((short)0);
        m_state.setDX((short)0);
        
        m_state.setDS(loadAddress.getSegment());
        m_state.setES(groupSharedMemory.getSegment());
        m_state.setSI((short)0);
        m_state.setDI((short)0);
        
        m_state.setSS(initialStack.getSegment());
        m_state.setBP((short)0);
        m_state.setSP(initialStack.getOffset());
        
        m_state.setCS(loadAddress.getSegment());
        m_state.setIP(loadAddress.getOffset());
        m_state.setFlags((short)0);
        
        // initialize Energy
        m_state.setEnergy((short)0);
        
        // initialize bombs
        m_state.setBomb1Count((byte)2);
        m_state.setBomb2Count((byte)1);
    }
    
    private void initializeNgCpuState(RealModeAddress loadAddress, RealModeAddress initialStack,
                                          RealModeAddress groupSharedMemory) {
        // initialize registers
        ngCpuState.setAx(loadAddress.getOffset());
        ngCpuState.setBx((short) 0);
        ngCpuState.setCx((short) 0);
        ngCpuState.setDx((short) 0);
        
        ngCpuState.setDs(loadAddress.getSegment());
        ngCpuState.setEs(groupSharedMemory.getSegment());
        ngCpuState.setSi((short) 0);
        ngCpuState.setDi((short) 0);
        
        ngCpuState.setSs(initialStack.getSegment());
        ngCpuState.setBp((short) 0);
        ngCpuState.setSp(initialStack.getOffset());
        
        ngCpuState.setCs(loadAddress.getSegment());
        ngCpuState.setIp(loadAddress.getOffset());
        ngCpuState.setFlags((short) 0);
        
        // initialize Energy
        ngCpuState.setEnergy((short) 0);
        
        // initialize bombs
        ngCpuState.setBruteBombCount((byte) 2);
        ngCpuState.setSmartBombCount((byte) 1);
    }
    
    public CpuState getCpuState() {
    	return m_state;
    }

    /** Warrior's name */
    private final String m_name;	
    /** Warrior's initial code size */	
    private final int m_codeSize;
    /** Warrior's initial load address */	
    private final RealModeAddress m_loadAddress;
    /** Current state of registers & flags */	
    private CpuState m_state;
    /** Applies restricted access logic on top of the actual core memory */
    private RestrictedAccessRealModeMemory m_memory;
    /** CPU instance */
    private Cpu m_cpu;
    
    private NgCpuState ngCpuState;
    private NgCpu ngCpu;
    
    /** Whether the warrior is still alive */
    private boolean m_isAlive;

    private final WarriorType type;
    
    private final boolean useNgCpu;
}