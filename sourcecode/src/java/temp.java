
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.stream.XMLStreamException;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Meet Dave
 */
public class temp {
    HashMap<String,ArrayList<String>> hypernym,hyponym;
    public static void main(String args[]) throws FileNotFoundException, XMLStreamException {
        temp t=new temp();
        t.start();
        
    }
    
    void start() throws FileNotFoundException, XMLStreamException{
        int i=0;
        search s=new search();
        s.searchWord("start");
        hypernym=new HashMap<String,ArrayList<String>>();
        hyponym=new HashMap<String,ArrayList<String>>();
        hypernym=s.getHypernym();
        hyponym=s.getHyponym();   
        System.out.println(hypernym.toString());
        System.out.println(hyponym.toString());
        
    }

}
