package DAO;


import FarmEntity.*;

import Interfaces.ISentDao;

import java.sql.*;

public class SentDao implements ISentDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status
    public Sent isSent(Purchase order){
        Sent sent = new Sent();
            con = ConnectionFactory.getConnection();
            try {
                String query = "select * from sent" +
                        " where purchaseId=?;";
                ps = con.prepareStatement(query);
                ps.setInt(1,order.getId());
                rs = ps.executeQuery();
                while (rs.next()) {
                    sent.setId(rs.getInt("id"));
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
        return sent;
    }
    public int insertSent(Sent sent){
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into sent (purchaseId,adress) "
                    + "values(?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, sent.getPurchaseId());
            ps.setString(2, sent.getAdress());
            st = ps.executeUpdate();
            System.out.println("inserted sent " + st);
        } catch (SQLSyntaxErrorException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
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
}
