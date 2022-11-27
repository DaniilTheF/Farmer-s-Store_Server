package DAO;

import FarmEntity.*;
import Interfaces.IPurchaseDao;

import java.sql.*;
import java.util.ArrayList;

public class PurchaseDao implements IPurchaseDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status

    public int insertPurchasedOrder(Orders order) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into purchase    (ordersId,customerId,sent) "
                    + "values(?,?,false)";
            ps = con.prepareStatement(query);
            ps.setInt(1, order.getId());
            ps.setInt(2, order.getPersonId());
            st = ps.executeUpdate();
            System.out.println("inserted purchase " + st);
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

    public ArrayList<Purchase> getPurchasedOrders(Purchase purchase) {
        ArrayList<Purchase> list = new ArrayList<>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select ordersId, sent FROM purchase " +
                    "where customerId=?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, purchase.getCustomerId());
            rs = ps.executeQuery();
            while (rs.next()) {
                Purchase purchases = new Purchase();
                purchases.setOrderId(rs.getInt("ordersId"));
                purchases.setSent(rs.getBoolean("sent"));
                list.add(purchases);
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

    public Purchase getPurchasedOrderId(Orders purchase) {
        Purchase purchases = new Purchase();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select ordersId FROM purchase " +
                    "where ordersId=?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, purchase.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                purchases.setOrderId(rs.getInt("ordersId"));
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
        return purchases;
    }

    public Purchase getPurchasedId(Purchase purchase) {
        Purchase purchases = new Purchase();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select id FROM purchase " +
                    "where id=?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, purchase.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                purchases.setId(rs.getInt("id"));
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
        return purchases;
    }

    public Sent getCustomerId(Purchase purchase) {
        Sent sent = new Sent();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT purchase.id as id, c.adress as adress FROM purchase " +
                    "INNER JOIN orders o on purchase.ordersId = o.id " +
                    "INNER JOIN customer c on purchase.customerId = c.id " +
                    "where purchase.id=?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, purchase.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                sent.setPurchaseId(rs.getInt("id"));
                sent.setAdress(rs.getString("adress"));
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

    public void sent(Purchase purchase) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE purchase SET sent= true" +
                    " where id=?";
            ps = con.prepareStatement(query);
            ps.setLong(1, purchase.getId());
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
    public ArrayList<Purchase> getPurchase(){
        ArrayList<Purchase> list = new ArrayList<>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select id,ordersId,sent FROM purchase ";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Purchase purchases = new Purchase();
                purchases.setId(rs.getInt("id"));
                purchases.setOrderId(rs.getInt("ordersId"));
                purchases.setSent(rs.getBoolean("sent"));
                list.add(purchases);
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
