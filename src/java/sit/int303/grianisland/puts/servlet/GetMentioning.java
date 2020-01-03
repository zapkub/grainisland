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
import sit.int303.grianisland.core.model.Player;

/**
 *
 * @author Vable
 */
@WebServlet(name="GetMentioning", urlPatterns={"/GetMentioning"})
public class GetMentioning extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //PrintWriter out = response.getWriter();
        String idStr = request.getParameter("id");
        try
        {
            int userId=Integer.parseInt(idStr);
            TreeMap<Integer,PutsMessage> timeline;
            if(userId==-2)
            {
                userId=((Player)request.getSession().getAttribute("player")).getId();
            }
            timeline=PutsBoard.getMentioning(userId);
            LinkedList<PutsMessage> putsMessages=new LinkedList(timeline.values());
            Collections.sort(putsMessages);
            request.setAttribute("putsMessages", putsMessages);
            
            //out.print("test");
        }
        catch(Exception ex)
        {
            request.setAttribute("error", new Error(ex.toString()));
            //out.print(ex);
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
