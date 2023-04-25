/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import context.DBContext;
import dao.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

/**
 *
 * @author ADMIN
 */
public class SignupController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String rePass = request.getParameter("repass");
        
        if(!pass.equals(rePass)){
            request.setAttribute("mess", "Mật khẩu và Nhập lại mật khẩu phải giống nhau!");
            request.setAttribute("toggle", "signup-form");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            
        } else{
            DAO dao = new DAO();
            Account a = dao.checkAccountExist(user);
            Connection conn = DBContext.getConnection();
            if(a==null){
                boolean check = dao.signup(user, pass, conn);
                if(check){
                    Cookie usernameCookie = new Cookie("user", user);
                    response.addCookie(usernameCookie);
                    response.sendRedirect("home");
                }
                else{
                    request.setAttribute("status", "Signup Failed");
                    request.setAttribute("toggle", "signup-form");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
            } else{
                request.setAttribute("status", "Signup Failed");
                request.setAttribute("toggle", "signup-form");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }
        

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
