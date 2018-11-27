import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FIFO {
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
        int flag = 0;
        int i, k, avail, count = 0;
        System.out.println("FIFO");
        for (i = 0; i < frame; i++)
            frm[i] = -1;

        int j = 0;
        for (i = 0; i < pagReq; i++) {
        
            avail = 0;
            flag = 0;
            for (k = 0; k < frame; k++)
                if (frm[k] == pagRQ[i])
                    avail = 1;

            if (avail == 0) {
            	if(frm[j] != -1){
            		System.out.printf("Page %d ",frm[j]);
            		flag = 1;
            	}
            	
                frm[j] = pagRQ[i];
                j = (j + 1) % frame;
                count++;

                if(flag == 1){
                	for (k = 0; k < frame; k++)
                        if ((frm[k] != -1) && (pagRQ[i] == frm[k])) {
                        	System.out.printf("unloaded from Frame %d, Page %d loaded into Frame %d ", k, pagRQ[i], k);
                            //System.out.printf("%d", frm[k]);
                        }
                }
                else{
                	for (k = 0; k < frame; k++)
                        if ((frm[k] != -1) && (pagRQ[i] == frm[k])) {
                        	System.out.printf("Page %d loaded into Frame %d ", pagRQ[i], k);
                            //System.out.printf("%d", frm[k]);
                        }
                }

                //System.out.printf("-->F");
            }

            if (avail == 1) {
                for (k = 0; k < frame; k++)
                    if ((frm[k] != -1) && (pagRQ[i] == frm[k])) {
                    	System.out.printf("Page %d already in Frame %d ", pagRQ[i], k);
                        //System.out.printf("%d", frm[k]);
                    }
            }
            System.out.printf("\n");
        }
        System.out.printf("No of Faults: %d", count);
	}
}
