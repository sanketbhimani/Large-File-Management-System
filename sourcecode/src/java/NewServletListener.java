
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Web application lifecycle listener.
 *
 * @author Sanket_b
 */
public class NewServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FileInputStream fileIn = null;
         HashMap <String,ArrayList<HashMap<String, HashMap<String,ArrayList<String>>>>> hm1 = null;
        /*try {
            ServletContext servletContext = (ServletContext) sce.getServletContext();
            //servletContext.setAttribute("start", "false");
            //fileIn = new FileInputStream("C:\\Users\\Sanket Bhimani\\Desktop\\data_old");
            //ObjectInputStream in = new ObjectInputStream(fileIn);
            //hm1 = (HashMap<String,ArrayList<HashMap<String, HashMap<String,ArrayList<String>>>>>) in.readObject();
            
            
            //servletContext.setAttribute("main", hm1);
            //servletContext.setAttribute("start", "true");
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        } finally {
            try {
                fileIn.close();
            } catch (IOException ex) {
               
            }
        }*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
