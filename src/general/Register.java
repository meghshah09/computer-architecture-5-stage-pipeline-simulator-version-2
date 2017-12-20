package general;
/**
 *
 * @author skdc
 */
public class Register {

	private int value;
	private String name; 
	private boolean isValid;
	private int lockedInCycle;
	private Instruction lockingInstruction;
        private boolean isForwarded;
	


	public Register(String name) {

		this.name = name;
		this.value = 0;
		this.isValid = true;
                this.isForwarded =false;
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
        public boolean isForwarded() {
		return isForwarded;
	}
	public void setForwarded(boolean isValid) {
		this.isForwarded = isValid;
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
