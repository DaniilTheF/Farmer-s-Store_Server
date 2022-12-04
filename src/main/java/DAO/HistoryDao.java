package DAO;

import FarmEntity.History;
import FarmEntity.Orders;
import FarmEntity.Person;
import Interfaces.IHistoryDao;

import java.sql.*;
import java.util.ArrayList;

public class HistoryDao implements IHistoryDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status


    public int insert(Orders order) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into history(ordersId, personId) "
                    + "values(?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, order.getId());
            ps.setInt(2,order.getPersonId());
            st = ps.executeUpdate();
            System.out.println("inserted person " + st);
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
    public ArrayList<History> getAll(Person person){
        con = ConnectionFactory.getConnection();
        ArrayList<History> list = new ArrayList<>();
        try{
            String query = "select ordersId from history" +
                    " where personId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, person.getId());
            rs= ps.executeQuery();
            while(rs.next()){
                History history = new History();
                history.setOrdersId(rs.getInt("ordersId"));
                list.add(history);
            }
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
        return list;
    }

}
