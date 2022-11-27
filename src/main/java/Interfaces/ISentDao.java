package Interfaces;

import FarmEntity.*;

public interface ISentDao {
    public Sent isSent(Purchase order);
    public int insertSent(Sent sent);
}
