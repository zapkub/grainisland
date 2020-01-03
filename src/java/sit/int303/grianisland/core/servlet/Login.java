package sit.int303.grianisland.core.servlet;

import sit.int303.grianisland.core.model.Player;
import sit.int303.grianisland.util.StatusMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sit.int303.grianisland.game.model.CardManager;
import sit.int303.grianisland.util.Shuffle;

/**
 *
 * @author Varavut
 * คลาสสำหรับควบคุมการ login
 */
@WebServlet(name="Login", urlPatterns={"/Login"})
public class Login extends HttpServlet {

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
        String email=request.getParameter("email");//รับ Parameter email มาเก็บใส่ตัวแปร
        String password=request.getParameter("password");//รับ Parameter passwird มาเก็บใส่ตัวแปร
        //ถ้ามี Parameter email ที่ความยาวไม่เป็น 0 และมี Parameter password ที่ความยาวไม่เป็น 0
        if(email!=null && email.trim().length()!=0 && password!=null && password.trim().length()!=0)
        {
            try {
                Player player = new Player();//สร้าง Player
                player.setEmail(email.trim());//ตั้งค่า email ให้ Player
                player.setPassword(password.trim());//ตั้งค่า password ให้ Player
                StatusMessage error=player.login();//ทำการ login
                if (!error.isError()) {//ถ้าไม่เกิด error
                    request.getSession().setAttribute("player", player);
                    request.getSession().setMaxInactiveInterval(60*60*2);
                    String authString=System.currentTimeMillis()+player.getName();
                    authString=Shuffle.shuffle(authString);
                    player.setAuthString(authString);
                    Cookie authCookie=new Cookie("auth",authString);
                    authCookie.setMaxAge(24*7*7);
                    authCookie.setPath("/");
                    response.addCookie(authCookie);

               CardManager cm = new CardManager(player.getId());
               request.getSession().setAttribute("cardset", cm.loadCard());

                    /*forward ไปหน้าอื่น*/
                    getServletContext().getRequestDispatcher("/Game.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("error", error);//เก็บค่า error ลง request
            } catch (SQLException ex) {
                request.setAttribute("error", new Error(ex.toString()));//เก็บค่า error ลง request
            } catch (ClassNotFoundException ex) {
                request.setAttribute("error", new Error(ex.toString()));//เก็บค่า error ลง request
            }
        }
        else
        {
            request.setAttribute("error", new Error("Missing email or password!!"));
        }
        /*forward ไปหน้าอื่น*/
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
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
