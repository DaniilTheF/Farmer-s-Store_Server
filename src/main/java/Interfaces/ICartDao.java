package Interfaces;

import FarmEntity.*;

import java.util.ArrayList;
import java.util.Vector;

public interface ICartDao {
    public int insert(Cart cart);
    public int deleteCart(Cart cart);
    public ArrayList<Cart> getInfoAboutOrder(Cart cart);
    public ArrayList<Product> restoreProducts(Cart product);
    public ArrayList<Cart> getCart();

}
