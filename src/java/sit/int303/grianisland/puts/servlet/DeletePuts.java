/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.puts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sit.int303.grianisland.core.model.Player;
import sit.int303.grianisland.util.StatusMessage;

/**
 *
 * @author Varavut
 */
@WebServlet(name="DeletePuts", urlPatterns={"/DeletePuts"})
public class DeletePuts extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");//กำหนด charset ของ request
        response.setCharacterEncoding("UTF-8");//กำหนด charset ของ response
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String putsIdStr = request.getParameter("putsId");
        if(putsIdStr!=null && !putsIdStr.trim().equals(""))
        {
            try {
                int putsId = Integer.parseInt(putsIdStr);
                Player player = (Player) request.getSession(false).getAttribute("player");
                StatusMessage status=player.getPutsBoard().remove(putsId);
                if(status.isError())
                {
                    out.println(status.getMessage());
                }
                else
                {
                    out.print("ok");
                    return;
                }
            } catch (SQLException ex) {
                out.print(ex);
            } catch (ClassNotFoundException ex) {
                out.print(ex);
            }
        }
        else
        {
            out.println("error");
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
