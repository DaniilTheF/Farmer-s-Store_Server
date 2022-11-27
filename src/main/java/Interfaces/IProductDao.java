package Interfaces;

import DAO.ProductDao;
import FarmEntity.Person;
import FarmEntity.Product;

import java.util.ArrayList;

public interface IProductDao {
    public int insertProduct(Product product);
    public ArrayList<Product> fetchProducts();
    public int updateProduct(Product product);
    public int updateProduct2(Product product);
    public int updateProduct3(Product product);
    public int deleteProduct(Product product);
    public Product fetchByName(Product product);
}
