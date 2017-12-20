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
public class IntegerFu extends StageCommunication{

    public IntegerFu(ProcessingUnitInner puInner) {
        super(puInner);
    }
    
    private Map<Instruction,Integer> integerFuResult = new HashMap<Instruction,Integer>(); 


	public Map<Instruction, Integer> getIntegerFuResult() {
		return integerFuResult;
	}


	public void setIntegerFuResult(Map<Instruction, Integer> intResultMap) {
		this.integerFuResult = intResultMap;
	}
    
    
    @Override
    public void execute(int currentExecutionCycle) {
        
        this.currentExecutionCycle= currentExecutionCycle;
            
                
                if (this.getInstruction()!=null && this.getInstruction()!=puInner.getMemory().getInstruction() ) {
			System.out.println("Integer FU Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
			return;

		}
                else if(puInner.getDecode().getInstruction()!=null && (puInner.getDecode().getInstruction().getInstruction().equalsIgnoreCase("MUL") || puInner.getDecode().getInstruction().getInstruction().equalsIgnoreCase("DIV") || puInner.getDecode().getInstruction().getInstruction().equalsIgnoreCase("HALT"))) {
			this.setInstruction(null);
		} 
		else if(getProcessingUnitInner().getDecode().isOutputDependency(this.currentExecutionCycle)) {
			this.setInstruction(null);
		}
                else if(getProcessingUnitInner().getDecode().isInstructionStalled(this.currentExecutionCycle)){
                    if(getProcessingUnitInner().getDecode().isInstructionForwarded(this.currentExecutionCycle) && puInner.getDecode().getInstruction() != null){
                        this.setInstruction(puInner.getDecode().getInstruction());
                    }else{
                        this.setInstruction(null);
                    }
                }
                
		else
		{	
			this.setInstruction(puInner.getDecode().getInstruction());

		}
                
                if (this.getInstruction() == null)
		{
			System.out.println("Integer Fu Stage:");
			return;
		}
                
		System.out.println("Integer FU Stage:" + "(I"+instruction.getInstructionCount()+"): "+instruction.getInstructionString());
                
                String instr = this.getInstruction().getInstruction();
                int operand1,operand2=0;
                switch(instr){
                    
                    case "ADD" :
                        int operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                        int operand2Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource2());
                
                        /* forwarding data + normal execution for soucre 1 */
                         if (getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                             operand1 = getProcessingUnitInner().getVirtualRegisters()[operand1Index].getValue();
                             
                          }else{
                                operand1 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource1());
                           }
                        /* forwarding data + normal execution for soucre 2 */
                        if (getProcessingUnitInner().getRegisters()[operand2Index].isForwarded()){
                            operand2 = getProcessingUnitInner().getVirtualRegisters()[operand2Index].getValue();
                            
                        }else{
                             operand2 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource2());
                        }

                        int result= operand1 + operand2 ;
		
                        integerFuResult.put(this.getInstruction(), result);
                        int destionationRegisterPosition = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setValid(false);
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
                        getProcessingUnitInner().getVirtualRegisters()[destionationRegisterPosition].setValue(result);
                        break;
                    case "SUB" :
                        
