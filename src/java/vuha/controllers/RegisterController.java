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
import vuha.daos.UserDAO;
import vuha.dtos.UserDTO;
import vuha.dtos.UserErrorDTO;

/**
 *
 * @author Admin
 */
public class RegisterController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String ERROR = "register.jsp";
    private static final String SUCCESS = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            UserErrorDTO userError = new UserErrorDTO("", "", "", "", "", "");
            boolean check = true;
            String email = request.getParameter("txtEmail").trim();
            if (email.isEmpty()) {
                userError.setEmailError("is Empty!!!!");
                check = false;
            } else {
                if (!email.matches("^[a-zA-Z][\\w-]*[a-zA-Z0-9]+@(([a-zA-Z0-9]+\\.[a-zA-Z0-9]+)|([a-zA-Z0-9]+\\.[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}))$")) {
                    userError.setEmailError("is incorrect Email format!!!!");
                    check = false;
                }
            }
            if(check){
                if(!UserDAO.checkAccount(email)){
                    check=false;
                    userError.setEmailError("The Email is Exist!!!!");
                }
            }
            String password = request.getParameter("txtPassword").trim();
            if (password.isEmpty()) {
                userError.setPasswordError("is Empty!!!!");
                check = false;
            } else {
                if (password.length() < 6 || password.length() > 30) {
                    userError.setPasswordError("Password must begin 6-30!!!!");
                    check = false;
                }
            }
            String confirm = request.getParameter("txtConfrim").trim();
            if (!confirm.equals(password)) {
                userError.setConfirmError("Password not match!!!!");
                check = false;
            }
            String name = request.getParameter("txtName").trim();
            if (name.isEmpty()) {
                userError.setNameError("is Empty!!!!");
                check = false;
            }
            String phone = request.getParameter("txtPhone").trim();
            if (phone.isEmpty()) {
                userError.setPhoneError("is Empty!!!!");
                check = false;
            } else {
                if (phone.length() < 10 || phone.length() > 11) {
                    userError.setPhoneError("Phone requires typing  10 or 11 !!!!");
                    check = false;
                }
            }
            String address = request.getParameter("txtAddress").trim();
            if (address.isEmpty()) {
                userError.setAddressError("is Empty!!!!");
                check = false;
            }

            if (check) {
                UserDTO user = new UserDTO(email, phone, name, address, password, check, "");
                UserDAO.registerUser(user);
                url=SUCCESS;
            } else {
                request.setAttribute("userError", userError);
            }

        } catch (Exception e) {
            log("Error at RegisterController: " + e.toString());
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
