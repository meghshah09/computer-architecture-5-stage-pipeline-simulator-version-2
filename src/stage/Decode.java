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
public class Decode extends StageCommunication {

    public Decode(ProcessingUnitInner puInner) {
        super(puInner);
    }
    
    

    public Instruction lastArithmetic =null;
    
    public Instruction getLastArithemeticInstruction() {
	return lastArithmetic;
    }

    public void setLastArithemeticInstruction(Instruction lastArithemeticInstruction) {
	this.lastArithmetic = lastArithemeticInstruction;
    }
                
    @Override
    public void execute(int currentExecutionCycle) {
                List<String> nonArithemeticInstruction = new ArrayList<String>();
		nonArithemeticInstruction.add("HALT");
		nonArithemeticInstruction.add("BNZ");
		nonArithemeticInstruction.add("BZ");
		nonArithemeticInstruction.add("JUMP");
		nonArithemeticInstruction.add("LOAD");
		nonArithemeticInstruction.add("STORE");
		nonArithemeticInstruction.add("MOVC");
                nonArithemeticInstruction.add("JAL");
                nonArithemeticInstruction.add("AND");
                nonArithemeticInstruction.add("OR");
                nonArithemeticInstruction.add("EXOR");
                
                this.currentExecutionCycle = currentExecutionCycle;
                
                if(this.isOutputDependency(currentExecutionCycle)){
                    System.out.println("Decode RF Stage:"+ "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString() + " Bubble output");
			return;
                }
                else if(this.isInstructionForwarded(currentExecutionCycle)){
                        this.setInstruction(puInner.getFetch().getInstruction());
                }
                else if(this.isInstructionStalled(currentExecutionCycle) || this.isInstructionStalledAtNextStage()) {
                    
                        System.out.println("Decode RF Stage:"+ "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString() + " Bubble");
			return;
                    
		}
                else{
                    this.setInstruction(puInner.getFetch().getInstruction());
                }
                
                if (this.getInstruction() == null)
		{
			System.out.println("Decode Stage:");
			return;
		}
                
                
                
                
            /* to be change after these */    
                String [] strArray = null;
		strArray = instruction.getInstructionString().split("[,\\s\\#]+");
		instruction.setInstruction(strArray[0]);
               

		if(strArray.length>1)
		{
			instruction.setDestination(strArray[1]);

		}
		else
		{
			instruction.setDestination(null); // if Halt instr comes, then in that case no destination will be present.
		}


		if(strArray.length>2)
		{
			instruction.setSource1(strArray[2]);

		}
		else
		{
			instruction.setSource1(null); // if BNZ instr comes, than in that case only one register will be needed.
		}

		if(strArray.length>3) {
			instruction.setSource2(strArray[3]);
		}
		else
		{
			instruction.setSource2(null);	// if MOVC R1 #10 type instruction comes than it will not take source2.
		}
                
		System.out.println("Decode RF Stage:"+ "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
                
                if(this.getInstruction().getInstruction().equalsIgnoreCase("HALT")) {
		getProcessingUnitInner().setProgramCounter(-1);
		}
		
		if(!nonArithemeticInstruction.contains(this.instruction.getInstruction())) {
			this.lastArithmetic= this.instruction;
		}
                
               
               // forwardingLogic();
           
                
    }
    public boolean isInstructionForwarded(int currentExecutionCycle){
        if (this.getInstruction() == null){
            return false;
        }
        
        if(this.getInstruction().getInstruction().equalsIgnoreCase("STORE")){
            if(ProcessingUnitComputation.wasForwardedFlagSet(this.puInner,this.getInstruction().getDestination()) && ProcessingUnitComputation.wasForwardedFlagSet(this.puInner,this.getInstruction().getSource1())){
                     return true;
            }
            else if(ProcessingUnitComputation.wasForwardedFlagSet(this.puInner,this.getInstruction().getDestination()) && !(ProcessingUnitComputation.isSourceLocked(this.puInner, this.getInstruction().getSource1(), currentExecutionCycle ))){
            return true;
        }else if(ProcessingUnitComputation.wasForwardedFlagSet(this.puInner,this.getInstruction().getSource1()) && !(ProcessingUnitComputation.isSourceLocked(this.puInner, this.getInstruction().getDestination(), currentExecutionCycle ))){
            return true;
        }
        }
        else if(this.getInstruction().getInstruction().equalsIgnoreCase("JUMP")) {
                if(ProcessingUnitComputation.wasForwardedFlagSet(this.puInner, this.getInstruction().getDestination())) {
				return true;	
			}
                
        }
        else if(ProcessingUnitComputation.wasForwardedFlagSet(this.puInner,this.getInstruction().getSource1()) && ProcessingUnitComputation.wasForwardedFlagSet(this.puInner,this.getInstruction().getSource2())){
            return true;
        }
        else if(ProcessingUnitComputation.wasForwardedFlagSet(this.puInner,this.getInstruction().getSource1()) && !(ProcessingUnitComputation.isSourceLocked(this.puInner, this.getInstruction().getSource2(), currentExecutionCycle ))){
            return true;
        }else if(ProcessingUnitComputation.wasForwardedFlagSet(this.puInner,this.getInstruction().getSource2()) && !(ProcessingUnitComputation.isSourceLocked(this.puInner, this.getInstruction().getSource1(), currentExecutionCycle ))){
            return true;
        }
        
        
            return false;
        
    }

    public boolean isInstructionStalled(int currentExecutionCycle) { // due to registers
		
		
		if (this.getInstruction()==null)
		{
			return false;
		}
		
                
		if(this.getInstruction().getInstruction().equalsIgnoreCase("STORE") || this.getInstruction().getInstruction().equalsIgnoreCase("JUMP")) {
			
			if(ProcessingUnitComputation.isSourceLocked(this.puInner, this.getInstruction().getDestination(), currentExecutionCycle ) 
                                || ProcessingUnitComputation.isSourceLocked(this.puInner, this.getInstruction().getSource1(), currentExecutionCycle )) {
				
				return true;
				
			}
                        else{
                            return false;
                        }
		}
		
		if(ProcessingUnitComputation.isSourceLocked(this.puInner, this.getInstruction().getSource1(), currentExecutionCycle ) || ProcessingUnitComputation.isSourceLocked(this.puInner, this.getInstruction().getSource2(),currentExecutionCycle ) ) 
		{
			return true;
		}
		
		if(this.getInstruction().getInstruction().equalsIgnoreCase("BZ") || this.getInstruction().getInstruction().equalsIgnoreCase("BNZ")) {
			if(!(getProcessingUnitInner().getZeroFlagSetBy() == this.lastArithmetic)) {
			return true;
		}
		}
		return false;

	}
    //mostly this will go in forwarding
	public boolean isInstructionStalledAtNextStage() { // due to stalling at MUL/EXE stage.
		if (this.getInstruction()==null)
		{

			return false;
		}

		if(!this.getInstruction().getInstruction().equals("MUL") && this.getInstruction() != puInner.getIntegerFu().getInstruction() && !this.getInstruction().getInstruction().equals("DIV") && !this.getInstruction().getInstruction().equals("HALT")) {
			return true;
		}
		return false;

	}
        
        public boolean isOutputDependency(int currentCycle){
            if(this.getInstruction() == null){
                return false;
            }
         if(!(this.getInstruction().getInstruction().equalsIgnoreCase("STORE") || this.getInstruction().getInstruction().equalsIgnoreCase("JUMP"))) {   
            if(ProcessingUnitComputation.isSourceLocked(this.puInner,this.getInstruction().getDestination(),currentCycle)){
                return true;
        }
        
            
        }    
            return false;
        }

    public void forwardingLogic() {
        /*getting Indexes*/  
        int src1,src2=0;
    if(this.getInstruction() != null){
        if(this.getInstruction().getInstruction().equalsIgnoreCase("STORE")){
                src1 = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                src2 = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
        }else if(this.getInstruction().getInstruction().equalsIgnoreCase("JUMP")){
            src1 = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
            src2 =-1;
        }else{
                src1 = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                src2 = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource2());
        }
        /*setting Forwarding Flag */
        if(getProcessingUnitInner().getDIV4().getInstruction() != null){
            
            if(this.getInstruction().getInstruction().equalsIgnoreCase("STORE")){
                if(src1 !=-1){
                if(this.getInstruction().getDestination().equalsIgnoreCase(getProcessingUnitInner().getMUL2().getInstruction().getDestination())){
                        
                    getProcessingUnitInner().getRegisters()[src1].setForwarded(true);
                    getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
                }
            }
          if(src2 !=-1){
            if(this.getInstruction().getSource1().equalsIgnoreCase(getProcessingUnitInner().getMUL2().getInstruction().getDestination())){
                        
                    getProcessingUnitInner().getRegisters()[src2].setForwarded(true);
                    getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
                }
            
            }
            }else if(this.getInstruction().getInstruction().equalsIgnoreCase("JUMP")){
                if(src1 !=-1){
                if(this.getInstruction().getDestination().equalsIgnoreCase(getProcessingUnitInner().getMUL2().getInstruction().getDestination())){
                        
                    getProcessingUnitInner().getRegisters()[src1].setForwarded(true);
                    getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
                }
            }
             if(src2 !=-1)   {}
             
            }else{ 
            if(src1 !=-1){
            if(this.getInstruction().getSource1().equalsIgnoreCase(getProcessingUnitInner().getDIV4().getInstruction().getDestination())){
                        getProcessingUnitInner().getRegisters()[src1].setForwarded(true);
                        getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
                        
                        
        }
            }
        if(src2 !=-1){
        if(this.getInstruction().getSource2().equalsIgnoreCase(getProcessingUnitInner().getDIV4().getInstruction().getDestination())){
                        getProcessingUnitInner().getRegisters()[src2].setForwarded(true);
                       getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
        }
        }
        }
        }
        if(getProcessingUnitInner().getMUL2().getInstruction() != null){
            if(this.getInstruction().getInstruction().equalsIgnoreCase("STORE")){
                if(src1 !=-1){
                if(this.getInstruction().getDestination().equalsIgnoreCase(getProcessingUnitInner().getMUL2().getInstruction().getDestination())){
                        
                    getProcessingUnitInner().getRegisters()[src1].setForwarded(true);
                    getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
                }
            }
          if(src2 !=-1){
            if(this.getInstruction().getSource1().equalsIgnoreCase(getProcessingUnitInner().getMUL2().getInstruction().getDestination())){
                        
                    getProcessingUnitInner().getRegisters()[src2].setForwarded(true);
                    getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
                }
            
            }
            }else if(this.getInstruction().getInstruction().equalsIgnoreCase("JUMP")){
                if(src1 !=-1){
                if(this.getInstruction().getDestination().equalsIgnoreCase(getProcessingUnitInner().getMUL2().getInstruction().getDestination())){
                        
                    getProcessingUnitInner().getRegisters()[src1].setForwarded(true);
                    getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
                }
            }
             if(src2 !=-1)   {}
             
            }else{
            if(src1 !=-1){
        if(this.getInstruction().getSource1().equalsIgnoreCase(getProcessingUnitInner().getMUL2().getInstruction().getDestination())){
                        getProcessingUnitInner().getRegisters()[src1].setForwarded(true);
                        getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
        }
        }
        if(src2 !=-1){
        if(this.getInstruction().getSource2().equalsIgnoreCase(getProcessingUnitInner().getMUL2().getInstruction().getDestination())){
                        getProcessingUnitInner().getRegisters()[src2].setForwarded(true);
                        getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
        }
        }
        }
        }
        if(getProcessingUnitInner().getIntegerFu().getInstruction() != null){
            
            if(this.getInstruction().getInstruction().equalsIgnoreCase("STORE")){
                if(src1 !=-1){
                if(this.getInstruction().getDestination().equalsIgnoreCase(getProcessingUnitInner().getIntegerFu().getInstruction().getDestination())){
                        
                    getProcessingUnitInner().getRegisters()[src1].setForwarded(true);
                    getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
                }
            }
          if(src2 !=-1){
            if(this.getInstruction().getSource1().equalsIgnoreCase(getProcessingUnitInner().getIntegerFu().getInstruction().getDestination())){
                        
                    getProcessingUnitInner().getRegisters()[src2].setForwarded(true);
                    getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
                }
            
            }
            }else if(this.getInstruction().getInstruction().equalsIgnoreCase("JUMP")){
                if(src1 !=-1){
                if(this.getInstruction().getDestination().equalsIgnoreCase(getProcessingUnitInner().getIntegerFu().getInstruction().getDestination())){
                        
                    getProcessingUnitInner().getRegisters()[src1].setForwarded(true);
                    getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
                }
            }
             if(src2 !=-1)   {}
             
            }else{
               if(src1 !=-1){
            if(this.getInstruction().getSource1().equalsIgnoreCase(getProcessingUnitInner().getIntegerFu().getInstruction().getDestination())){
                        
                    getProcessingUnitInner().getRegisters()[src1].setForwarded(true);
                    getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
                }
            
        }
        if(src2 !=-1){    
        if(this.getInstruction().getSource2().equalsIgnoreCase(getProcessingUnitInner().getIntegerFu().getInstruction().getDestination())){
                        
                    getProcessingUnitInner().getRegisters()[src2].setForwarded(true);
                    getProcessingUnitInner().setForwardFlagTo(this.getInstruction());
                } 
            }
            
        }
        
        
        
        
        
        
        }
    }
        
    }  
}
