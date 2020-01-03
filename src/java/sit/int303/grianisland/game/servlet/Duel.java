/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.game.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sit.int303.grianisland.core.model.Player;
import sit.int303.grianisland.game.model.Card;
import sit.int303.grianisland.game.model.CardBattle;
import sit.int303.grianisland.game.model.CardManager;

/**
 *
 * @author Zapdos
 */
public class Duel extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            Player pl = (Player) request.getSession().getAttribute("player");
           // Player ob = new Player();
             CardBattle cb = new CardBattle((String)request.getParameter("Bid"));

              //  ob.setId(cb.getUserid());

            
                   String result = "พัง";
                    Card card = new Card();
                        int opATK = card.getCard(cb.getCardid()).getATK();
                        int usDEF = card.getCard(Integer.parseInt(request.getParameter("cardId"))).getDEF();
                            request.setAttribute("opcard", cb.getCardid());
                            request.setAttribute("uscard", request.getParameter("cardId"));
                        if(opATK>usDEF){

                            result = "You lost : "+"Winer is ";
                            pl.getPutsBoard().puts("\""+pl.getName()+"\", Dual with \""+pl.getName(cb.getUserid())+"\", Winner is \""+pl.getName(cb.getUserid())+"\"");

                            pl.removeMoney(15);
                            pl.removeMoneyById(-30,cb.getUserid()+"");
                        }else if(opATK<usDEF){
                            result = "win";

  pl.getPutsBoard().puts("\""+pl.getName()+"\", Dual with \""+pl.getName(cb.getUserid())+"\",Winner is \""+pl.getName()+"\"");


                            pl.removeMoney(-30);
                            pl.removeMoneyById(15,cb.getUserid()+"");
                        }else if(opATK==usDEF){
                            result = "draw";
                             pl.getPutsBoard().puts("\""+pl.getName()+"\" ,Dual with \""+pl.getName(cb.getUserid())+"\" but Draw");
                            pl.removeMoney(0);
                        }else
                                result  = "พัง";

              
          // out.print(opponentid+"<BR><BR><BR>");
              
CardManager cm = new CardManager(pl.getId());
               request.getSession().setAttribute("cardset", cm.loadCard());
               request.setAttribute("result",result);
               request.setAttribute("opcard", ""+cb.getCardid());
               request.setAttribute("uscard", request.getParameter("cardId"));
                cb.dropBattle((String)request.getParameter("Bid"));

                getServletContext().getRequestDispatcher("/DuelResult.jsp").forward(request, response);


         /*  out.print("YOU!!"+cb.getOpponentid()+"VS"+cb.getUserid()+"<BR>");
                out.print("your card is "+request.getParameter("cardId")+"<BR>");
                out.print("Opponent card is"+cb.getCardid());*/
        } catch(Exception e){ e.printStackTrace(out);}finally {
            out.close();
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
