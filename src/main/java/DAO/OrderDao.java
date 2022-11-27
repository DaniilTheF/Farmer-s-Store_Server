package DAO;

import FarmEntity.Cart;
import FarmEntity.Orders;
import FarmEntity.Person;
import Interfaces.IOrderDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class OrderDao implements IOrderDao {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int st;
    public int insertOrder(Cart cart) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into orders(total_price,personId) "
                    + "values(?,?)";
            ps = con.prepareStatement(query);
            ps.setFloat(1, cart.getTotal_price());
            ps.setInt(2, cart.getPersonId());
            st = ps.executeUpdate();
            System.out.println("inserted orders " + st);
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
    public int deleteOrder(Cart order) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "delete from orders where id=? and personId=? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, order.getOrdersId());
            ps.setInt(2,order.getPersonId());
            st = ps.executeUpdate();
            System.out.println("deleted order " + st);
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
    public Orders getOrderId(){
        Orders order = new Orders();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select id FROM orders ORDER BY id DESC LIMIT 1;";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                order.setId(rs.getInt("id"));
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
        return order;
    }
    public Orders getOrderPrice(Orders orders){
        Orders order = new Orders();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * FROM orders "+
                    "where id=? and personId=?;";
            ps = con.prepareStatement(query);
            ps.setInt(1,orders.getId());
            ps.setInt(2,orders.getPersonId());
            rs = ps.executeQuery();
            while (rs.next()) {
                order.setId(rs.getInt("id"));
                order.setTotal_price(rs.getFloat("total_price"));
                order.setPersonId(rs.getInt("personId"));
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
        return order;
    }
    public int updateOrder(Orders order){
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE farm.orders SET total_price=?"
                    + "WHERE id=? ";
            ps = con.prepareStatement(query);
            ps.setFloat(1, order.getTotal_price());
            ps.setInt(2, order.getId());
            st = ps.executeUpdate();
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
    public Vector<Orders> getAllOrders(Cart cart){
        Vector<Orders> ordersList = new Vector();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select id, total_price from orders" +
                    " where personId =?;";
            ps = con.prepareStatement(query);
            ps.setInt(1,cart.getPersonId());
            rs = ps.executeQuery();
            while (rs.next()) {
                Orders orders = new Orders();
                orders.setId(rs.getInt("id"));
                orders.setTotal_price(rs.getFloat("total_price"));
                ordersList.add(orders);
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
        return ordersList;
    }
}
