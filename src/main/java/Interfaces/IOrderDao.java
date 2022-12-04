package Interfaces;

import FarmEntity.*;

import java.util.ArrayList;
import java.util.Vector;

public interface IOrderDao {
    public int insertOrder(Cart cart);
    public Orders getOrderId();

    public int deleteOrder(Cart order);
    public int updateOrder(Orders order);
    public ArrayList<Orders> getAllOrder();
   public Vector<Orders> getAllOrders(Cart cart);
    public Orders getOrderPrice(Orders orders);
}
