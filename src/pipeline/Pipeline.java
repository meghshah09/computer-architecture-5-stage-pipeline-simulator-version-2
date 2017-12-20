/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipeline;

/**
 *
 * @author skdc
 */

import java.io.*;
import java.util.*;

import general.*;

public class Pipeline {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        ProcessingUnit pu = new ProcessingUnit();
        Scanner sc = new Scanner(System.in);
        int cycle_no;
        System.out.println("================ COA Project 2 ====================");
        System.out.println("Enter the File You want to run :");
        String str = sc.next();
        while(true){
        
            System.out.println("Enter Your Option :");
            System.out.println("1.Initialize");
            System.out.println("2.Simulate");
            System.out.println("3.Display");
            System.out.println("4.Exit");
            
            int option = sc.nextInt();
            switch(option){
                case 1:
                    System.out.println("Initialization Started.");
                    pu.init(str);
                    System.out.println("Initialization Completed.");
                    break;
                case 2:
                    System.out.println("Enter the Number of Cycles You want to Simulate : ");
                    cycle_no= sc.nextInt();
                    int i=1;
                    while(i<=cycle_no){
                        System.out.println("------- Cycle No."+i+" is Running -----------");
                        pu.execute(i);
                        System.out.println("");
                        if(pu.haltEncountered){
                            break;
                        }
                        i++;
                    }
                    break;
                case 3:
                    
                    pu.display(pu.getProcessingUnitInner());
                    break;
                case 4 :
                    
                    System.out.println("You are out now, please run the program again to use it");
                    System.exit(0);
                    break;
            }
    
    
        }
        
        
        
    }
    
}
