package DAO;

import FarmEntity.Cart;
import FarmEntity.Person;
import FarmEntity.Product;
import Interfaces.IProductDao;

import java.sql.*;
import java.util.ArrayList;

public class ProductDao implements IProductDao {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status


    public int insertProduct(Product product) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into product(name,quantity,cost) "
                    + "values(?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setInt(2, product.getQuantity());
            ps.setFloat(3, product.getCost());
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
    public ArrayList<Product> fetchProducts() {
        ArrayList<Product> productArrayList = new ArrayList<>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from product";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setQuantity(rs.getInt("quantity"));
                product.setCost(rs.getInt("cost"));
                productArrayList.add(product);
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
        return productArrayList;
    }
    public int updateProduct(Product product){
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE product SET quantity =? "
                    + "WHERE id=?;";
            ps = con.prepareStatement(query);
            ps.setInt(1,product.getQuantity());
            ps.setInt(2,product.getId());
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
    public int updateProduct3(Product product){
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE product SET name =?, quantity =?, cost=? "
                    + "WHERE id=?;";
            ps = con.prepareStatement(query);
            ps.setString(1,product.getName());
            ps.setInt(2,product.getQuantity());
            ps.setFloat(3,product.getCost());
            ps.setInt(4,product.getId());
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
    public int deleteProduct(Product product){
        con = ConnectionFactory.getConnection();
        try {
            String query = "delete from product " +
                    "where id=?;";
            ps = con.prepareStatement(query);
            ps.setInt(1,product.getId());
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
    public Product fetchByName(Product product){
        Product products = new Product();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from product" +
                    " where name=?;";
            ps = con.prepareStatement(query);
            ps.setString(1,product.getName());
            rs = ps.executeQuery();
            while (rs.next()) {
                products.setId(rs.getInt("id"));
                products.setName(rs.getString("name"));
                products.setQuantity(rs.getInt("quantity"));
                products.setCost(rs.getInt("cost"));
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
        return products;
    }
    public int updateProduct2(Product product){
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE product SET quantity = quantity + ? "
                    + "WHERE id=?;";
            ps = con.prepareStatement(query);
            ps.setInt(1,product.getQuantity());
            ps.setInt(2,product.getId());
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
}
