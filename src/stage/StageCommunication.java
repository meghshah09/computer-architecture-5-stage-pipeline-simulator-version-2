/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stage;

/**
 *
 * @author skdc
 */
import general.*;

public abstract class StageCommunication {
    protected Instruction instruction;
    protected ProcessingUnitInner puInner;
    
    protected int currentExecutionCycle;
    
    
     public StageCommunication(ProcessingUnitInner puInner) {
		this.setProcessingUnitInner(puInner);
	}
    
    public ProcessingUnitInner getProcessingUnitInner() {
		return puInner;
	}

    public void setProcessingUnitInner(ProcessingUnitInner puInner) {
		this.puInner = puInner;
	}
        
    public int getCurrentExecutionCycle() {
		return currentExecutionCycle;
	}

    public void setCurrentExecutionCycle(int currentCycle) {
		this.currentExecutionCycle = currentCycle;
	}
        
    public abstract void execute(int currentCycle);
    

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}
}
