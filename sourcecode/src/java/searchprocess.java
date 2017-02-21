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
            throws ServletException, IOException, FileNotFoundException, XMLStreamException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            hypernym = new HashMap<String, ArrayList<String>>();
            hyponym = new HashMap<String, ArrayList<String>>();

            String searchKey = request.getParameter("searchKey");
            search s = new search();
            s.searchWord(searchKey);
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
        JsonArrayBuilder def = Json.createArrayBuilder();
        ArrayList<String> hyprCount, hypoCount;
        int cnt = 0;
        JsonArrayBuilder final_array = Json.createArrayBuilder();
        
        for (String key : hypernym.keySet()) {
            hyprCount = new ArrayList<String>();
            hypoCount = new ArrayList<String>();
            result = hypernym.get(key);
            JsonArrayBuilder adjacencies = Json.createArrayBuilder();
            for (int i = 0; i < result.size(); i++) {
                final_array.add(Json.createObjectBuilder().add("id", cnt + "hypr").add("name", result.get(i)).add("adjacencies", adjacencies.build()).add("data", Json.createObjectBuilder().add("$color", "#0000FF").add("$type", "square").add("$dim", 7).build()).build());
                hyprCount.add(cnt + "hypr");
                cnt++;
            }
            adjacencies = null;

            //   cnt++;
            result.clear();
            result = hyponym.get(key);
            adjacencies = Json.createArrayBuilder();
            for (int i = 0; i < result.size(); i++) {
                final_array.add(Json.createObjectBuilder().add("id", cnt + "hypo").add("name", result.get(i)).add("adjacencies", adjacencies.build()).add("data", Json.createObjectBuilder().add("$color", "#FF0000").add("$type", "square").add("$dim", 7).build()).build());
                hyprCount.add(cnt + "hypo");
                cnt++;
            }

            adjacencies = null;
            adjacencies = Json.createArrayBuilder();

            for (int i = 0; i < hyprCount.size(); i++) {
                adjacencies.add(Json.createObjectBuilder().add("nodeTo", hyprCount.get(i)).add("nodeFrom", cnt).add("data", Json.createObjectBuilder().add("$color", "#0000FF").build()).build());
            }

            for (int i = 0; i < hypoCount.size(); i++) {
                adjacencies.add(Json.createObjectBuilder().add("nodeTo", hypoCount.get(i)).add("nodeFrom", cnt).add("data", Json.createObjectBuilder().add("$color", "#FF0000").build()).build());
            }

            final_array.add(Json.createObjectBuilder().add("id", cnt).add("name", key).add("adjacencies", adjacencies.build()).add("data", Json.createObjectBuilder().add("$color", "#000000").add("$type", "square").add("$dim", 7).build()).build());

        }

       
       // JsonArrayBuilder array = Json.createArrayBuilder();
        
        //array.add(Json.createObjectBuilder().add("graph",final_array.build()).add("side_panel",side_panel.build()));
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
