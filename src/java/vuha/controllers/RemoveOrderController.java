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
import vuha.daos.OrderDAO;
import vuha.dtos.CartDTO;
import vuha.dtos.CartDetailDTO;

/**
 *
 * @author Admin
 */
public class RemoveOrderController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String ERROR="viewdetailhistrory.jsp";
    private static final String SUCCESS="SearchHistoryController";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url=ERROR;
        try {
            String txtOrderID=request.getParameter("txtOrderID");
            HttpSession session= request.getSession();
            CartDTO order= (CartDTO) session.getAttribute("detail");
            if(order!=null){
                boolean check=true;
                for (CartDetailDTO value : order.getCart().values()) {
                    Date dateNow=Date.valueOf(java.time.LocalDate.now().toString());
                    Date checkIn=Date.valueOf(value.getCheckIn());
                    if(dateNow.after(checkIn)||dateNow.compareTo(checkIn)==0){
                        check=false;
                    }
                }
                if(check){
                    OrderDAO.RemoveOrder(txtOrderID);
                    request.setAttribute("Romove", "Remove Success.");
                    url=SUCCESS;
                }else{
                    request.setAttribute("error", "Can not delete order available already To Use!!!!!");
                }
            }
        } catch (Exception e) {
            log("Error at RemoveOrderController"+e.toString());
        }finally{
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
