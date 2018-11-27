import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class fileIN{

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
    	
        //display to print line here
        System.out.println("page is: " + page);
        System.out.println("frame is: " + frame);
        System.out.println("pagReq is: " + pagReq);
        System.out.println("page Requests");
        for(int i = 0; i < pagReq; i++){
        	pagRQ[i] = pagRQs.get(i);
        	System.out.println(pagRQ[i]);
        }
    }
}