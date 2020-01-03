/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.game.servlet;

import sit.int303.grianisland.core.model.Player;
import sit.int303.grianisland.util.StatusMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vable
 */
@WebServlet(name="ChooseClan", urlPatterns={"/ChooseClan"})
public class ChooseClan extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");//กำหนด charset ของ request
        response.setCharacterEncoding("UTF-8");//กำหนด charset ของ response
        try {
            PrintWriter out = response.getWriter();
            Player player = (Player) request.getSession(false).getAttribute("player");
            byte clan = Byte.parseByte(request.getParameter("id"));
            StatusMessage error = player.setClan(clan);
            if (error.isError()) {
                out.print(error.getMessage());
            } else {
                out.print("ok");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChooseClan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChooseClan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
