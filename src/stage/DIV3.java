/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stage;

import general.ProcessingUnitInner;

/**
 *
 * @author skdc
 */
public class DIV3 extends StageCommunication {

    public DIV3(ProcessingUnitInner puInner) {
        super(puInner);
    }

    @Override
    public void execute(int cycle_no) {
        
        this.currentExecutionCycle=cycle_no;
        
        if(puInner.getDIV2().getInstruction()!=null){
         this.setInstruction(puInner.getDIV2().getInstruction());
        }
        else{
            this.setInstruction(null);
        }
        
        if (this.getInstruction() == null)
		{
			System.out.println("DIV3 Stage:");
			return;
		}
		System.out.println("DIV3 Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
        
    }
    
}
