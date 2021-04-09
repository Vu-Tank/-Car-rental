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
import vuha.daos.CarDAO;
import vuha.daos.OrderDAO;
import vuha.dtos.CartDTO;
import vuha.dtos.CartDetailDTO;
import vuha.dtos.DiscountDTO;

/**
 *
 * @author Admin
 */
public class CheckOutController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static final String ERROR="cart.jsp";
    public static final String SUCCESS="SearchController";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url=ERROR;
        try {
            HttpSession session=request.getSession();
            CartDTO cart=(CartDTO) session.getAttribute("CART");
            boolean check=true;
            float total=0;
            for (CartDetailDTO value : cart.getCart().values()) {
                total+=value.getPrice();
                if(!CarDAO.checkQuantity(value.getCar().getCarID(), value.getCheckIn(), value.getCheckOut(), value.getQuantity())){
                    check=false;
                    request.setAttribute("errorQuantity", value.getCar().getCarName()+"not enough car to rent!!!!");
                    break;
                } 
            }
            if(check){
                if(session.getAttribute("discount")!=null){
                    DiscountDTO discount= (DiscountDTO) session.getAttribute("discount");
                    total=total-total*discount.getValue()/100;
                    cart.setDiscountCode(discount.getDiscountCode());
                }else{
                    cart.setDiscountCode("N/A");
                }
                cart.setPrice(total);
                OrderDAO.insertOder(cart);
                for (CartDetailDTO value : cart.getCart().values()) {
                    value.setOrderID(OrderDAO.getNewOrder(cart.getEmail()));
                    OrderDAO.insertOrderDetail(value);
                }
                request.setAttribute("msg", "Check Out Succes.");
                session.setAttribute("CART", null);
                url=SUCCESS;
            }
        } catch (Exception e) {
            log("Error at CheckOutController: "+e.toString());
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
