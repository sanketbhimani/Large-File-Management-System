
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sanket_b
 */
public class get_hash {
    HashMap <String,ArrayList<HashMap<String, HashMap<String,ArrayList<String>>>>> hm1 = null;
    public get_hash(ServletContext sc) throws FileNotFoundException, IOException, ClassNotFoundException{
        //while(sc.getAttribute("start").equals("false")){
            
        //}
       hm1 = (HashMap<String, ArrayList<HashMap<String, HashMap<String, ArrayList<String>>>>>) sc.getAttribute("main");
    }
    ArrayList a = null;
    public void get_word_details(String s){
        a =  (hm1.get(s));
    }
    
    
    
    public HashMap<String, ArrayList<String>> getHypernym(){
        HashMap hm = new HashMap();
        a.forEach((defarray)->{
            HashMap h = (HashMap)defarray;
            h.forEach((def,v)->{
                HashMap vv = (HashMap)v;
                hm.put(def,vv.get("hyper"));
            });
            
        });
        return hm;
    }
    
    public HashMap<String, ArrayList<String>> getHyponym(){
         HashMap hm = new HashMap();
        a.forEach((defarray)->{
            HashMap h = (HashMap)defarray;
            h.forEach((def,v)->{
                HashMap vv = (HashMap)v;
                hm.put(def,vv.get("hypo"));
            });
            
        });
        return hm;
    }
}
