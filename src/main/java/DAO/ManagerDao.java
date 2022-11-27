package DAO;

import FarmEntity.Person;
import FarmEntity.Purchase;
import Interfaces.IManagerDao;

import java.sql.*;

public class ManagerDao implements IManagerDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status

    public int insertManager(Person manager) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into manager(personId) "
                    + "values(?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, manager.getId());
            st = ps.executeUpdate();
            System.out.println("inserted manager " + st);
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
    public int deleteManager(Person person) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "delete from manager where personId=?";
            ps = con.prepareStatement(query);
            ps.setLong(1, person.getId());
            st = ps.executeUpdate();
            System.out.println("deleted manager " + st);
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
    public void increaseCount(Purchase order){
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE manager SET count_of_order= count_of_order+1" +
                    " where id=?";
            ps = con.prepareStatement(query);
            ps.setLong(1, order.getCustomerId());
            st = ps.executeUpdate();
            System.out.println("deleted manager " + st);
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
    }
}
