/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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

@WebServlet(urlPatterns = {"/aaa"})
public class aaa extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
            {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        try{
            HashMap<String, ArrayList<String>> hypernym = new HashMap<String, ArrayList<String>>();
            HashMap<String, ArrayList<String>> hyponym = new HashMap<String, ArrayList<String>>();

            String searchKey = request.getParameter("searchKey");
            search s = new search();
            s.searchWord(searchKey);
            hypernym = s.getHypernym();
            hyponym = s.getHyponym();

            ArrayList<String> result;
            JsonArrayBuilder def = Json.createArrayBuilder();

            int cnt = 0;
            for (String key : hypernym.keySet()) {

                JsonArrayBuilder hypr = Json.createArrayBuilder();
                result = hypernym.get(key);
                for (int i = 0; i < result.size(); i++) {
                    hypr.add(Json.createObjectBuilder().add("name", result.get(i)).add("id", cnt).build());
                    cnt++;
                }
                JsonArrayBuilder hypo = Json.createArrayBuilder();
                result = hyponym.get(key);
                for (int i = 0; i < result.size(); i++) {
                    hypo.add(Json.createObjectBuilder().add("name", result.get(i)).add("id", cnt).build());
                    cnt++;
                }

                JsonArrayBuilder hyp = Json.createArrayBuilder();
                hyp.add(Json.createObjectBuilder().add("name", "Hypo").add("id", cnt + "SSS").add("children", hypo).build());
                cnt++;
                hyp.add(Json.createObjectBuilder().add("name", "Hypr").add("id", cnt + "SSS").add("children", hypr).build());
                cnt++;

                def.add(Json.createObjectBuilder().add("name", key).add("id", cnt + "SSS").add("children", hyp).build());
                cnt++;
            }

            JsonObjectBuilder ooo = Json.createObjectBuilder().add("id", cnt + "SSS").add("name", searchKey).add("children", def);

            out.println(ooo.build().toString());

        }catch(FileNotFoundException e){
            out.println(e.getMessage());
        }
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
            throws ServletException, IOException {
        processRequest(request, response);
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
            throws ServletException, IOException {
        processRequest(request, response);
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
