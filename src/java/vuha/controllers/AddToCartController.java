/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuha.controllers;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vuha.dtos.CarDTO;
import vuha.dtos.CartDTO;
import vuha.dtos.CartDetailDTO;
import vuha.dtos.UserDTO;

/**
 *
 * @author Admin
 */
public class AddToCartController extends HttpServlet {

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
            String carID=request.getParameter("txtCarID");
            float price=Float.parseFloat(request.getParameter("txtPrice"));
            String txtimg=request.getParameter("txtimg");
            String txtName=request.getParameter("txtName");
            HttpSession session=request.getSession();
            UserDTO user= (UserDTO) session.getAttribute("USER");
            CartDTO cart=(CartDTO) session.getAttribute("CART");
            if(cart==null){
                cart=new CartDTO(null, user.getEmail(), 0, null, true, null);
            }
             ArrayList<CarDTO> cars = (ArrayList<CarDTO>) session.getAttribute("cars");
             CarDTO carOrder=null;
             for (CarDTO car : cars) {
                if(car.getCarID().equals(carID))
                    carOrder=car;
            }
            
            String rentalDateS=(String)session.getAttribute("txtRentalDate");
            String returnDateS=(String)session.getAttribute("txtReturnDate");
            Date retalDate=Date.valueOf(rentalDateS);
            Date returnDate=Date.valueOf(returnDateS);
            long totalDate=(returnDate.getTime()-retalDate.getTime())/(1000 * 60 * 60 * 24);
            
            cart.add(new CartDetailDTO(LocalDateTime.now().toString(), null,carOrder, 1, totalDate*price,rentalDateS, returnDateS, true));
            session.setAttribute("CART", cart);
            request.setAttribute("msg", "Add to cart Success");
        } catch (Exception e) {
            log("Error at AddToCartController: "+e.toString());
        }finally{
            request.getRequestDispatcher("search.jsp").forward(request, response);
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
