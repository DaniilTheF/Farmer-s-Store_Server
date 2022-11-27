package Interfaces;

import FarmEntity.Customer;
import FarmEntity.Orders;
import FarmEntity.Person;
import FarmEntity.Purchase;

import java.util.ArrayList;

public interface ICustomerDao {
    public int addMoney(Customer customer);
    public int insertCustomer(Person customer);
    public int withdrawMoney(Orders order);
    public int getCustomerId(Orders customer);
    public int getCustomerIdP(Purchase purchase);

    public ArrayList<Customer> getCustomerBalance(Customer customer);
}
