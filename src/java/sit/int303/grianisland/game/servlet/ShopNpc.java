/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.game.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sit.int303.grianisland.core.model.Player;
import sit.int303.grianisland.game.model.Card;
import sit.int303.grianisland.game.model.CardManager;
import sit.int303.grianisland.game.model.CardShop;

/**
 *
 * @author Zapdos
 */
public class ShopNpc extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try 
        {
            CardShop cs = new CardShop();
            Player pl =(Player) request.getSession().getAttribute("player");
            if(request.getParameter("npc").equals("shop")){//เมนู
                out.print("ว่าไงหล่ะ เอาอะไรดี ?<br>"
                + "  <b href=\"#\" class=\"npc\" id=\"buy\">ซื้อ</b>"
                + "  <b href=\"#\" class=\"npc\" id=\"sell\">ขาย</b>"
                //+ "  <b href=\"#\" class=\"npc\" id=\"buy\">เสี่ยงทาย</b>"
                );
            }else if(request.getParameter("npc").equals("buy")){//แสดงรายการการ์ด
                out.print("<H1 style=\"color:white\">รายการการ์ดที่มีขาย</H1>");
                Card tmp = new Card();
                LinkedList<Card> cardList = cs.getStorelist();
                for(Card card:cardList)
                {
                    out.print("<b href=\"#\" class=\"card\" id=\""+card.getCardId()

                                    + "\">"+card.getCardName()+" ราคา "+card.getCardPrice()+" gam</b><br>");
                }
                out.print("วันนี้ก็มีเท่านี้แหล่ะ");
            }else if(request.getParameter("npc").equals("buycard")){//ซื้อการ์ด
                Card card=Card.getCard(Integer.parseInt(request.getParameter("card")));
                if(pl.removeMoney(card.getCardPrice()))
                {
                    out.print("คุณซื้อการ์ด "+card.getCardName()+" ราคา "+(card.getCardPrice())+" gam");
                    cs.buyCard(Integer.parseInt(request.getParameter("card")),pl.getId());
                    CardManager cm = new CardManager(pl.getId());
                    request.getSession().setAttribute("cardset", cm.loadCard());
                }
                else
                {
                    out.print("คุณมีเงินไม่พอ");
                }
                }else if(request.getParameter("npc").equals("sell")){//แสดงรายการการ์ดที่มี
                    out.print("<H1 style=\"color:white\">รายการการ์ดที่คุณมี</H1>");
                    CardManager cm = new CardManager(pl.getId());
                    LinkedList<Card> cardSet=cm.loadCard();
                    for(Card card:cardSet)
                    {
                        out.print("<b href=\"#\" class=\"sellCard\" id=\""+card.getCardId()
                        + "\">"+card.getCardName()+" ราคา "+(card.getCardPrice()/2)+" gam จำนวน"+card.getCardAmount()+"ใบ</b><br>");
                    }
                }else if(request.getParameter("npc").equals("sellCard")){//ขายการ์ด
                    Card card=Card.getCard(Integer.parseInt(request.getParameter("card")));
                    if(pl.removeMoney(-(card.getCardPrice()/2)))
                    {
                        out.print("ขายการ์ด "+card.getCardName()+" ได้เงิน "+(card.getCardPrice()/2)+" gam");
                        cs.sellCard(Integer.parseInt(request.getParameter("card")),pl.getId());
                        CardManager cm = new CardManager(pl.getId());
                        request.getSession().setAttribute("cardset", cm.loadCard());
                    }
                }
            } catch (Exception ex) {
               ex.printStackTrace(out);
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