                        operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                        operand2Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource2());
                
                        /* forwarding data + normal execution for soucre 1 */
                         if (getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                             operand1 = getProcessingUnitInner().getVirtualRegisters()[operand1Index].getValue();
                             
                          }else{
                                operand1 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource1());
                           }
                        /* forwarding data + normal execution for soucre 2 */
                        if (getProcessingUnitInner().getRegisters()[operand2Index].isForwarded()){
                            operand2 = getProcessingUnitInner().getVirtualRegisters()[operand2Index].getValue();
                            
                        }else{
                             operand2 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource2());
                        }
                        
                        result= operand1 - operand2 ;
		
                        integerFuResult.put(this.getInstruction(), result);
                        destionationRegisterPosition = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setValid(false);
		
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
                        
                        getProcessingUnitInner().getVirtualRegisters()[destionationRegisterPosition].setValue(result);
                        break;
                        
                    case "MOVC" :
                        
                        int registerValue = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource1());
                            integerFuResult.put(this.getInstruction(),registerValue);
                            
                        destionationRegisterPosition = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setValid(false);
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());    
                        getProcessingUnitInner().getVirtualRegisters()[destionationRegisterPosition].setValue(registerValue);
                        break;
                        
                    case "AND":
                        
                        operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                        operand2Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource2());
                
                        /* forwarding data + normal execution for soucre 1 */
                         if (getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                             operand1 = getProcessingUnitInner().getVirtualRegisters()[operand1Index].getValue();
                          }else{
                                operand1 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource1());
                           }
                        /* forwarding data + normal execution for soucre 2 */
                        if (getProcessingUnitInner().getRegisters()[operand2Index].isForwarded()){
                            operand2 = getProcessingUnitInner().getVirtualRegisters()[operand2Index].getValue();
                        }else{
                             operand2 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource2());
                        }

                        result= operand1 & operand2;
		
                        integerFuResult.put(this.getInstruction(), result);
                        destionationRegisterPosition = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setValid(false);
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
                    
                        getProcessingUnitInner().getVirtualRegisters()[destionationRegisterPosition].setValue(result);
                        break;
                        
                    case "OR" :
                        operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                        operand2Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource2());
                
                        /* forwarding data + normal execution for soucre 1 */
                         if (getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                             operand1 = getProcessingUnitInner().getVirtualRegisters()[operand1Index].getValue();
                          }else{
                                operand1 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource1());
                           }
                        /* forwarding data + normal execution for soucre 2 */
                        if (getProcessingUnitInner().getRegisters()[operand2Index].isForwarded()){
                            operand2 = getProcessingUnitInner().getVirtualRegisters()[operand2Index].getValue();
                        }else{
                             operand2 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource2());
                        }

                        result= operand1 | operand2;
		
                        integerFuResult.put(this.getInstruction(), result);
                        destionationRegisterPosition = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setValid(false);
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
                     
                        getProcessingUnitInner().getVirtualRegisters()[destionationRegisterPosition].setValue(result);
                        break;
                       
                    case "EXOR" :
                        
                        operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                        operand2Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource2());
                
                        /* forwarding data + normal execution for soucre 1 */
                         if (getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                             operand1 = getProcessingUnitInner().getVirtualRegisters()[operand1Index].getValue();
                          }else{
                                operand1 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource1());
                           }
                        /* forwarding data + normal execution for soucre 2 */
                        if (getProcessingUnitInner().getRegisters()[operand2Index].isForwarded()){
                            operand2 = getProcessingUnitInner().getVirtualRegisters()[operand2Index].getValue();
                        }else{
                             operand2 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource2());
                        }

                        result= operand1 ^ operand2;
		
                        integerFuResult.put(this.getInstruction(), result);
                        destionationRegisterPosition = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setValid(false);
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
                        getProcessingUnitInner().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
                        
                        getProcessingUnitInner().getVirtualRegisters()[destionationRegisterPosition].setValue(result);
                        break;
                       
                    case "BZ" :
                        int loopLiteral = ProcessingUnitComputation.getOperandValue(this.puInner,this.getInstruction().getDestination());
                        int newAddress = getProcessingUnitInner().getProgramCounter()+loopLiteral -8;
                        integerFuResult.put(this.instruction, newAddress);
                        break;
                    case "BNZ" :
                        loopLiteral = ProcessingUnitComputation.getOperandValue(this.puInner,this.getInstruction().getDestination());
                        newAddress = getProcessingUnitInner().getProgramCounter()+loopLiteral -8;
                        integerFuResult.put(this.instruction, newAddress);
                        break;
                    case "JUMP" :
                        int src1;
                        operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                        if (getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                             src1 = getProcessingUnitInner().getVirtualRegisters()[operand1Index].getValue();
                          }else{
                            src1= ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getDestination());
                        }
                        int src2 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource1());
                        newAddress = src1+src2;
                        integerFuResult.put(this.instruction, newAddress);
                        break;
                    case "JAL" :
                        
                        
                        
                        operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                        if (getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                             src1 = getProcessingUnitInner().getVirtualRegisters()[operand1Index].getValue();
                          }else{
                                src1 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource1());
                           }
                        src2 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource2());
                        newAddress = src1 +src2 ;
                        integerFuResult.put(this.instruction, newAddress);
                        int dest = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                        getProcessingUnitInner().getRegisters()[dest].setValid(false);
                        getProcessingUnitInner().getRegisters()[dest].setLockedInCycle(this.getCurrentExecutionCycle());
                        getProcessingUnitInner().getRegisters()[dest].setLockingInstruction(this.getInstruction());
                        getProcessingUnitInner().getVirtualRegisters()[dest].setValue( this.getInstruction().getPcAddress()+ 4);
                        break;
                    
                    case "LOAD" :
                        
                        src2 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource2());
                        /* forwarding for source register */
                        operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                        if (getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                             src1 = getProcessingUnitInner().getVirtualRegisters()[operand1Index].getValue();
                          }else{
                                src1= ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource1());
                           }
                        
                       
                            result = src1 +src2;
                            integerFuResult.put(this.getInstruction(), result);
                        
                        
                        dest = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                        getProcessingUnitInner().getRegisters()[dest].setValid(false);
                        getProcessingUnitInner().getRegisters()[dest].setLockedInCycle(this.getCurrentExecutionCycle());
                        getProcessingUnitInner().getRegisters()[dest].setLockingInstruction(this.getInstruction());
                        
                        break;
                    case "STORE" :
                        operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                        if (getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                             src1 = getProcessingUnitInner().getVirtualRegisters()[operand1Index].getValue();
                          }else{
                            src1= ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource1());
                        }
                        
                        src2 = ProcessingUnitComputation.getOperandValue(this.puInner, this.getInstruction().getSource2());
                        
                            result = src1 + src2;
                            integerFuResult.put(this.getInstruction(), result);
                       
                        break;
                 
                }
                
        
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

			int destinationValue= getProcessingUnitInner().getIntegerFu().getIntegerFuResult().get(this.getInstruction());
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
    
    public void releaseForwarding(){
     
     if(this.getInstruction() != null){
         
         if(this.getInstruction() == getProcessingUnitInner().getForwardFlagTo()){
             int operand1,operand2,operand1Index,operand2Index =0;
             if(this.getInstruction().getInstruction().equalsIgnoreCase("JUMP")){
                 operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
             }
             else if(this.getInstruction().getInstruction().equalsIgnoreCase("STORE")){
                 operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getDestination());
                 operand2Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
             }else{
                operand1Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource1());
                operand2Index = ProcessingUnitComputation.getRegisterIndex(this.getInstruction().getSource2());
             }
             
                if(getProcessingUnitInner().getRegisters()[operand1Index].isForwarded()){
                    getProcessingUnitInner().getRegisters()[operand1Index].setForwarded(false);
                    getProcessingUnitInner().setForwardFlagTo(null);
                }
                else{
                    getProcessingUnitInner().getRegisters()[operand2Index].setForwarded(false);
                    getProcessingUnitInner().setForwardFlagTo(null);
                }
             
         }
     }
 }
}
