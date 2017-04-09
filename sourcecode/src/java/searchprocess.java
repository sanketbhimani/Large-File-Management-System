/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Meet Dave
 */
@WebServlet(urlPatterns = {"/searchprocess"})
public class searchprocess extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    HashMap<String, ArrayList<String>> hypernym, hyponym;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException, XMLStreamException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            hypernym = new HashMap<String, ArrayList<String>>();
            hyponym = new HashMap<String, ArrayList<String>>();

            String searchKey = request.getParameter("searchKey");
            
            search s = new search();
            s.searchWord(searchKey);
            
            
            //search s = new search();
            //s.searchWord(searchKey);
            hypernym = s.getHypernym();
            hyponym = s.getHyponym();
            request.setAttribute("hypernym", hypernym);
            request.setAttribute("hyponym", hyponym);

            out.println(createJSONResponse(searchKey));
            //   RequestDispatcher rd = request.getRequestDispatcher("index.jsp?q="+searchKey);
            //  rd.forward(request, response);
        }
    }

    protected String createJSONResponse(String searchKey) {
        ArrayList<String> result;
        //ArrayList<String> side_result;
        ArrayList<String> hyprCount, hypoCount;
        int cnt = 0;
        JsonArrayBuilder final_array = Json.createArrayBuilder();
        JsonArrayBuilder defs = Json.createArrayBuilder();
        for (String key : hypernym.keySet()) {
            hyprCount = new ArrayList<String>();
            hypoCount = new ArrayList<String>();
            result = hypernym.get(key);
            
            JsonArrayBuilder hypo = Json.createArrayBuilder();
            JsonArrayBuilder hypr = Json.createArrayBuilder();
            
            for (int i = 0; i < result.size(); i++) {
                hypo.add(Json.createObjectBuilder().add("id", cnt+"truehypo").add("name", result.get(i)).add("children", Json.createArrayBuilder()));
                cnt++;
            }
            
            for (int i = 0; i < hyponym.get(key).size(); i++) {
                hypo.add(Json.createObjectBuilder().add("id", cnt+"truehypr").add("name", hyponym.get(key).get(i)).add("children", Json.createArrayBuilder()));
                cnt++;
            }
            
            //defs.add(Json.createObjectBuilder().add("id", cnt).add("name", key).add("children", Json.createArrayBuilder().add(Json.createObjectBuilder().add("id", cnt+1).add("name", "hypo").add("children",hypo)).add(Json.createObjectBuilder().add("id", cnt+2).add("name", "hypr").add("children",hypr))).build());
            defs.add(Json.createObjectBuilder().add("id", cnt+"s").add("name", key).add("children", hypo.build()));
            
            cnt+=3;
         }
            
            final_array.add(Json.createObjectBuilder().add("id", cnt+"s").add("name", searchKey).add("children", defs).build());
            
            return final_array.build().toString();
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException {
        try {
            processRequest(request, response);
        } catch (XMLStreamException ex) {
            Logger.getLogger(searchprocess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(searchprocess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException {
        try {
            processRequest(request, response);
        } catch (XMLStreamException ex) {
            Logger.getLogger(searchprocess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(searchprocess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
