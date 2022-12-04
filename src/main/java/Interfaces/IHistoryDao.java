package Interfaces;

import FarmEntity.History;
import FarmEntity.Orders;
import FarmEntity.Person;

import java.util.ArrayList;

public interface IHistoryDao {
    public int insert(Orders order);
    public ArrayList<History> getAll(Person person);
}
