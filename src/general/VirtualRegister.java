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
public class VirtualRegister {
    
    
        private int value;
	private String name; 
	private boolean isValid;
	private int lockedInCycle;
	private Instruction lockingInstruction;
        
        public VirtualRegister(String name) {

		this.name = name;
		this.value = 0;
		this.isValid = true;
	}
        
        public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public int getLockedInCycle() {
		return lockedInCycle;
	}

	public void setLockedInCycle(int lockedInCycle) {
		this.lockedInCycle = lockedInCycle;
	}
	
	public Instruction getLockingInstruction() {
		return lockingInstruction;
	}

	public void setLockingInstruction(Instruction lockingInstruction) {
		this.lockingInstruction = lockingInstruction;
	}
    
    
}
