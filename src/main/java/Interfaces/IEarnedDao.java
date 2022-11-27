package Interfaces;

import FarmEntity.Earned;
import FarmEntity.Orders;

import java.util.ArrayList;

public interface IEarnedDao {
    public int updateEarned(Orders order);
    public ArrayList<Earned> getEarned();
}
