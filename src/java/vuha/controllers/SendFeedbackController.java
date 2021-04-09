/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuha.controllers;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vuha.daos.FeedbackDAO;
import vuha.dtos.CartDTO;
import vuha.dtos.CartDetailDTO;
import vuha.dtos.FeedbackDTO;

/**
 *
 * @author Admin
 */
public class SendFeedbackController extends HttpServlet {

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
            String txtRanting = request.getParameter("txtRanting");
            HttpSession session = request.getSession();
            CartDTO detail = (CartDTO) session.getAttribute("detail");
            String txtOrderDeltailID = request.getParameter("txtOrderDeltailID");
            for (CartDetailDTO value : detail.getCart().values()) {
                if (value.getOrderDetalID().equals(txtOrderDeltailID) && value.isStatus()) {
                    Date dateNow = Date.valueOf(java.time.LocalDate.now().toString());
                    Date checkIn = Date.valueOf(value.getCheckIn());
                    if (dateNow.after(checkIn) || dateNow.compareTo(checkIn) == 0) {
                        FeedbackDTO fb = new FeedbackDTO();
                        fb.setOrderDetailID(txtOrderDeltailID);
                        fb.setValue(Integer.parseInt(txtRanting));
                        FeedbackDAO.insertFeedback(fb);
                        value.setFeedback(fb);
                        value.setStatus(false);
                        request.setAttribute("msg", "Send Feedback success.");
                    }else{
                        request.setAttribute("msg", "only feedback rented!!!!");
                    }
                }
            }
            session.setAttribute("detail", detail);
        } catch (Exception e) {
            log("Error at SendFeedbackController: " + e.toString());
        } finally {
            request.getRequestDispatcher("viewdetailhistrory.jsp").forward(request, response);
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
