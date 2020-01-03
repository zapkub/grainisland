/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.core.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;
import sit.int303.grianisland.core.model.Player;
import sit.int303.grianisland.util.ComponentHouseResizer;

/**
 *
 * @author Vable
 */
@WebServlet(name="ChangeAvatar", urlPatterns={"/ChangeAvatar"})
public class ChangeAvatar extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            if (MultipartFormDataRequest.isMultipartFormData(request))
            {
                MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
                String todo = null;
                if (mrequest != null) todo = mrequest.getParameter("todo");
                if ( (todo != null) && (todo.equalsIgnoreCase("upload")) )
                {
                    Hashtable files = mrequest.getFiles();
                    if ( (files != null) && (!files.isEmpty()) )
                    {
                        Player p = (Player)request.getSession(false).getAttribute("player");
                        UploadFile file = (UploadFile) files.get("uploadfile");
                        out.println(file.getContentType());
                        file.setFileName(new Date().getTime()+file.getFileName()+","+p.getName());
                        UploadBean uploadBean = new UploadBean();
                        uploadBean.setFolderstore(getServletContext().getRealPath("/images/avatar/"));
                        uploadBean.store(mrequest, "uploadfile");
                        String imgPath=getServletContext().getRealPath("/images/avatar/")+"/"+file.getFileName();
                        BufferedImage image = ImageIO.read(new File(imgPath));
                         if(image.getWidth()>150)
                            image = ComponentHouseResizer.resizeWidthTrick(image, 150);
                        if(image.getHeight()>150)
                            image = ComponentHouseResizer.resizeHeightTrick(image, 150);    
                         
                        ImageIO.write(image, "png", new File(imgPath.substring(0, imgPath.length()-4)+".png"));
                        try {
                            p.setAvatar(file.getFileName().substring(0, file.getFileName().length() - 4) + ".png");
                        } catch (Exception ex) {
                        }
                        File f= new File(imgPath);
                        f.delete();
                        getServletContext().getRequestDispatcher("/editpassword.jsp").forward(request, response);
                    }
                    else
                    {
                      out.println("<li>No uploaded files");
                    }
                }
            }
        } catch (UploadException ex) {
            Logger.getLogger(ChangeAvatar.class.getName()).log(Level.SEVERE, null, ex);
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
