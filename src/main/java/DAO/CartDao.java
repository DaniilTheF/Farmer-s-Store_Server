package DAO;

import FarmEntity.Cart;
import FarmEntity.Orders;
import FarmEntity.Person;
import FarmEntity.Product;
import Interfaces.ICartDao;
import Interfaces.IPersonDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class CartDao implements ICartDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status

    public int insert(Cart cart) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into cart(ordersId,personsId,productId,amount,price) "
                    + "values(?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, cart.getOrdersId());
            ps.setInt(2, cart.getPersonId());
            ps.setInt(3, cart.getProductId());
            ps.setInt(4, cart.getAmount());
            ps.setFloat(5, cart.getPrice());
            st = ps.executeUpdate();
            System.out.println("inserted student " + st);
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
    public int deleteCart(Cart cart) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "delete from cart where ordersId=? and personsId=? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, cart.getOrdersId());
            ps.setInt(2, cart.getPersonId());
            st = ps.executeUpdate();
            System.out.println("deleted cart " + st);
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
    public ArrayList<Product> restoreProducts(Cart product){
        ArrayList<Product> cartList = new ArrayList();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT productId, amount from cart"+
                    " where ordersId=? and personsId=?;";
            ps = con.prepareStatement(query);
            ps.setInt(1,product.getOrdersId());
            ps.setInt(2,product.getPersonId());
            rs = ps.executeQuery();
            while (rs.next()) {
                Product prod = new Product();
                prod.setId(rs.getInt("productId"));
                prod.setQuantity(rs.getInt("amount"));
                cartList.add(prod);
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
        return cartList;
    }
public ArrayList<Cart> getInfoAboutOrder(Cart cart){
    ArrayList<Cart> cartList = new ArrayList();
    con = ConnectionFactory.getConnection();
    try {
        String query = "select p.name as productName, cart.amount as amount, cart.price from cart" +
                "    INNER JOIN orders o on o.id = cart.ordersId" +
                "    INNER JOIN person ps on ps.id =  cart.personsId" +
                "    INNER JOIN product p on p.id = cart.productId" +
                "        where cart.ordersId=? and cart.personsId=?;";
        ps = con.prepareStatement(query);
        ps.setInt(1,cart.getOrdersId());
        ps.setInt(2,cart.getPersonId());
        rs = ps.executeQuery();
        while (rs.next()) {
            Cart cart1 = new Cart();
            cart1.setProductName(rs.getString("productName"));
            cart1.setAmount(rs.getInt("amount"));
            cart1.setPrice(rs.getFloat("price"));
            cartList.add(cart1);
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
    return cartList;
}
    public ArrayList<Cart> getCart(){
        ArrayList<Cart> cartList = new ArrayList();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select p.name as productName, cart.amount as amount from cart" +
                    "    INNER JOIN orders o on o.id = cart.ordersId" +
                    "    INNER JOIN person ps on ps.id =  cart.personsId" +
                    "    INNER JOIN product p on p.id = cart.productId";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cart cart1 = new Cart();
                cart1.setProductName(rs.getString("productName"));
                cart1.setAmount(rs.getInt("amount"));
                cartList.add(cart1);
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
        return cartList;
    }
}
