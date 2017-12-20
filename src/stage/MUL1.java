/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stage;

import general.*;

import java.util.*;

/**
 *
 * @author skdc
 */
public class MUL1 extends StageCommunication{

    public MUL1(ProcessingUnitInner puInner) {
        super(puInner);
    }

    private Map<Instruction,Integer> multiplicationALUBufferMap = new HashMap<Instruction,Integer>(); 

	public Map<Instruction, Integer> getMultiplicationALUBufferMap() {
		return multiplicationALUBufferMap;
	}
    
    @Override
    public void execute(int currentExecutionCycle) {
        
        this.currentExecutionCycle=currentExecutionCycle;
        if(getProcessingUnitInner().getDecode().getInstruction() != null && getProcessingUnitInner().getDecode().getInstruction().getInstruction().equalsIgnoreCase("DIV")){
            this.setInstruction(null);
        }
        
        if(getProcessingUnitInner().getDecode().isOutputDependency(this.currentExecutionCycle)){
            this.setInstruction(null);
        }
        else if(getProcessingUnitInner().getDecode().isInstructionStalled(this.currentExecutionCycle)) {
				
            if(getProcessingUnitInner().getDecode().isInstructionForwarded(this.currentExecutionCycle) && puInner.getDecode().getInstruction().getInstruction().equalsIgnoreCase("MUL")){
                this.setInstruction(puInner.getDecode().getInstruction());
            }else{
                this.setInstruction(null);
            }
	}
	else if (puInner.getDecode().getInstruction()!=null && puInner.getDecode().getInstruction().getInstruction().equalsIgnoreCase("MUL"))
	{	
            this.setInstruction(puInner.getDecode().getInstruction()); // gets the new MUL Instruction
              
        } 
	else
	{
            this.setInstruction(null);	
	}
        
        if (this.getInstruction()!= null)
	{
            System.out.println("MUL1 Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
             int operand1,operand2 =0;
            int operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                int operand2Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource2());
                /* forwarding data + normal execution for soucre 1 */
                if (getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                    operand1 = getProcessingUnitInner().getVirtualRegisters()[operand1Index].getValue();
                   // getProcessingUnitInner().getRegisters()[operand1Index].setForwarded(false);
                }else{
                    operand1 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource1());
                }
                /* forwarding data + normal execution for soucre 2 */
                if (getProcessingUnitInner().getRegisters()[operand2Index].isForwarded()){
                    operand2 = getProcessingUnitInner().getVirtualRegisters()[operand2Index].getValue();
                   // getProcessingUnitInner().getRegisters()[operand2Index].setForwarded(false);
                }else{
                    operand2 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource2());
                }

		int result= operand1 * operand2 ;
		
		multiplicationALUBufferMap.put(this.getInstruction(), result);
		int destionationRegisterPosition = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
		getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setValid(false);
		getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
		getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
	
                getProcessingUnitInner().getVirtualRegisters()[destionationRegisterPosition].setValue(result);
	}
	else {
            
            System.out.println("MUL1 Stage:");
	}
        
    }

    
 public void releaseForwarding(){
     
     if(this.getInstruction() != null){
         
         if(this.getInstruction() == getProcessingUnitInner().getForwardFlagTo()){
             int operand1,operand2 =0;
            int operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                int operand2Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource2());
                if(getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                    getProcessingUnitInner().getRegisters()[operand1Index].setForwarded(false);
                    getProcessingUnitInner().setForwardFlagTo(null);
                }
                else{
                    getProcessingUnitInner().getRegisters()[operand2Index].setForwarded(false);
                    getProcessingUnitInner().setForwardFlagTo(null);
                }
             
         }
     }
 }   
}
