/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuha.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vuha.daos.CarDAO;
import vuha.dtos.CarDTO;
import vuha.dtos.CartDTO;
import vuha.dtos.CartDetailDTO;

/**
 *
 * @author Admin
 */
public class UpdateCartController extends HttpServlet {

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
            String quantity = request.getParameter("txtQuantity");
            String txtCarID = request.getParameter("txtCarID");
            String txtRentalDate = request.getParameter("txtRentalDate");
            String txtReturnDate = request.getParameter("txtReturnDate");
            String orderDetalID = request.getParameter("txtOrderDetalID");
            boolean check = true;
            if (txtRentalDate.isEmpty()) {
                check = false;
                request.setAttribute("rentalError", "Select Rental Date!!!!");
            }
            if (txtReturnDate.isEmpty()) {
                check = false;
                request.setAttribute("returnError", "Select Return Date!!!!");
            }
            if (check) {
                Date retalDate = Date.valueOf(txtRentalDate);
                Date returnDate = Date.valueOf(txtReturnDate);
                if (retalDate.after(returnDate)) {
                    check = false;
                    request.setAttribute("returnError", "Return date > retal date!!!!");
                }
            }

            if (!CarDAO.checkQuantity(txtCarID, txtRentalDate, txtReturnDate, Integer.parseInt(quantity))) {
                check = false;
                request.setAttribute("errorQuantity", "not enough car to rent!!!!");
            }
            if (!check) {
                request.setAttribute("errorID", orderDetalID);
            } else {
                HttpSession session = request.getSession();
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                ArrayList<CarDTO> cars = (ArrayList<CarDTO>) session.getAttribute("cars");
                CarDTO carOrder = null;
                for (CarDTO car : cars) {
                    if (car.getCarID().equals(txtCarID)) {
                        carOrder = car;
                    }
                }
                Date retalDate = Date.valueOf(txtRentalDate);
                Date returnDate = Date.valueOf(txtReturnDate);
                long totalDate = (returnDate.getTime() - retalDate.getTime()) / (1000 * 60 * 60 * 24);
                cart.update(new CartDetailDTO(orderDetalID, null, carOrder, Integer.parseInt(quantity), totalDate*carOrder.getPrice()*Integer.parseInt(quantity), txtRentalDate, txtReturnDate, check));
                session.setAttribute("CART", cart);
                request.setAttribute("msg", "Update success.");
            }
        } catch (Exception e) {
            log("Error at UpdateCartController: " + e.toString());
        } finally {
            request.getRequestDispatcher("cart.jsp").forward(request, response);
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
