package DAO;

import FarmEntity.*;
import Interfaces.ICustomerDao;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDao implements ICustomerDao {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int st;//status

        public int insertCustomer(Person customer) {
            con = ConnectionFactory.getConnection();
            try {
                String query = "insert into customer(personId) "
                        + "values(?)";
                ps = con.prepareStatement(query);
                ps.setInt(1, customer.getId());
                st = ps.executeUpdate();
                System.out.println("inserted customer " + st);
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

    public int addMoney(Customer customer) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE customer SET  money = money + ?, adress =? "
                    + "where personId=?;";
            ps = con.prepareStatement(query);
            ps.setFloat(1, customer.getMoney());
            ps.setString(2,customer.getAdress());
            ps.setInt(3, customer.getPersonId());
            st = ps.executeUpdate();
            System.out.println("inserted customer " + st);
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

    public int withdrawMoney(Orders order) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE customer SET  money = money - ? "
                    + "where personId=? and money > ?;";
            ps = con.prepareStatement(query);
            ps.setFloat(1, order.getTotal_price());
            ps.setInt(2, order.getPersonId());
            ps.setFloat(3, order.getTotal_price());
            st = ps.executeUpdate();
            System.out.println("inserted customer " + st);
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
    public int getCustomerId(Orders customer){
        Orders order = new Orders();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select id FROM customer "+
                    "where personId=?;";
            ps = con.prepareStatement(query);
            ps.setInt(1,customer.getPersonId());
            rs = ps.executeQuery();
            while (rs.next()) {
                order.setPersonId(rs.getInt("id"));
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
        return order.getPersonId();
    }

    public int getCustomerIdP(Purchase purchase){
        Orders order = new Orders();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select id FROM customer "+
                    "where personId=?;";
            ps = con.prepareStatement(query);
            ps.setInt(1,purchase.getCustomerId());
            rs = ps.executeQuery();
            while (rs.next()) {
                order.setPersonId(rs.getInt("id"));
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
        return order.getPersonId();
    }
    public ArrayList<Customer> getCustomerBalance(Customer customer){
        ArrayList<Customer> list = new ArrayList<>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select money FROM customer "+
                    "where personId=?;";
            ps = con.prepareStatement(query);
            ps.setInt(1,customer.getPersonId());
            rs = ps.executeQuery();
            while (rs.next()) {
                Customer customers = new Customer();
                customers.setMoney(rs.getFloat("money"));
                list.add(customers);
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
