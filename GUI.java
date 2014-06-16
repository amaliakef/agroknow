/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.agroknow.metadata.harvester;

import java.io.*;
import java.util.Scanner;
import org.jdom.JDOMException;
import uiuc.oai.OAIException;

/**
 *
 * @author Amalia
 */
public class GUI {
    

    public static String readString() { 
        Scanner scan= new Scanner(System.in);
        String text= scan.nextLine();
        return text;
   
    }

        
    
    

    public static void main(String[] args)throws OAIException,IOException, JDOMException {
        
        System.out.println("Give me the url");
        String param1=readString();
        
        System.out.println("Give the path");
        String param2=readString();
        
        System.out.println("Give protocol");
        String param3=readString();
    
        System.out.println("Do you want to start the harversting?(yes or no)");
        String x=readString();
        
        if( x!= "no"){
            HarvestAllProcess.listRecords(param1, param2, param3);
        }
    }
}

    
   

