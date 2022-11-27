package Interfaces;

import FarmEntity.Orders;
import FarmEntity.Purchase;
import FarmEntity.Sent;

import java.util.ArrayList;

public interface IPurchaseDao {
    public int insertPurchasedOrder(Orders order);
    public ArrayList<Purchase> getPurchasedOrders(Purchase purchase);
    public Purchase getPurchasedOrderId(Orders purchase);
    public Purchase getPurchasedId(Purchase purchase);
    public Sent getCustomerId(Purchase purchase);
    public void sent(Purchase purchase);
    public ArrayList<Purchase> getPurchase();
}
