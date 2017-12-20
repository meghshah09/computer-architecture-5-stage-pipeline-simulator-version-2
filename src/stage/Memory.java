package stage;
/**
 *
 * @author skdc
 */
import java.util.*;


import general.*;

public class Memory extends StageCommunication {

	public Memory(ProcessingUnitInner puInner) {
		super(puInner);
	}

	private Map<Instruction,Integer> bufferMemoryMap = new HashMap<Instruction,Integer>(); 


	public Map<Instruction,Integer> getBufferMemoryMap() {
		return bufferMemoryMap;
	}


	public void setBufferMemoryMap(Map<Instruction,Integer> bufferMemoryMap) {
		this.bufferMemoryMap = bufferMemoryMap;
	}


	@Override
	public void execute(int currentExecutionCycle) {
		
		this.currentExecutionCycle= currentExecutionCycle;
		if (puInner.getDIV4().getInstruction() != null){
                    this.setInstruction(puInner.getDIV4().getInstruction());
                }
                else if(getProcessingUnitInner().getMUL2().getInstruction() != null) 
		{
			this.setInstruction(puInner.getMUL2().getInstruction());

		}
		else {
			this.setInstruction(puInner.getIntegerFu().getInstruction());

		}
		if (this.getInstruction() == null)
		{
			System.out.println("Memory Stage:");			
			return;
		}
		String str = this.getInstruction().getInstruction();
                switch (str){
                    case "LOAD" :
                        System.out.println("Memory Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
				int memAddress= getProcessingUnitInner().getIntegerFu().getIntegerFuResult().get(this.getInstruction());
				Integer memValue= getProcessingUnitInner().getMemoryMap().get(memAddress);
				if (memValue==null) {
					memValue=0;
				}
			
				bufferMemoryMap.put(this.getInstruction(),memValue);
				
				
			break;
                    case "STORE" :
			System.out.println("Memory Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
				int registerDestinationPosition=ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
				int registerDestinationValue= getProcessingUnitInner().getRegisters()[registerDestinationPosition].getValue();
				getProcessingUnitInner().getMemoryMap().put(getProcessingUnitInner().getIntegerFu().getIntegerFuResult().get(this.getInstruction()), registerDestinationValue);
				
			break;
                    case "BNZ" :
                        System.out.println("Memory Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
				if(!getProcessingUnitInner().isZeroFlag()) {
					
					getProcessingUnitInner().setProgramCounter(getProcessingUnitInner().getIntegerFu().getIntegerFuResult().get(this.getInstruction()));
					getProcessingUnitInner().getFetch().setInstruction(null);
					getProcessingUnitInner().getDecode().setInstruction(null);
					getProcessingUnitInner().getFetch().setBranched(true);
				}
				
			break;
                    case "BZ" :
                        System.out.println("Memory Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
				if(getProcessingUnitInner().isZeroFlag()) {

					getProcessingUnitInner().setProgramCounter(getProcessingUnitInner().getIntegerFu().getIntegerFuResult().get(this.getInstruction()));
					getProcessingUnitInner().getFetch().setInstruction(null);
					getProcessingUnitInner().getDecode().setInstruction(null);
					getProcessingUnitInner().getFetch().setBranched(true);
				}
			break;
                    case "JUMP" :
                                
                                int counter = getProcessingUnitInner().getIntegerFu().getIntegerFuResult().get(this.getInstruction());
                                if(getProcessingUnitInner().getInstructionMap().get(counter) == null){
                                    getProcessingUnitInner().setProgramCounter(-1);
                                }else{
                                    getProcessingUnitInner().setProgramCounter(counter);
                                }
				getProcessingUnitInner().getFetch().setInstruction(null);
				getProcessingUnitInner().getDecode().setInstruction(null);
				getProcessingUnitInner().getFetch().setBranched(true);
                                System.out.println("Memory Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
                    break;
                    case "JAL" :
                                getProcessingUnitInner().setProgramCounter(getProcessingUnitInner().getIntegerFu().getIntegerFuResult().get(this.getInstruction()));
				getProcessingUnitInner().getFetch().setInstruction(null);
				getProcessingUnitInner().getDecode().setInstruction(null);
				getProcessingUnitInner().getFetch().setBranched(true);
                                System.out.println("Memory Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
                        break;
                    default :
                        System.out.println("Memory Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
                        break;
            }

	}



}
