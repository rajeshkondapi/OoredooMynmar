package AndroidAppiumAuto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;

import java.util.StringTokenizer;  
public class Test {
	
	    public static void main(String[] args) {
	       /* List<String> arr = new ArrayList<>();
	        DecimalFormat df2 = new DecimalFormat("#.##");
	        arr.add("2.37 GB");
	        arr.add("3GB");
	       
	        arr.add("7.5 GB");
	        double count = 0;
	        for(String s : arr){
	            String sp = s.replace("GB", "");
	            double val = Double.parseDouble(sp.trim());
	            count = count + val;
	            df2.format(count);
	            
	        }
	        System.out.println("Count: " + df2.format(count) + " GB");
	    }*/
	    	
	    	
	    	
	    	
	    	   /*StringTokenizer st = new StringTokenizer("my name is khan uday@gmail.com"," ");  
	    	     while (st.hasMoreTokens()) {  
	    	         System.out.println(st.nextToken()); */
	    	        //System.out.println(st.equals("uday@gmail.com"));
	    	        String mess = "my name is khan uday@gmail.com";
	    	        int i = 0;
	    	        String[] arr = mess.split(" ");
	    	        String finalVal = "";
	    			for (String token : arr) {
	    				if (token.equalsIgnoreCase("uday") || token.equalsIgnoreCase("uday@")) {
	    					finalVal = arr[i - 1];
	    					break;
	    				}
	    				i++;
	    	     }  
	    			System.out.println(finalVal);
	    	   }  
	    
	}