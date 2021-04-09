/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuha.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vuha.daos.UserDAO;
import vuha.dtos.UserDTO;
import vuha.utils.VerifyUtils;

/**
 *
 * @author Admin
 */
public class LoginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String ERROR = "login.jsp";
    private static final String SUCCESS = "search.jsp";
    private static final String SUCCESSERROR = "verification.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            boolean check = true;
            String email = request.getParameter("txtEmail").trim();
            if (email.isEmpty()) {
                request.setAttribute("errorEmail", "is Empty!!!!");
                check = false;
            }
            String password = request.getParameter("txtPassword").trim();
            if (password.isEmpty()) {
                request.setAttribute("errorPassword", "is Empty!!!!");
                check = false;
            }
            if (check) {
                String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
                check = VerifyUtils.verify(gRecaptchaResponse);
                if (check) {
                    UserDTO user = UserDAO.checkLogin(email, password);
                    if (user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("USER", user);
                        if (user.isStatus()) {
                            url = SUCCESS;
                        } else {
                            url = SUCCESSERROR;
                        }
                    } else {
                        request.setAttribute("message", "Email or Password is NOT correct!!!!");
                    }
                }else{
                    request.setAttribute("message", "Miss Captcha!");
                }
            }
        } catch (Exception e) {
            log("Error at LoginController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
