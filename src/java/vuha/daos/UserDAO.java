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
import javax.naming.NamingException;
import vuha.dtos.UserDTO;
import vuha.utils.DBUtil;

/**
 *
 * @author Admin
 */
public class UserDAO {

    public static UserDTO checkLogin(String email, String password) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        UserDTO user = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "select name,address,status,phone,verificationCode  \n"
                        + "from tblUsers \n"
                        + "where email=? and password=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String address = rs.getString("address");
                    String name = rs.getString("name");
                    boolean status = rs.getBoolean("status");
                    String phone = rs.getString("phone");
                    String verificationCode=rs.getString("verificationCode");
                    user = new UserDTO(email, phone, name, address, "", status, verificationCode);
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
        return user;
    }
    
    public static boolean checkAccount(String email) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean results = true;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "select name\n"
                        + "from tblUsers \n"
                        + "where email=? ";
                pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                rs = pst.executeQuery();
                if (rs.next()) {
                    results=false;
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
        return results;
    }

    public static void registerUser(UserDTO user) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "insert into tblUsers(email,password,name,address,createDate,phone,status,verificationCode)\n"
                        + "values(?,?,?,?,GETDATE(),?,?,RAND()*10000)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, user.getEmail());
                pst.setString(2, user.getPassword());
                pst.setString(3, user.getName());
                pst.setString(4, user.getAddress());
                pst.setString(5, user.getPhone());
                pst.setBoolean(6, !user.isStatus());
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

    public static String getVerificationCode(String email) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String verificationCode = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "select verificationCode  \n"
                        + "from tblUsers \n"
                        + "where email=? ";
                pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                rs = pst.executeQuery();
                if (rs.next()) {
                    verificationCode = rs.getString("verificationCode");
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
        return verificationCode;
    }

    public static void updateEmail(String emailOld, String emailNew) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "update tblUsers\n"
                        + "set email=?\n"
                        + "where email=?";
                pst=cn.prepareStatement(sql);
                pst.setString(1, emailNew);
                pst.setString(2, emailOld);
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
    public static void updateStatus(String email) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "update tblUsers\n"
                        + "set status=1\n"
                        + "where email=?";
                pst=cn.prepareStatement(sql);
                pst.setString(1, email);
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
