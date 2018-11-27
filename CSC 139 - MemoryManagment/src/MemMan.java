/******************************************************************
 * Assignment 4: Memory Managment
 *
 * Name: Amarjit Singh
 *
 * Due Date: 5/10/17
 *
 ******************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

public class MemMan {
    public static void main(String[] args) {
    	int i = 0, j = 0, k = 0;
        int flag1, flag2, flag3;
        int pgFault = 0, curPos = 0, topFIFO = 0;

        int page = 0;	// not used anywhere
        int frame = 0;
        int pagReq = 0;

        try {
            File x = new File("input.txt");
            Scanner scan = new Scanner(x);
            Formatter writeOUT = new Formatter("output.txt");
            
            Scanner firstLine = new Scanner(scan.nextLine());
            while (firstLine.hasNext()) {
                page = firstLine.nextInt();
                frame = firstLine.nextInt();
                pagReq = firstLine.nextInt();
            }
            firstLine.close();
        	int[] frm = new int[frame];
        	int[] used = new int[frame];

        	int pagRQ[] = new int[pagReq];
            for (i = 0; i < pagReq; i++) {
                Scanner thisLine = new Scanner(scan.nextLine());
                pagRQ[i] = thisLine.nextInt();
                thisLine.close();
            }
            scan.close();

            //----------------------------------------------------------------------------------

            System.out.println("FIFO");
            writeOUT.format("FIFO\n");
            pgFault = 0;

            for (i = 0; i < frame; i++)
                frm[i] = -1;
            for (i = 0; i < pagReq; i++) {
                flag1 = flag2 = 0;
                for (j = 0; j < frame; j++) { // check to see if already in frame
                    if (frm[j] == pagRQ[i]) {
                        flag1 = 1;
                        flag2 = 1;
                        System.out.printf("Page %d already in Frame %d \n", pagRQ[i], j);
                        writeOUT.format("Page %d already in Frame %d \n", pagRQ[i], j);
                    }
                }
                if (flag1 == 0) {
                    for (j = 0; j < frame; j++) { // initial values to fill the frame
                        if (frm[j] == -1) {
                            frm[j] = pagRQ[i];
                            System.out.printf("Page %d loaded into Frame %d \n", pagRQ[i], j);
                            writeOUT.format("Page %d loaded into Frame %d \n", pagRQ[i], j);
                            pgFault++;
                            flag2 = 1;
                            break;
                        }
                    }
                }
                if (flag2 == 0) {	// unload old and load new values
                	System.out.printf("Page %d unloaded from Frame %d, ", frm[topFIFO], topFIFO);
                	writeOUT.format("Page %d unloaded from Frame %d, ", frm[topFIFO], topFIFO);
                    frm[topFIFO] = pagRQ[i];
                    System.out.printf("Page %d loaded into Frame %d \n", pagRQ[i], topFIFO);
                    writeOUT.format("Page %d loaded into Frame %d \n", pagRQ[i], topFIFO);
                    topFIFO++;
                    pgFault++;
                    if (topFIFO >= frame)
                    	topFIFO = 0;
                }
            }
            System.out.printf("%d page fault\n\n", pgFault);
            writeOUT.format("%d page fault\n\n", pgFault);

            //----------------------------------------------------------------------------------

            System.out.printf("Optimal\n");
            writeOUT.format("Optimal\n");
            pgFault = 0; curPos = 0;

            for(i = 0; i < frame; ++i){
                frm[i] = -1;
            }
            for(i = 0; i < pagReq; ++i){
                flag1 = flag2 = 0;
                for(j = 0; j < frame; ++j){	// check to see if already in frame
                    if(frm[j] == pagRQ[i]){
                           flag1 = flag2 = 1;
                           System.out.printf("Page %d already in Frame %d\n", frm[j], j);
                           writeOUT.format("Page %d already in Frame %d\n", frm[j], j);
                           break;
                       }
                }
                if(flag1 == 0){	// initial values to fill the frame
                    for(j = 0; j < frame; ++j){
                        if(frm[j] == -1){
                        	pgFault++;
                            frm[j] = pagRQ[i];
                            flag2 = 1;
                            System.out.printf("Page %d loaded into Frame %d\n", frm[j], j);
                            writeOUT.format("Page %d loaded into Frame %d\n", frm[j], j);
                            break;
                        }
                    }   
                }
                if(flag2 == 0){	// every other values
                    flag3 = 0;
                    for(j = 0; j < frame; ++j){ // mark used locations
                        used[j] = -1;
                        for(k = i + 1; k < pagReq; ++k){// find plausible location
                            if(frm[j] == pagRQ[k]){
                                used[j] = k;
                                break;
                            }
                        }
                    }
                    for(j = 0; j < frame; ++j){	// location that will be unloaded
                        if(used[j] == -1){
                            curPos = j;
                            flag3 = 1;
                            System.out.printf("Page %d unloaded from Frame %d, ", frm[curPos], curPos);
                            writeOUT.format("Page %d unloaded from Frame %d, ", frm[curPos], curPos);
                            break;
                        }
                    }
                    if(flag3 == 0){	// find next position to fill
                    	int max;
                        max = used[0];
                        curPos = 0;
                        for(j = 1; j < frame; ++j){
                            if(used[j] > max){
                                max = used[j];
                                curPos = j;
                            }
                        }                
                    }
                    frm[curPos] = pagRQ[i];	// new values from initial values
                    pgFault++;
                    System.out.printf("Page %d loaded into Frame %d\n", frm[curPos], curPos);
                    writeOUT.format("Page %d loaded into Frame %d\n", frm[curPos], curPos);
                }
            }
            System.out.printf("%d page fault\n\n", pgFault);
            writeOUT.format("%d page fault\n\n", pgFault);

            //----------------------------------------------------------------------------------

            System.out.printf("LRU\n");
            writeOUT.format("LRU\n");
            pgFault = 0; curPos = 0;
            
            for (i = 0; i < frame; i++)
                frm[i] = -1;
            for (i = 0; i < pagReq; i++) {
                flag1 = 0;
                for (j = 0; j < frame; j++) { // check to see if already in frame
                    if (frm[j] == pagRQ[i]) { 
                        System.out.printf("Page %d already in Frame %d \n", pagRQ[i], j);
                        writeOUT.format("Page %d already in Frame %d \n", pagRQ[i], j);
                        flag1 = 1;
                        break;
                    }
                }
                if (flag1 == 0) { 
                    for (j = 0; j < frame; j++)
                        used[j] = 0; 
                    try {
                    	int temp;
                        for (j = 0, temp = i - 1; j < frame - 1; j++, temp--) {	// mark used locations for frame
                            for (k = 0; k < frame; k++) {
                                if (frm[k] == pagRQ[temp])
                                    used[k] = -1;
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {}
                    for (j = frame - 1; j >= 0; j--)
                        if (used[j] == 0) {
                        	curPos = j;
                        }
                    if (frm[curPos] != -1) { // unload page from current frame position
                        System.out.printf("Page %d unloaded from Frame %d, ", frm[curPos], curPos);
                        writeOUT.format("Page %d unloaded from Frame %d, ", frm[curPos], curPos);
                    }
                    frm[curPos] = pagRQ[i];	// load new value into from, or initial values
                    System.out.printf("Page %d loaded into Frame %d \n", pagRQ[i], curPos);
                    writeOUT.format("Page %d loaded into Frame %d \n", pagRQ[i], curPos);
                    pgFault++;
                }
            }
            System.out.printf("%d page fault\n", pgFault);
            writeOUT.format("%d page fault\n", pgFault);
            
          //----------------------------------------------------------------------------------
            
            writeOUT.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }
}