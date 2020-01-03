/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.puts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sit.int303.grianisland.puts.model.PutsBoard;
import sit.int303.grianisland.puts.model.PutsMessage;

/**
 *
 * @author Vable
 */
@WebServlet(name="GetPublicTimeline", urlPatterns={"/GetPublicTimeline"})
public class GetPublicTimeline extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try
        {
            TreeMap<Integer,PutsMessage> timeline=PutsBoard.getPublicTimeline();
            LinkedList<PutsMessage> putsMessages=new LinkedList(timeline.values());
            Collections.sort(putsMessages);
            request.setAttribute("putsMessages", putsMessages);
        }
        catch(Exception ex)
        {
            request.setAttribute("error", new Error(ex.toString()));
        }
        getServletContext().getRequestDispatcher("/PublicTimeline.jsp").forward(request, response);
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
