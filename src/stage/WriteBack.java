package stage;
/**
 *
 * @author skdc
 */
import java.util.*;

import general.*;

public class WriteBack extends StageCommunication {

	public WriteBack(ProcessingUnitInner puInner) {
		super(puInner);
	}

	@Override
	public void execute(int currentExecutionCycle) {

		this.currentExecutionCycle= currentExecutionCycle;

		this.setInstruction(puInner.getMemory().getInstruction());
		if (this.getInstruction()==null)
		{
			System.out.println("WriteBack Stage:");			
			return ;
		}
                String str = this.getInstruction().getInstruction();
		switch (str)
		{
                    case "LOAD" :
			
				int registerDestIndex=ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination()); // getting the index of the destination register which will be used to place the data of the calc memory location.
				//getProcessingUnitInner().getRegisters()[registerDestIndex].setForwarded(false);
                                getProcessingUnitInner().getRegisters()[registerDestIndex].setValue(getProcessingUnitInner().getMemory().getBufferMemoryMap().get(this.getInstruction())); // now setting the value present at memory location into destination address.
				getProcessingUnitInner().getMemory().getBufferMemoryMap().remove(this.getInstruction()); // removing map of bufferMemoryMap which holds the value present inside the memory location calculated in execution stage.
				getProcessingUnitInner().getIntegerFu().getIntegerFuResult().remove(this.getInstruction()); // removing map of bufferMap which holds value of register and literal to calc memory address.
                                
			break;
                    case "STORE" :
			
				getProcessingUnitInner().getMemory().getBufferMemoryMap().remove(this.getInstruction()); // removing map of bufferMemoryMap which holds the value present inside the memory location calculated in execution stage.
				getProcessingUnitInner().getIntegerFu().getIntegerFuResult().remove(this.getInstruction()); // removing map of bufferMap which holds value of register and literal to calc memory address.

			break;
                    case "JUMP":
				System.out.println("Write Back Stage:"+"(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
				//return;
			break;
                    case "HALT" : 
				System.out.println("Write Back Stage:"+"(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
                                puInner.setHaltFlag(true);
				//return;
			break;
                    case "JAL" :
                        registerDestIndex = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                        getProcessingUnitInner().getRegisters()[registerDestIndex].setValue( this.getInstruction().getPcAddress()+ 4);
                        
                        break;
                    case "MUL" :
				registerDestIndex=ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination()); // getting the index of the destination register which will be used to place the data of the calc memory location.
				if(registerDestIndex != -1) {
					getProcessingUnitInner().getRegisters()[registerDestIndex].setValue(getProcessingUnitInner().getMUL1().getMultiplicationALUBufferMap().get(this.getInstruction()));
                                       // getProcessingUnitInner().getRegisters()[registerDestIndex].setForwarded(false);
                                }
				getProcessingUnitInner().getMUL1().getMultiplicationALUBufferMap().remove(this.getInstruction()); 


			break;
                    case "DIV" :
                        registerDestIndex = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                        
                        getProcessingUnitInner().getRegisters()[registerDestIndex].setValue(getProcessingUnitInner().getDIV1().getDivResultMap().get(this.getInstruction()));
                        //getProcessingUnitInner().getRegisters()[registerDestIndex].setForwarded(false);
                        getProcessingUnitInner().getDIV1().getDivResultMap().remove(this.getInstruction());
                        
                        break;
                    default :
				 registerDestIndex=ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
				if(registerDestIndex != -1) {
					getProcessingUnitInner().getRegisters()[registerDestIndex].setValue(getProcessingUnitInner().getIntegerFu().getIntegerFuResult().get(this.getInstruction()));
                                        //getProcessingUnitInner().getRegisters()[registerDestIndex].setForwarded(false);

				}
				getProcessingUnitInner().getIntegerFu().getIntegerFuResult().remove(this.getInstruction());


			break;


		}
                if(str.equalsIgnoreCase("HALT")){
                    return;
                }
                else if(str.equalsIgnoreCase("JUMP")){
                    return;
                }
		System.out.println("Write Back Stage:"+"(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());

	}



	public void lock() 
	{

		if (this.getInstruction()==null || this.getInstruction().getInstruction().equalsIgnoreCase("HALT"))
		{

			return ;
		}

		int destionationRegisterPosition = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
		if(destionationRegisterPosition != -1) 
		{
			if(getProcessingUnitInner().getRegisters()[destionationRegisterPosition].getLockingInstruction()==this.getInstruction()) {
				getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setValid(true);
				getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockedInCycle(0);
				getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockingInstruction(null);
			}

		}
	}
}
