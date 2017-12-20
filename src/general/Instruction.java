package general;

public class Instruction {

	String instruction;
	String source1;
	String source2;
	String destination;
	String instructionString;  
        int pcAddress;
        int instCount;
        
	public Instruction(String instr)
	{	
		instructionString=instr;
	}
        public int getPcAddress(){
            return pcAddress;
        }
        public void setPcAddress(int pcAddr){
            this.pcAddress=pcAddr;
        }
	public String getInstruction() {
		return instruction;
	}
        public int getInstructionCount() {
		return instCount;
	}
        public void setInstructionCount(int no) {
		this.instCount = no;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getSource1() {
		return source1;
	}

	public void setSource1(String source1) {
		this.source1 = source1;
	}

	public String getSource2() {
		return source2;
	}

	public void setSource2(String source2) {
		this.source2 = source2;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getInstructionString() {
		return instructionString;
	}

	public void setInstructionString(String instructionString) {
		this.instructionString = instructionString;
	}
	
	
}
