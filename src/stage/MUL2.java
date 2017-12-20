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
public class MUL2 extends StageCommunication{

    public MUL2(ProcessingUnitInner puInner) {
        super(puInner);
    }

    @Override
    public void execute(int cycle_no) {
        
        this.currentExecutionCycle = cycle_no;
        this.setInstruction(puInner.getMUL1().getInstruction());
        
        if(this.getInstruction()== null) 
	{
			System.out.println("MUL2 Stage:");
                        return;	
	}
        else{
            
        }
	
		System.out.println("MUL2 Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
    }
    
    public void lock(){
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

			int destinationValue= getProcessingUnitInner().getMUL1().getMultiplicationALUBufferMap().get(this.getInstruction());
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
