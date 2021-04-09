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
import javax.naming.NamingException;
import vuha.dtos.CarDTO;
import vuha.utils.DBUtil;

/**
 *
 * @author Admin
 */
public class CarDAO {

    public static ArrayList<CarDTO> getCars(String typeSearch, String dateSearch, String amount, String retalDate, String returnDate, int pageSize, int pageIndex) throws NamingException, SQLException {
        ArrayList<CarDTO> list = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "";
                if ("name".equals(typeSearch)) {
                    sql = "with x as (select ROW_NUMBER() over (order by  a.categoryID asc ,a.year desc) as r,a.carID,a.categoryID,a.carName,a.color,a.quantity-COALESCE(b.quantity, 0) as quantity,a.year,a.price,a.img\n"
                            + "from tblCars a LEFT OUTER join (select b.carID,SUM(b.quantity) as quantity\n"
                            + "from tblOrders a join tblOrderDetail b on a.orderID=b.orderID\n"
                            + "where a.status='1' and b.status='1' \n"
                            + "and( ? between b.checkIn and b.checkOut\n"
                            + "or ? between b.checkIn and b.checkOut \n"
                            + "or b.checkIn between ? and ?\n"
                            + "or b.checkOut between ? and ?)\n"
                            + "group by b.carID)  b on a.carID=b.carID\n"
                            + "where a.status='1' and (a.quantity-COALESCE(b.quantity, 0))>=? and a.carName like ?)\n"
                            + "select carID,categoryID,carName,color,quantity,year,price,img\n"
                            + "from x\n"
                            + "where r between ? and ?";
                } else {
                    sql = "with x as (select ROW_NUMBER() over (order by a.year desc ) as r,a.carID,a.categoryID,a.carName,a.color,a.quantity-COALESCE(b.quantity, 0) as quantity,a.year,a.price,a.img\n"
                            + "from tblCars a LEFT OUTER join (select b.carID,SUM(b.quantity) as quantity\n"
                            + "from tblOrders a join tblOrderDetail b on a.orderID=b.orderID\n"
                            + "where a.status='1' and b.status='1' \n"
                            + "and( ? between b.checkIn and b.checkOut\n"
                            + "or ? between b.checkIn and b.checkOut \n"
                            + "or b.checkIn between ? and ?\n"
                            + "or b.checkOut between ? and ?)\n"
                            + "group by b.carID)  b on a.carID=b.carID\n"
                            + "where a.status='1' and (a.quantity-COALESCE(b.quantity, 0))>=? and a.categoryID=? )\n"
                            + "select carID,categoryID,carName,color,quantity,year,price,img\n"
                            + "from x\n"
                            + "where r between ? and ?";
                }
                pst = cn.prepareStatement(sql);
                pst.setString(1, retalDate);
                pst.setString(2, returnDate);
                pst.setString(3, retalDate);
                pst.setString(4, returnDate);
                pst.setString(5, retalDate);
                pst.setString(6, returnDate);
                pst.setString(7, amount);
                if ("name".equals(typeSearch)) {
                    pst.setString(8, "%" + dateSearch + "%");
                } else {
                    pst.setString(8, dateSearch);
                }
                pst.setInt(9, (pageSize * pageIndex) - (pageSize - 1));
                pst.setInt(10, (pageSize * pageIndex));
                rs = pst.executeQuery();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String categoryID = rs.getString("categoryID");
                    String carName = rs.getString("carName");
                    String img = rs.getString("img");
                    String color = rs.getString("color");
                    int year = rs.getInt("year");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    CarDTO car = new CarDTO(carID, categoryID, carName, img, color, year, price, quantity);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(car);
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

    public static int countCar(String typeSearch, String dateSearch, String amount, String rentalDate, String returnDate) throws SQLException, NamingException {
        int result = 0;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "";
                if ("name".equals(typeSearch)) {
                    sql = " with x as (select ROW_NUMBER() over (order by a.carID asc ) as r,a.carID,a.categoryID,a.carName,a.color,a.quantity-COALESCE(b.quantity, 0) as quantity,a.year,a.price,a.img\n"
                            + "from tblCars a LEFT OUTER join (select b.carID,SUM(b.quantity) as quantity\n"
                            + "from tblOrders a join tblOrderDetail b on a.orderID=b.orderID\n"
                            + "where a.status='1' and b.status='1' \n"
                            + "and( ? between b.checkIn and b.checkOut\n"
                            + "or ? between b.checkIn and b.checkOut \n"
                            + "or b.checkIn between ? and ?\n"
                            + "or b.checkOut between ? and ?)\n"
                            + "group by b.carID)  b on a.carID=b.carID\n"
                            + "where a.status='1' and (a.quantity-COALESCE(b.quantity, 0))>=? and a.carName like ?)\n"
                            + "select COUNT(carID) as count\n"
                            + "from x";
                } else if ("category".equals(typeSearch)) {
                    sql = " with x as (select ROW_NUMBER() over (order by a.carID asc ) as r,a.carID,a.categoryID,a.carName,a.color,a.quantity-COALESCE(b.quantity, 0) as quantity,a.year,a.price,a.img\n"
                            + "from tblCars a LEFT OUTER join (select b.carID,SUM(b.quantity) as quantity\n"
                            + "from tblOrders a join tblOrderDetail b on a.orderID=b.orderID\n"
                            + "where a.status='1' and b.status='1' \n"
                            + "and( ? between b.checkIn and b.checkOut\n"
                            + "or ? between b.checkIn and b.checkOut \n"
                            + "or b.checkIn between ? and ?\n"
                            + "or b.checkOut between ? and ?)\n"
                            + "group by b.carID)  b on a.carID=b.carID\n"
                            + "where a.status='1' and (a.quantity-COALESCE(b.quantity, 0))>=? and a.categoryID=?)\n"
                            + "select COUNT(carID) as count\n"
                            + "from x";
                }
                pst = cn.prepareStatement(sql);
                pst.setString(1, rentalDate);
                pst.setString(2, returnDate);
                pst.setString(3, rentalDate);
                pst.setString(4, returnDate);
                pst.setString(5, rentalDate);
                pst.setString(6, returnDate);
                pst.setString(7, amount);
                if ("name".equals(typeSearch)) {
                    pst.setString(8, "%" + dateSearch + "%");
                } else {
                    pst.setString(8, dateSearch);
                }
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("count");
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

    public static boolean checkQuantity(String carID, String rentalDate, String returnDate, int quantity) throws SQLException, NamingException {
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "select ROW_NUMBER() over (order by  a.categoryID asc ,a.year desc) as r,a.carID,a.categoryID,a.carName,a.color,a.quantity-COALESCE(b.quantity, 0) as quantity,a.year,a.price,a.img\n"
                        + "from tblCars a LEFT OUTER join (select b.carID,SUM(b.quantity) as quantity\n"
                        + "from tblOrders a join tblOrderDetail b on a.orderID=b.orderID\n"
                        + "where a.status='1' and b.status='1' \n"
                        + "and( ? between b.checkIn and b.checkOut\n"
                        + "or ? between b.checkIn and b.checkOut \n"
                        + "or b.checkIn between ? and ?\n"
                        + "or b.checkOut between ? and ?)\n"
                        + "group by b.carID)  b on a.carID=b.carID\n"
                        + "where a.status='1' and (a.quantity-COALESCE(b.quantity, 0))>=? and a.carID=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, rentalDate);
                pst.setString(2, returnDate);
                pst.setString(3, rentalDate);
                pst.setString(4, returnDate);
                pst.setString(5, rentalDate);
                pst.setString(6, returnDate);
                pst.setInt(7, quantity);
                pst.setString(8, carID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = true;
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

    public static CarDTO getCar(String carID) throws SQLException, NamingException {
        CarDTO car = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "select carID,categoryID,carName,color,quantity,year,price,img,status\n"
                        + "from tblCars\n"
                        + "where carID=?";
                pst=cn.prepareStatement(sql);
                pst.setString(1, carID);
                rs=pst.executeQuery();
                if(rs.next()){
                    car=new CarDTO();
                    car.setCarID(carID);
                    car.setCarName(rs.getString("carName"));
                    car.setCategoryID(rs.getString("categoryID"));
                    car.setColor(rs.getString("color"));
                    car.setImg(rs.getString("img"));
                    car.setPrice(rs.getFloat("price"));
                    car.setQuantity(rs.getInt("quantity"));
                    car.setYear(rs.getInt("year"));
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
        return car;
    }
}
