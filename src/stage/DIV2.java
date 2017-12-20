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
public class DIV2 extends StageCommunication{

    public DIV2(ProcessingUnitInner puInner) {
        super(puInner);
    }

    @Override
    public void execute(int cycle_no) {
        
        this.currentExecutionCycle=cycle_no;
        
        if(puInner.getDIV1().getInstruction()!=null){
         this.setInstruction(puInner.getDIV1().getInstruction());
        }else{
            this.setInstruction(null);
        }
        
        if (this.getInstruction() == null)
		{
			System.out.println("DIV2 Stage:");
			return;
		}
		System.out.println("DIV2 Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
    }
    
}
