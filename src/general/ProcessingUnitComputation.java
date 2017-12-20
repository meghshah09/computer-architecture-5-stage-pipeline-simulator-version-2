/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

/**
 *
 * @author skdc
 */
import java.util.*;
import java.io.*;


public class ProcessingUnitComputation {
    
    public static int getRegisterIndex(String str){
            int index = findIndex(str);
            return index;
    }
    public static int getOperandValue(ProcessingUnitInner puInner,String src){
        int operand;
		int registerPosition = getRegisterIndex(src);
		
		if(registerPosition!= -1) {
                    operand=puInner.getRegisters()[registerPosition].getValue();
		}
		else {
			operand=Integer.parseInt(src);
		}
		return operand;
    }
    
    public static boolean isSourceLocked(ProcessingUnitInner puInner, String src, int currentCycle) {
		if(src==null || src.length()==0 ) {
			return false;
		}
		
		int sourceRegisterPosition1 = getRegisterIndex(src);
		if(sourceRegisterPosition1 != -1 ) {
			Register register = puInner.getRegisters()[sourceRegisterPosition1];
			if(!register.isValid() && register.getLockedInCycle()!= currentCycle) {
				return true;
			}
                        
		}
		return false;
	}
    
    public static boolean wasForwardedFlagSet(ProcessingUnitInner puInner, String src){
        
        int sourceRegisterPosition1 = getRegisterIndex(src);
        if(sourceRegisterPosition1 != -1 ) {
           Register register = puInner.getRegisters()[sourceRegisterPosition1];
                     if( register.isForwarded()){
                         return true;
                     }
                     else {
                         return false;
                     }
		}
        return false;
    }
    private static  int findIndex(String regName ) {
        List<String> registers = new ArrayList<String>();
		registers.add("R0");
		registers.add("R1");
		registers.add("R2");
		registers.add("R3");
		registers.add("R4");
		registers.add("R5");
		registers.add("R6");
		registers.add("R7");
		registers.add("R8");
		registers.add("R9");
		registers.add("R10");
		registers.add("R11");
		registers.add("R12");
		registers.add("R13");
		registers.add("R14");
		registers.add("R15");
                
                return registers.indexOf(regName);
    }
    
    
    
    
}
