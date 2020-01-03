/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.core.servlet;

import sit.int303.grianisland.util.StatusMessage;
import sit.int303.grianisland.core.model.Player;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Varavut
 */
@WebServlet(name="Register", urlPatterns={"/Register"})
public class Register extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");//กำหนด charset ของ request
        response.setCharacterEncoding("UTF-8");//กำหนด charset ของ response
        try
        {
            //รับพารามิเตอร์มาเก็บในตัวแปร
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String confermEmail = request.getParameter("conferm_email");
            String password = request.getParameter("password");
            String ConfirmPassword = request.getParameter("Confirm_password");
            String sexStr = request.getParameter("sex");
            String birthDateStr = request.getParameter("birthDate");
            //แปลงสตริงของวันเกิดเป็น Date
            Date birthDate = new Date(birthDateStr);
            if (name==null||name.equals("") || email==null||email.equals("") ||confermEmail==null
                    || confermEmail.equals("") || password==null||password.equals("")
                    ||ConfirmPassword==null|| ConfirmPassword.equals("") || sexStr==null
                    ||sexStr.equals("") || birthDateStr==null || birthDateStr.equals(""))
            {
                //ถ้ามี error เกิดขึ้นให้เก็บลง request
                request.setAttribute("error", new StatusMessage("กรอกข้อมูลไม่ครบ"));
            }
            else if (!email.equals(confermEmail)){
                //ถ้ามี error เกิดขึ้นให้เก็บลง request
                request.setAttribute("error", new StatusMessage("การยืนยันอีเมลผิดพลาด"));
            }
            else if (!password.equals(ConfirmPassword)){
                //ถ้ามี error เกิดขึ้นให้เก็บลง request
                request.setAttribute("error", new StatusMessage("การยืนยันรหัสผ่านผิดพลาด"));
            }
            else
            {
                //กำหนดให้ sex = 1 คือเป็นผู้ชาย เท่ากับ 2 ถ้าเป็นผู้หญิง
                byte sex=1;
                if(sexStr.equals("female"))
                {
                    sex=2;
                }
                //สร้าง Player แล้วกำหนดค่า
                Player player = new Player();
                player.setName(name);
                player.setEmail(email);
                player.setPassword(password);
                player.setBirthDate(birthDate);
                player.setSex(sex);
                StatusMessage error = player.register();//ทำการบันทึกลงฐานข้อมูล
                if (error.isError())
                {
                    //ถ้ามี error เกิดขึ้นให้เก็บลง request
                    request.setAttribute("error", error);
                } else
                {
                    //เก็บ player ลง session เพื่อกำหนดว่า login แล้ว
                    player.login();
                    request.getSession().setAttribute("player", player);
                    request.getSession().setMaxInactiveInterval(60*60*2);
                    //ไปหน้า เลือก clan
                    getServletContext().getRequestDispatcher("/ChooseClan.jsp").forward(request, response);
                    return;
                }
            }
        }
        catch(Exception ex)
        {
            //ถ้ามี error เกิดขึ้นให้เก็บลง request
            request.setAttribute("error", new StatusMessage(ex.toString()));
        }
        //ไปหน้า index
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
