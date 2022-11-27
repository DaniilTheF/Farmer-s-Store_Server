package DAO;

import FarmEntity.Earned;
import FarmEntity.Orders;
import FarmEntity.Purchase;
import Interfaces.IEarnedDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EarnedDao implements IEarnedDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status


    public int updateEarned(Orders order){
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE earned SET earn_money= earn_money + ? "
                    + "WHERE id=1 ";
            ps = con.prepareStatement(query);
            ps.setFloat(1, order.getTotal_price());
            st = ps.executeUpdate();;
        } catch (Exception e) {
            st = -1;
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public ArrayList<Earned> getEarned(){
        con = ConnectionFactory.getConnection();
        ArrayList<Earned> list = new ArrayList<>();
        try {
            String query = "select * FROM earned;";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Earned earned = new Earned();
                earned.setId(rs.getInt("id"));
                earned.setEarned(rs.getInt("earn_money"));
                list.add(earned);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }
}
