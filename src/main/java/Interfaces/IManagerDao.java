package Interfaces;

import FarmEntity.Person;
import FarmEntity.Purchase;

public interface IManagerDao {
    public int insertManager(Person manager);
    public int deleteManager(Person person);
    public void increaseCount(Purchase order);
}
