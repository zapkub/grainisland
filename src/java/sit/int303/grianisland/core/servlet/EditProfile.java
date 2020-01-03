
package sit.int303.grianisland.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sit.int303.grianisland.core.model.Player;

public class EditProfile extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
                String oldpassword = request.getParameter("oldpassword");
                String newpassword = request.getParameter("newpassword");
                String newpassagn = request.getParameter("newpassagn");
                Player py = (Player) request.getSession().getAttribute("player");
            try {
                if (py.changepassword(oldpassword,newpassword,newpassagn)) {
                    request.setAttribute("message", "เปลี่ยนรหัสผ่านสำเร็จ!!");
                }
                else
                    request.setAttribute("message", "เปลี่ยนรหัสผ่านล้มเหลว!!");
                getServletContext().getRequestDispatcher("/editpassword.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally { 
            out.close();
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
