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
import vuha.dtos.DiscountDTO;
import vuha.utils.DBUtil;

/**
 *
 * @author Admin
 */
public class DiscountDAO {

    public static DiscountDTO getDiscount(String code) throws SQLException, NamingException {
        DiscountDTO result = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "select value \n"
                        + "from tblDiscounts\n"
                        + "where GETDATE() between beginDate and endDate and discountCode=?";
                pst=cn.prepareStatement(sql);
                pst.setString(1, code);
                rs=pst.executeQuery();
                if(rs.next()){
                    float value =rs.getFloat("value");
                    result=new DiscountDTO(code, value);
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
}
