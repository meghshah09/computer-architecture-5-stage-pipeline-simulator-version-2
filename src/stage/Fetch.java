package stage;

import general.*;
public class Fetch extends StageCommunication{
	
	private boolean endOfProgram=false;
	private boolean isBranched=false;

	public Fetch(ProcessingUnitInner puInner) {
		super(puInner);
	}

	@Override
	public void execute(int currentExecutionCycle) {
		
		this.currentExecutionCycle= currentExecutionCycle;
		
		if( isFetchInstructionStalled()) {
			System.out.println("fetch Stage:"+ "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
			return ;
		}
		else if (endOfProgram && !isBranched) {
			System.out.println("Fetch Stage:");
			return;
		}
		int programCounter= puInner.getProgramCounter();
		
		if( programCounter ==-1 || puInner.getInstructionMap().get(programCounter)==null) {
			endOfProgram = true;
			this.setInstruction(null);
			System.out.println("fetch Stage:");
			programCounter+=4;
			puInner.setProgramCounter(programCounter);
			return;
			
		}
		else {
			endOfProgram=false;
			isBranched=false;
		}
		setInstruction(puInner.getInstructionMap().get(programCounter));
		programCounter+=4;
		puInner.setProgramCounter(programCounter);
		System.out.println("fetch Stage:"+ "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
	}

	public boolean isEndOfProgram() {
		return endOfProgram;
	}

	public void setEndOfProgram(boolean endOfProgram) {
		this.endOfProgram = endOfProgram;
	}
	
	public boolean isFetchInstructionStalled() {
		if(this.getInstruction()== null) {
			return false;
		}
		Instruction instructionInDecodeStage = getProcessingUnitInner().getDecode().getInstruction();
		if(instructionInDecodeStage != this.getInstruction()) {
			return true;
		}
		return false;
		
	}

	public boolean isBranched() {
		return isBranched;
	}

	public void setBranched(boolean isBranched) {
		this.isBranched = isBranched;
	}
	
}
