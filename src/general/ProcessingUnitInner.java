/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

/**
 *
 * @author skdc
 */
import java.io.*;
import java.util.*;

import stage.*;

public class ProcessingUnitInner {

    private Register[] register = new Register[16];
    private int programCounter=4000;
    private Fetch fetch;
    private Decode decode;
    private IntegerFu int1;
    private MUL1 mul1;
    private MUL2 mul2;
    private DIV1 div1;
    private DIV2 div2;
    private DIV3 div3;
    private DIV4 div4;
    private Memory memory;
    private WriteBack writeback;
    // Map with key as the memory location and value as the Instruction.
    private Map<Integer,Instruction> instructionMap = new HashMap<Integer,Instruction>();
    private Map <Integer, Integer> memoryMap = new HashMap<Integer,Integer>();
    private boolean zeroFlag= false ;
    private Instruction zeroFlagSetBy= null;
    private VirtualRegister[] virtualregister = new VirtualRegister[16];
    private boolean forwardFlag=false;
    private Instruction forwardFlagSetTo= null;
    private boolean haltFlag =false;
    
    public void init(String s1) throws Exception{
        int i=0;
        while(i<16){
            register[i]= new Register("R"+i);
            virtualregister[i] = new VirtualRegister("R"+i);
            i++;
        }
        
                fetch  = new Fetch(this);
		decode =new Decode(this);
		int1 =new IntegerFu(this);
       		mul1 = new MUL1(this);
		mul2 = new MUL2(this);
                div1 = new DIV1(this);
                div2 = new DIV2(this);
                div3 = new DIV3(this);
                div4 = new DIV4(this);
                memory = new Memory(this);
		writeback =new WriteBack(this);
		                
                int pcValue=4000;
		File file = new File(s1);
		Scanner scan = new Scanner(file);
		String str;
                int j =0;
		while (scan.hasNextLine())
		{
			str=scan.nextLine();
			Instruction instruction = new Instruction(str);
                        instruction.setPcAddress(pcValue);
                        instruction.setInstructionCount(j);
			instructionMap.put(pcValue,instruction);
			pcValue+=4;
                        j++;
		}

        
    }

        
        public Map<Integer, Instruction> getInstructionMap() {
		return instructionMap;
	}


	public void setInstructionMap(Map<Integer, Instruction> instructionMap) {
		this.instructionMap = instructionMap;
	}


	public Register[] getRegisters() {
		return register;
	}

	public void setRegisters(Register[] registers) {
		this.register = register;
	}
        
        public VirtualRegister[] getVirtualRegisters() {
		return virtualregister;
	}

	public void setVirtualRegisters(VirtualRegister[] virtualregister) {
		this.virtualregister = virtualregister;
	}
/* Getter and Setter For Stages */
	public Fetch getFetch() {
		return fetch;
	}
	public Decode getDecode() {
		return decode;
	}
	public IntegerFu getIntegerFu() {
		return int1;
	}
	public Memory getMemory() {
		return memory;
	}
	public WriteBack getWriteBack() {
		return writeback;
	}
        public MUL1 getMUL1() {
		return mul1;
	}
        public MUL2 getMUL2() {
		return mul2;
	}
        public DIV1 getDIV1() {
		return div1;
	}
        public DIV2 getDIV2() {
		return div2;
	}
        public DIV3 getDIV3() {
		return div3;
	}
        public DIV4 getDIV4() {
		return div4;
	}
/* getter and setter for counting pc address by manually  */
        public int getProgramCounter() {
		return programCounter;
	}
	public void setProgramCounter(int programCounter) {
		this.programCounter = programCounter;
	}
/* Getter and Setter for zero flag and getting info about it*/
	public boolean isZeroFlag() {
		return zeroFlag;
	}
        public boolean getHaltFlag(){
            return haltFlag;
        }
        public void setHaltFlag(boolean value){
            this.haltFlag = value;
        }
	public void setZeroFlag(boolean zeroFlag) {
		this.zeroFlag = zeroFlag;
	}


	public Instruction getZeroFlagSetBy() {
		return zeroFlagSetBy;
	}


	public void setZeroFlagSetBy(Instruction zeroFlagSetBy) {
		this.zeroFlagSetBy = zeroFlagSetBy;
	}
        /* Getter and Setter for zero flag and getting info about it*/
        public boolean isForwardFlag() {
		return forwardFlag;
	}


	public void setForwardFlag(boolean fwdFlag) {
		this.forwardFlag = fwdFlag;
	}
        public Instruction getForwardFlagTo() {
		return forwardFlagSetTo;
	}


	public void setForwardFlagTo(Instruction fwdFlagSetBy) {
		this.forwardFlagSetTo = fwdFlagSetBy;
	}
/*Memory map maps memory address to its value eg: mem[4044]=40 */
	public Map <Integer, Integer> getMemoryMap() {
		return memoryMap;
	}
 
        
       
        
}
