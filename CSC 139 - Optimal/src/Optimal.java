import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
 
public class Optimal {
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
        int flag1, flag2, flag3, i, j, k, pos = 0, max, faults = 0;
		int[] frm = new int[frame+2];
        int[] used = new int[frame+2];
        
        for(i = 0; i < frame; ++i){
            frm[i] = -1;
        }
        
        for(i = 0; i < pagReq; ++i){
            flag1 = flag2 = 0;
            
            for(j = 0; j < frame; ++j){
                if(frm[j] == pagRQ[i]){
                       flag1 = flag2 = 1;
                       System.out.printf("Page %d already in Frame %d\n", frm[j], j);
                       break;
                   }
            }
            
            if(flag1 == 0){
                for(j = 0; j < frame; ++j){
                    if(frm[j] == -1){
                        faults++;
                        frm[j] = pagRQ[i];
                        flag2 = 1;
                        System.out.printf("Page %d loaded into Frame %d\n", frm[j], j);
                        break;
                    }
                }    
            }
            
            if(flag2 == 0){
                flag3 = 0;
                
                for(j = 0; j < frame; ++j){
                    used[j] = -1;
                    
                    for(k = i + 1; k < pagReq; ++k){
                        if(frm[j] == pagRQ[k]){
                            used[j] = k;
                            break;
                        }
                    }
                }
                
                for(j = 0; j < frame; ++j){
                    if(used[j] == -1){
                        pos = j;
                        flag3 = 1;
                        System.out.printf("Page %d unloaded from Frame %d, ", frm[pos], pos);
                        break;
                    }
                }
                
                if(flag3 == 0){
                    max = used[0];
                    pos = 0;
                    
                    for(j = 1; j < frame; ++j){
                        if(used[j] > max){
                            max = used[j];
                            pos = j;
                            System.out.printf("Page %d unloaded from Frame %d, ", frm[pos], pos);
                        }
                    }                
                }
                
                frm[pos] = pagRQ[i];
                faults++;
                System.out.printf("Page %d loaded into Frame %d\n", frm[pos], pos);
            }
        }
        System.out.printf("%d page fault", faults);
    }
 
}