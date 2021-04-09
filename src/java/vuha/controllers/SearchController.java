/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuha.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vuha.daos.CarDAO;
import vuha.dtos.CarDTO;

/**
 *
 * @author Admin
 */
public class SearchController extends HttpServlet {

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
            String typeSearch = request.getParameter("typeSearch");
            if (typeSearch == null) {
                typeSearch = (String) session.getAttribute("typeSearch");
                if (typeSearch == null) {
                    typeSearch = "name";
                }
            }
            session.setAttribute("typeSearch", typeSearch);
            typeSearch = (String) session.getAttribute("typeSearch");

            String dataSearch;
            if ("name".equals(typeSearch)) {
                dataSearch = request.getParameter("dataSearch");
                if (dataSearch == null) {
                    dataSearch = (String) session.getAttribute("dataSearch");
                    if (dataSearch == null) {
                        dataSearch = "";
                    }
                }
                session.setAttribute("dataSearch", dataSearch);
                dataSearch = (String) session.getAttribute("dataSearch");
            } else {
                dataSearch = request.getParameter("dataSearchCbx");
                if (dataSearch == null) {
                    dataSearch = (String) session.getAttribute("dataSearchCbx");
                    if (dataSearch == null) {
                        dataSearch = "1";
                    }
                }
                session.setAttribute("dataSearchCbx", dataSearch);
                dataSearch = (String) session.getAttribute("dataSearchCbx");
            }

            String txtAmount = request.getParameter("txtAmount");
            if (txtAmount == null) {
                txtAmount = (String) session.getAttribute("txtAmount");
                if (txtAmount == null) {
                    txtAmount = "1";
                }
            }
            session.setAttribute("txtAmount", txtAmount);
            txtAmount = (String) session.getAttribute("txtAmount");

            Date retalDate = null;
            Date returnDate = null;
            String txtRentalDate = request.getParameter("txtRentalDate");
            String txtReturnDate = request.getParameter("txtReturnDate");
            if (txtRentalDate == null) {
                txtRentalDate = (String) session.getAttribute("txtRentalDate");
                if (txtRentalDate == null) {
                    txtRentalDate = java.time.LocalDate.now().toString();
                }
            }
            session.setAttribute("txtRentalDate", txtRentalDate);
            txtRentalDate = (String) session.getAttribute("txtRentalDate");

            if (txtReturnDate == null) {
                txtReturnDate = (String) session.getAttribute("txtReturnDate");
                if (txtReturnDate == null) {
                    txtReturnDate = java.time.LocalDate.now().plusDays(1).toString();
                }
            }
            session.setAttribute("txtReturnDate", txtReturnDate);
            txtReturnDate = (String) session.getAttribute("txtReturnDate");

            retalDate = Date.valueOf(txtRentalDate);
            returnDate = Date.valueOf(txtReturnDate);
            if (retalDate.after(returnDate)||retalDate.compareTo(returnDate)==0) {
                check = false;
                request.setAttribute("dateError", "Return date > retal date!!!!");
            }

            int count = CarDAO.countCar(typeSearch, dataSearch, txtAmount, txtRentalDate, txtReturnDate);
            int pageSize = 15;
            int endPage = 0;
            endPage = (int) Math.ceil((float) count / pageSize);
            session.setAttribute("endPage", endPage);

            String pageIndexS = request.getParameter("pageIndex");
            if (pageIndexS == null) {
                pageIndexS="1";
            }else{
                if(pageIndexS.trim().isEmpty())
                    pageIndexS="1";
            }
            int pageIndex = Integer.parseInt(pageIndexS);
            ArrayList<CarDTO> cars = CarDAO.getCars(typeSearch, dataSearch, txtAmount, txtRentalDate, txtReturnDate, pageSize, pageIndex);
            session.setAttribute("cars", cars);
            session.setAttribute("result", count);
            session.setAttribute("dateNow", java.time.LocalDate.now());
            session.setAttribute("dateNext", java.time.LocalDate.now().plusDays(1));
        } catch (Exception e) {
            log("Error at SearchController: " + e.getMessage());
        } finally {
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
