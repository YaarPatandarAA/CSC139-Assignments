import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LRU {
	public static void main(String[] args) {

    	ArrayList <Integer> pagRQs = new ArrayList <Integer>();
    	
    	int page = 0;
    	int frame = 0;
    	int pagReq = 0;
    	
    	try {
    		File x = new File("input.txt");
    		Scanner scan = new Scanner(x);
    		
    		Scanner firstLine = new Scanner(scan.nextLine());
            while (firstLine.hasNext()) {
                page = firstLine.nextInt();
                frame = firstLine.nextInt();
                pagReq = firstLine.nextInt();
            }
            firstLine.close();
            
            for (int i = 0; i < pagReq; i++) {
                Scanner thisLine = new Scanner(scan.nextLine());
                
                pagRQs.add(thisLine.nextInt());
                
                thisLine.close();
            }
            
            //close the file here
            scan.close();
        }
    	catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    	
    	int pagRQ[] = new int[pagReq];
    	for(int i = 0; i < pagReq; i++){
        	pagRQ[i] = pagRQs.get(i);
        }
    	/*
        //display to print line here
        System.out.println("page is: " + page);
        System.out.println("frame is: " + frame);
        System.out.println("pagReq is: " + pagReq);
        System.out.println("page Requests");
        for(int i = 0; i < pagReq; i++){
        	System.out.println(pagRQ[i]);
        }*/
        
        //----------------------------------------------------------------------------------
		
		int[] frm = new int[frame+2];
        int[] used = new int[frame+2];
        int index = 0;
        int i, j, k = 0, temp;
        int flag = 0, pf = 0;
        System.out.printf("LRU\n");
        for (i = 0; i < frame; i++)
            frm[i] = -1;

        for (i = 0; i < pagReq; i++) {
            flag = 0;
            
            for (j = 0; j < frame; j++) {
                if (frm[j] == pagRQ[i]) { //no fault
                    System.out.printf("Page %d already in Frame %d \n", pagRQ[i], j);
                    flag = 1;
                    break;
                }
            }
            
            if (flag == 0) { //fault occurs
            	
                for (j = 0; j < frame; j++)
                    used[j] = 0; //all unused initially
                
                //moving through pages and searching recently used pages
                try {
                    for (j = 0, temp = i - 1; j < frame - 1; j++, temp--) {
                        for (k = 0; k < frame; k++) {
                            if (frm[k] == pagRQ[temp])
                                used[k] = 1;
                            //mark the recently used pages
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {}
                
                for (j = frame-1; j >= 0; j--)
                    if (used[j] == 0){
                    	index = j;
                    }
                
                if(frm[index] != -1){
                	System.out.printf("Page %d unloaded from Frame %d, ",frm[index], index);
                }

                //replace the lru page with new page
                frm[index] = pagRQ[i];
                System.out.printf("Page %d loaded into Frame %d \n", pagRQ[i], index);
                pf++; //no of page faults
            }
        }

        System.out.printf("%d page faults", pf);
	}

}
