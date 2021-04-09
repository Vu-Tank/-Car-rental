/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuha.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.naming.NamingException;
import vuha.dtos.CartDTO;
import vuha.dtos.CartDetailDTO;
import vuha.utils.DBUtil;

/**
 *
 * @author Admin
 */
public class OrderDAO {

    public static void insertOder(CartDTO cart) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "insert into tblOrders(email,price,discountCode,bookingDate,status)\n"
                        + "values(?,?,?,GETDATE(),?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, cart.getEmail());
                pst.setFloat(2, cart.getPrice());
                pst.setString(3, cart.getDiscountCode());
                pst.setBoolean(4, true);
                pst.executeUpdate();
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static String getNewOrder(String email) throws SQLException, NamingException {
        String result = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "select top 1 orderID\n"
                        + "from tblOrders\n"
                        + "where email=?\n"
                        + "order by orderID desc";
                pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = rs.getString("orderID");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return result;
    }

    public static void insertOrderDetail(CartDetailDTO cartDetail) throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "insert into tblOrderDetail(orderID,carID,quantity,price,checkIn,checkOut,status)\n"
                        + "values(?,?,?,?,?,?,?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, cartDetail.getOrderID());
                pst.setString(2, cartDetail.getCar().getCarID());
                pst.setInt(3, cartDetail.getQuantity());
                pst.setFloat(4, cartDetail.getPrice());
                pst.setString(5, cartDetail.getCheckIn());
                pst.setString(6, cartDetail.getCheckOut());
                pst.setBoolean(7, true);
                pst.executeUpdate();
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static ArrayList<CartDTO> getOrderHistory(String typeSearch, String txtSearch, String txtOrderDate, String email) throws SQLException, NamingException {
        ArrayList<CartDTO> list = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "";
                if ("name".equals(typeSearch)) {
                    sql = "select  a.orderID,a.bookingDate,a.discountCode,a.email,a.price,a.status\n"
                            + "from tblOrders a join tblOrderDetail b on a.orderID=b.orderID join tblCars c on b.carID=c.carID\n"
                            + "where a.email=? and c.carName like ?\n"
                            + "order by a.orderID desc";
                } else {
                    sql = "select  a.orderID,a.bookingDate,a.discountCode,a.email,a.price,a.status\n"
                            + "from tblOrders a join tblOrderDetail b on a.orderID=b.orderID \n"
                            + "where a.email=? and a.bookingDate=?\n"
                            + "order by a.orderID desc";
                }
                pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                if ("name".equals(typeSearch)) {
                    pst.setString(2, "%" + txtSearch + "%");
                } else {
                    pst.setString(2, txtOrderDate);
                }
                rs = pst.executeQuery();
                while (rs.next()) {
                    CartDTO order = new CartDTO();
                    order.setOrderID(rs.getString("orderID"));
                    order.setEmail(rs.getString("email"));
                    order.setBookingDate(rs.getString("bookingDate"));
                    order.setPrice(rs.getFloat("price"));
                    order.setDiscountCode(rs.getString("discountCode"));
                    order.setStatus(rs.getBoolean("status"));
                    order.setCart(getCartDetail(order.getOrderID()));
                    if (list == null) {
                        list = new ArrayList<>();
                    }

                    if (list.size() == 0) {
                        list.add(order);
                    } else {
                        boolean check = true;
                        for (CartDTO cartDTO : list) {
                            if (cartDTO.getOrderID().equals(order.getOrderID())) {
                                check = false;
                            }
                        }
                        if (check) {
                            list.add(order);
                        }
                    }
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return list;
    }

    public static HashMap<String, CartDetailDTO> getCartDetail(String orderID) throws SQLException, NamingException {
        HashMap<String, CartDetailDTO> hash = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "select carID,checkIn,checkOut,orderDetailID,price,quantity,status\n"
                        + "from tblOrderDetail \n"
                        + "where orderID=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, orderID);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    CartDetailDTO cartDetail = new CartDetailDTO();
                    cartDetail.setCheckIn(rs.getString("checkIn"));
                    cartDetail.setCar(CarDAO.getCar(carID));
                    cartDetail.setCheckOut(rs.getString("checkOut"));
                    cartDetail.setOrderDetalID(rs.getString("orderDetailID"));
                    cartDetail.setOrderID(orderID);
                    cartDetail.setPrice(rs.getFloat("price"));
                    cartDetail.setQuantity(rs.getInt("quantity"));
                    cartDetail.setStatus(rs.getBoolean("status"));
                    if (hash == null) {
                        hash = new HashMap<>();
                    }
                    hash.put(cartDetail.getOrderDetalID(), cartDetail);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return hash;
    }

    public static void RemoveOrder(String orderID) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "update tblOrders\n"
                        + "set status=0\n"
                        + "where orderID=?";
                pst=cn.prepareStatement(sql);
                pst.setString(1, orderID);
                pst.executeUpdate();
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }
}
