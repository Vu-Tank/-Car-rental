/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuha.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vuha.daos.OrderDAO;
import vuha.dtos.CartDTO;
import vuha.dtos.UserDTO;

/**
 *
 * @author Admin
 */
public class SearchHistoryController extends HttpServlet {

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
        try {
            boolean check = true;
            HttpSession session = request.getSession();
            UserDTO user= (UserDTO) session.getAttribute("USER");
            String typeSearchH = request.getParameter("typeSearchH");
            if (typeSearchH == null) {
                typeSearchH = (String) session.getAttribute("typeSearchH");
                if (typeSearchH == null) {
                    typeSearchH = "name";
                }
            }
            session.setAttribute("typeSearchH", typeSearchH);
            typeSearchH = (String) session.getAttribute("typeSearchH");

            String dataSearch = request.getParameter("txtSearch");
            if (dataSearch == null) {
                dataSearch = (String) session.getAttribute("txtSearch");
                if (dataSearch == null) {
                    dataSearch = "";
                }
            }
            session.setAttribute("txtSearch", dataSearch);
            dataSearch = (String) session.getAttribute("txtSearch");
            String txtOrderDate = request.getParameter("txtOrderDate");
            if (txtOrderDate == null ) {
                txtOrderDate = (String) session.getAttribute("txtOrderDate");
                if (txtOrderDate == null ) {
                    txtOrderDate = java.time.LocalDate.now().toString();
                }
            }
            session.setAttribute("txtOrderDate", txtOrderDate);
            txtOrderDate = (String) session.getAttribute("txtOrderDate");
            
            ArrayList<CartDTO> carts=OrderDAO.getOrderHistory(typeSearchH, dataSearch, txtOrderDate, user.getEmail());
            session.setAttribute("history", carts);
            if(carts!=null){
                session.setAttribute("result", carts.size());
            }else{
                session.setAttribute("result", 0);
            }
        } catch (Exception e) {
            log("Error at SearchHistoryController: " + e.toString());
        } finally {
            request.getRequestDispatcher("history.jsp").forward(request, response);
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
