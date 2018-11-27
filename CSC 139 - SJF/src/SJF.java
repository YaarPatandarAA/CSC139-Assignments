import java.io.File;					//read file in library
import java.util.Formatter;				// write to file library
import java.io.FileNotFoundException;	// file not found exception
import java.text.DecimalFormat;			// decimal format library
import java.util.*;



public class SJF{
	static DecimalFormat df = new DecimalFormat("#.##");	// for decimal format
	
    public static void main(String[] args) {
    	String type = "null";	// string for type of scheduling
    	int timeQuanta = 0;		// timeQuanta for round robin only
    	
    	int processCount;		// amount of processes
    	
    	try {	// do all in a try catch to find errors
    		File x = new File("input.txt");		// import input file
    		Scanner scan = new Scanner(x);		//  create scanner for input file
    		
    		Formatter writeOUT = new Formatter("output.txt");	// create output file
    		
    		Scanner firstLine = new Scanner(scan.nextLine());	// scanner for first line
            Scanner secondLine = new Scanner(scan.nextLine());	// scanner for second line
            
            while (firstLine.hasNext()) {		// loop to find type of scheduling
                type = firstLine.next();	// finding type of scheduling
                if (type.equals("RR")) {	// only if scheduling is round robin then we find time quanta
                    timeQuanta = firstLine.nextInt();	// finding time quanta
                }
            }
            processCount = secondLine.nextInt();	// find amount process
            
            int processNumber[] 	= new int[processCount];	// array for processNumber
            int arrivalTime[]		= new int[processCount];	// array for arrivalTime
            int CPU_Burst_Time[] 	= new int[processCount];	// array for CPU_Burst_Time
            int Priority[] 			= new int[processCount];	// array for Priority

            for (int i = 0; i < processCount; i++) {	// loop to fill arrays from input file, loop runs to amount of processes given before
                Scanner thisLine = new Scanner(scan.nextLine());	// new scanner for the lines
                
                processNumber[i] = thisLine.nextInt();	// get process number
                arrivalTime[i] = thisLine.nextInt();	// get arrival time
                CPU_Burst_Time[i] = thisLine.nextInt();	// get CPU Burst Time
                Priority[i] = thisLine.nextInt();		// get priority number
                
                thisLine.close();	// close scanner
            }
            //close the file here, closing scanners
            scan.close();
            firstLine.close();
            secondLine.close();
            
            writeOUT.format("%s \n", type);	// write the type to the output file
            
 //===================================================================================================================================          

            int CPUBurst_t2[] = new int[processCount];	// variable needed for use later
            int CPUBurst_t2e[] = new int[processCount];
            int procNum_t2[] = new int[processCount];
            int arvTime_t2[] = new int[processCount];	// another variable needed for use later
            
            
            int savTime[] = new int[processCount];	// savTime variable for saving last know time of finished process
            float avgTime = 0;
            int initTime = 0;	// time counter
            int sort = 0; // sort counter
            boolean first = true;
            
            boolean flag = true;   // set flag to true to begin first pass
            int temp;   //holding variable
            
            for (int i = 0; i < processCount; i++){	// loop to fill temp1 with CPU Burst Time and temp2 with arrivalTime
            	CPUBurst_t2[i] = CPU_Burst_Time[i];	// give temp1 values of CPU-BurstTime to work with, can modify
            	CPUBurst_t2e[i] = CPU_Burst_Time[i];
            	arvTime_t2[i] = arrivalTime[i];		// give temp2 values of arrivalTime to work with, can modify
            	procNum_t2[i] = processNumber[i];
            }
            
            int procNum = 0; // counter for number of finished processes
            
			do{
				if(sort == 0){
					while ( flag ){
						flag= false;    //set flag to false awaiting a possible swap
						
						for(int k = 0; k < (processCount - 1); k++){
							if ( CPUBurst_t2[k] > CPUBurst_t2[k+1] ){   // change to > for ascending sort
								
								temp = CPUBurst_t2[k];                //swap elements
								CPUBurst_t2[k] = CPUBurst_t2[k+1];
								CPUBurst_t2[k+1] = temp;
								
								temp = procNum_t2[k];
								procNum_t2[k] = procNum_t2[k+1];
								procNum_t2[k+1] = temp;
								
								temp = arvTime_t2[k];
								arvTime_t2[k] = arvTime_t2[k+1];
								arvTime_t2[k+1] = temp;
								
								temp = CPUBurst_t2e[k];
								CPUBurst_t2e[k] = CPUBurst_t2e[k+1];
								CPUBurst_t2e[k+1] = temp;

				                flag = true;
				            }
						}
					}
					sort = 1;
				}
				
				for(int i = 0; i < processCount; i++){	// loop to iterate through all processes and work on them
					
					if (CPUBurst_t2[i] == 0){	// if process is done then don't do any work on it and continue out of loop
						continue;
					}
					
					if( (arvTime_t2[i] <= initTime) && (first == true) ){
						System.out.println("time: " + initTime + "   processNumber: " + procNum_t2[i]);
						writeOUT.format("%d  %d\n", initTime, procNum_t2[i]);
						
						savTime[i] = initTime;
						initTime += CPUBurst_t2[i];
						CPUBurst_t2[i] -= CPUBurst_t2[i];
						
						procNum++;
						first = false;
						continue;
					}
					
					if( (arvTime_t2[i] > initTime) && (first == true) ){	// if any process arrival time is greater that the current time
						continue;
					}
					
					else{	// work on process
						
							if (CPUBurst_t2[i] == 0){	// if process is done then don't do any work on it and continue out of loop
								continue;
							}
						
							if( (i < (processCount-1)) && (CPUBurst_t2[i] <= CPUBurst_t2e[i+1]) ){
								
								System.out.println("time: " + initTime + "   processNumber: " + procNum_t2[i]);
								writeOUT.format("%d  %d\n", initTime, procNum_t2[i]);
								
								savTime[i] = initTime;
								initTime += CPUBurst_t2[i];
								CPUBurst_t2[i] -= CPUBurst_t2[i];
								
								procNum++;
								continue;
							}
							
							if( (i == (processCount-1)) && (procNum == (processCount-1)) ){
								
								System.out.println("time: " + initTime + "   processNumber: " + procNum_t2[i]);
								writeOUT.format("%d  %d\n", initTime, procNum_t2[i]);
								
								savTime[i] = initTime;
								initTime += CPUBurst_t2[i];
								CPUBurst_t2[i] -= CPUBurst_t2[i];

								procNum++;
								continue;
							}
						
					}
				}
            }while(procNum != processCount);	//keep looping until all process are finished
			for(int i = 0; i < processCount; i++){
				avgTime += savTime[i];
			}
			for(int i = 0; i < processCount; i++){
				avgTime -= arvTime_t2[i];
			}
			avgTime = (float) (avgTime / processCount); // calculate average time by dividing the previous calculation by the amount of processes
            
            System.out.println("\n avgTime: " + df.format(avgTime));    // print to the console


//====================================================================================================================================           
// Write to Output.txt and close
			writeOUT.format("AVG Waiting Time: %f \n", avgTime);	// write the AVG time to the output.txt file
			writeOUT.close();	// close the output.txt file
			
//====================================================================================================================================  
        }catch (FileNotFoundException e) {	// catch the exception if the input file is not found
            System.out.println("file not found");	// show to console
        }
    }
}