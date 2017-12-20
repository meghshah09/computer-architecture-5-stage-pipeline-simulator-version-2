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
public class DIV4 extends StageCommunication{

    public DIV4(ProcessingUnitInner puInner) {
        super(puInner);
    }

    
    @Override
    public void execute(int cycle_no) {
        this.currentExecutionCycle=cycle_no;
        
        if(puInner.getDIV3().getInstruction()!=null && (puInner.getDIV3().getInstruction().getInstruction().equalsIgnoreCase("DIV") || puInner.getDIV3().getInstruction().getInstruction().equalsIgnoreCase("HALT"))){
         this.setInstruction(puInner.getDIV3().getInstruction());
        }
        else{
            this.setInstruction(null);
        }
        if (this.getInstruction() == null)
	{
			System.out.println("DIV4 Stage:");
			return;
	}
        else if(this.getInstruction().getInstruction().equalsIgnoreCase("HALT")){
            System.out.println("DIV1 Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
			return;
        }
        else{
            
           
            int destIndex = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
           /* getProcessingUnitInner().getRegisters()[destIndex].setValue(result);
            getProcessingUnitInner().getRegisters()[destIndex].setValid(true);
            getProcessingUnitInner().getRegisters()[destIndex].setLockedInCycle(0);
            getProcessingUnitInner().getRegisters()[destIndex].setLockingInstruction(null); */
           
           
           
        }
		System.out.println("DIV4 Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
    }

    public void lock(){
        
        if (this.getInstruction()==null || this.getInstruction().getInstruction().equalsIgnoreCase("HALT"))
		{

			return ;
		}
        
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
                if(this.getInstruction()== null){
                    return;
                }
		if(!nonArithemeticInstruction.contains(this.getInstruction().getInstruction())) {

			int destinationValue= getProcessingUnitInner().getDIV1().getDivResultMap().get(this.getInstruction());
			if(destinationValue==0) {

				puInner.setZeroFlag(true);
				puInner.setZeroFlagSetBy(this.getInstruction());

			}
			else {
				puInner.setZeroFlag(false);
				puInner.setZeroFlagSetBy(this.getInstruction());

			}

		}
    }
    
}
