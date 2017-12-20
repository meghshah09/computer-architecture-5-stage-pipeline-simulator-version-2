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
public class DIV1 extends StageCommunication {

    public DIV1(ProcessingUnitInner puInner) {
        super(puInner);
    }
    
    private Map<Instruction,Integer> divResultMap = new HashMap<Instruction,Integer>(); 

	public Map<Instruction, Integer> getDivResultMap() {
		return divResultMap;
	}

    @Override
    public void execute(int cycle_no) {
        
        this.currentExecutionCycle = cycle_no;
        
        if(getProcessingUnitInner().getDecode().isOutputDependency(this.currentExecutionCycle)){
            this.setInstruction(null);
        }else if(getProcessingUnitInner().getDecode().isInstructionStalled(this.currentExecutionCycle)){
            if(getProcessingUnitInner().getDecode().isInstructionForwarded(this.currentExecutionCycle) && puInner.getDecode().getInstruction().getInstruction().equalsIgnoreCase("DIV")){
                this.setInstruction(puInner.getDecode().getInstruction());
            }else{
                this.setInstruction(null);
            }
        }
        else if(getProcessingUnitInner().getDecode().getInstruction() == null){
            this.setInstruction(null);
        }
        else if (getProcessingUnitInner().getDecode().getInstruction().getInstruction().equalsIgnoreCase("DIV")  &&  getProcessingUnitInner().getDecode().getInstruction() != null){
            this.setInstruction(getProcessingUnitInner().getDecode().getInstruction());
        }
        else if (getProcessingUnitInner().getDecode().getInstruction().getInstruction().equalsIgnoreCase("HALT")  &&  getProcessingUnitInner().getDecode().getInstruction() != null){
            this.setInstruction(getProcessingUnitInner().getDecode().getInstruction());
        }
        else{
            this.setInstruction(null);
        }
        
        if (this.getInstruction() == null)
        {
			System.out.println("DIV1 Stage:");
			return;
	}
        else if(this.getInstruction().getInstruction().equalsIgnoreCase("HALT")){
            System.out.println("DIV1 Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
			return;
        }
        else{
            
            int operand1,operand2 =0;
            int operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                int operand2Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource2());
                
             /* forwarding data + normal execution for soucre 1 */
                if (getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                    operand1 = getProcessingUnitInner().getVirtualRegisters()[operand1Index].getValue();
                }else{
                    operand1 = ProcessingUnitComputation.getOperandValue(this.puInner,this.getInstruction().getSource1());
                }
                /* forwarding data + normal execution for soucre 2 */
                if (getProcessingUnitInner().getRegisters()[operand2Index].isForwarded()){
                    operand2 = getProcessingUnitInner().getVirtualRegisters()[operand2Index].getValue();
                }else{
                    operand2 = ProcessingUnitComputation.getOperandValue(this.puInner,this.getInstruction().getSource2());
                }
            
            int result = operand1/operand2;
            divResultMap.put(this.getInstruction(), result);
            
            int destIndex = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
            
            getProcessingUnitInner().getRegisters()[destIndex].setValid(false);
            getProcessingUnitInner().getRegisters()[destIndex].setLockedInCycle(this.currentExecutionCycle);
            getProcessingUnitInner().getRegisters()[destIndex].setLockingInstruction(this.getInstruction());
            getProcessingUnitInner().getVirtualRegisters()[destIndex].setValue(result);
        }
		System.out.println("DIV1 Stage:" +"(I"+instruction.getInstructionCount()+"): "+ instruction.getInstructionString());
    }
 public void releaseForwarding(){
     if (this.getInstruction()==null || this.getInstruction().getInstruction().equalsIgnoreCase("HALT"))
		{

			return ;
		}
     
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
