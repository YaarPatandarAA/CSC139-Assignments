import java.io.File;					//read file in library
import java.util.Formatter;				// write to file library
import java.io.FileNotFoundException;	// file not found exception
import java.text.DecimalFormat;			// decimal format library
import java.util.*;


public class RoundRobinTEST{
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
            
            writeOUT.format("%s\n", type);	// write the type to the output file
            
 //===================================================================================================================================          
 // Round Robin          
            
            int temp1[] = new int[processCount];	// variable needed for use later
            int temp2[] = new int[processCount];	// another variable needed for use later
            int savTime[] = new int[processCount];	// savTime variable for saving last know time of finished process
            int initTime = 0;	// time counter
            float avgTime = 0;	// average time variable

            for (int i = 0; i < processCount; i++){	// loop to fill temp1 with CPU Burst Time and temp2 with arrivalTime
            	temp1[i] = CPU_Burst_Time[i];	// give temp1 values of CPU-BurstTime to work with, can modify
            	temp2[i] = arrivalTime[i];		// give temp2 values of arrivalTime to work with, can modify
            }

            
            int procNum = 0; // counter for number of finished processes
			do{	// round robin loop, round robin algorithm in here. loop runs until all process are finished
				
				for(int i = 0; i < processCount; i++){	// loop to iterate through all processes and work on them
					
					/*System.out.println("\nCPU_Burst_Time: " + temp1[i]);
					System.out.println("processCount: " + processCount);
					System.out.println("iteration: " + i);
					System.out.println("procNum: " + procNum);*/
					if(temp2[0] > initTime){	// if the first process is arrival time greater than 0
						initTime++;	// increase the current time as nothing is happening right now but time is going by
						continue;
					}
					
					if(temp2[i] > initTime){	// if any process arrival time is greater that the current time
						continue;
					}
					
					if (temp1[i] == 0){	// if process is done then don't do any work on it and continue out of loop
						continue;
					}
					
					else{	// work on process
						if(temp1[i] > timeQuanta){	//if CPU Burst time is greater then the given timeQuanta
							
							System.out.println("time: " + initTime + "   processNumber: " + processNumber[i]);	// print statements to show in console
							writeOUT.format("%d  %d\n", initTime, processNumber[i]);	// write out to output.txt the current time and process number
							
							initTime += timeQuanta;	// increase current time by timeQuanta
							temp1[i] -= timeQuanta;	// decrease the CPU Burst Time by the timeQuanta
							
							if(temp1[i] == 0){	// only if after decreasing the timeQuanta the CPU Burst Time becomes 0
								savTime[i] = initTime;	// save the current time to that location in array so it matches with the current process in the loop
							}
						}
						
						else if(temp1[i] <= timeQuanta){	// if CPU Burst time is less than or equal to the given timeQuanta
							
							System.out.println("time: " + initTime + "   processNumber: " + processNumber[i]);	// print statements to show in console
							writeOUT.format("%d  %d\n", initTime, processNumber[i]);	// write out to output.txt the current time and process number
							
							initTime += temp1[i];	// increase current time by CPU Burst Time, note it is less than timeQuanta
							temp1[i] -= temp1[i];	// decrease the CPU Burst Time by the CPU Burst time, note it is less than the timeQuanta
							
							if(temp1[i] == 0){	// only if after decreasing the CPU Burst time the CPU Burst Time becomes 0
								savTime[i] = initTime;	// save the current time to that location in array so it matches with the current process in the loop
							}
							
							procNum++;	// now that the process is done increment the precess counter created before
						}
						
					}
				}
            	
            }while(procNum != processCount);	//keep looping until all process are finished
            
			//System.out.println("\n time: " + (initTime) );
			
			for(int i = 0; i < processCount; i++){	// add up all saved times of the processes
				avgTime += savTime[i];	
			}
			for(int i = 0; i < processCount; i++){	// subtract all CPU Burst Times of the processes
				avgTime -= CPU_Burst_Time[i];
			}
			avgTime = (float) (avgTime / processCount);	// calculate average time by dividing the previous calculation by the amount of processes
			
			System.out.println("\n avgTime: " + df.format(avgTime));	// print to the console
            
            
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