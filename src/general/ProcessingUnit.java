/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import java.io.*;
import java.util.*;

/**
 *
 * @author skdc
 */

public class ProcessingUnit {
    
    ProcessingUnitInner puInner;
    public boolean haltEncountered =false;
   public void init(String s1) throws Exception{
        if(puInner== null) {
			this.puInner = new ProcessingUnitInner();
		
		
			this.puInner.init(s1);
		}    
        
        
    }
   
   
   /*execute the Stages */
   public void execute(int cycle_no){
   // logic for execution of cycle one by one.
                puInner.getWriteBack().execute(cycle_no);
		puInner.getMemory().execute(cycle_no);
		puInner.getIntegerFu().execute(cycle_no);
		puInner.getMUL2().execute(cycle_no);
		puInner.getMUL1().execute(cycle_no);
                puInner.getDIV4().execute(cycle_no);
                puInner.getDIV3().execute(cycle_no);
                puInner.getDIV2().execute(cycle_no);
                puInner.getDIV1().execute(cycle_no);
		puInner.getDecode().execute(cycle_no);
		puInner.getFetch().execute(cycle_no);
                
                puInner.getDIV1().releaseForwarding();
                puInner.getMUL1().releaseForwarding();
                puInner.getIntegerFu().releaseForwarding();
                puInner.getDecode().forwardingLogic();
                
                puInner.getWriteBack().lock(); 
                puInner.getMUL2().lock();
                puInner.getDIV4().lock();
                puInner.getIntegerFu().lock();
                if(puInner.getHaltFlag()){
                    haltEncountered = true;
                }
                
                
   }
   
   public static void display(ProcessingUnitInner puInner)
		{
			Register[] registers= puInner.getRegisters();
			int i;
			for (i=0;i<registers.length;i++)
			{
				System.out.println(registers[i].getName()+":"+registers[i].getValue());
			}
			
				
			for(Integer memLocation: puInner.getMemoryMap().keySet()) {
					
				System.out.println("Memory Address:"+memLocation+"\tValue:"+puInner.getMemoryMap().get(memLocation));
				
			} 
                }

    public ProcessingUnitInner getProcessingUnitInner() {
		return puInner;
	}
    
    public void setProcessingUnitInner(ProcessingUnitInner puInner) {
		this.puInner = puInner;
	}
    
    
}
