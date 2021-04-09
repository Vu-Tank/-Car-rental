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
import vuha.dtos.FeedbackDTO;
import vuha.utils.DBUtil;

/**
 *
 * @author Admin
 */
public class FeedbackDAO {

    public static void insertFeedback(FeedbackDTO fb) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "insert into tblFeedbacks(orderDetailID,value)\n"
                        + "values(?,?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, fb.getOrderDetailID());
                pst.setInt(2, fb.getValue());
                int result = pst.executeUpdate();
                if (result == 1) {
                    sql = "update tblOrderDetail\n"
                            + "set status=0\n"
                            + "where orderDetailID=?";
                    pst=cn.prepareStatement(sql);
                    pst.setString(1, fb.getOrderDetailID());
                    pst.executeUpdate();
                }
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

    public static FeedbackDTO getFeedbacks(String orderDetailID) throws SQLException, NamingException {
        FeedbackDTO fb = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "select feedbackID,value\n"
                        + "from tblFeedbacks\n"
                        + "where orderDetailID=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, orderDetailID);
                rs=pst.executeQuery();
                if (rs.next()) {
                    fb=new FeedbackDTO();
                    fb.setFeedbackID(rs.getString("feedbackID"));
                    fb.setValue(rs.getInt("value"));
                    fb.setOrderDetailID(orderDetailID);
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
        return fb;
    }
}
