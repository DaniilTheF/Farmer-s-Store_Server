package Interfaces;

import FarmEntity.Person;
import FarmEntity.Product;

import java.util.ArrayList;

public interface IPersonDao {
 public int insert(Person person);
 public Person fetchById(Person person);
 public Person fetchByLogin(String login);
 public Person fetchByUser(Person person);
 public ArrayList<Person> fetchAll();
 public ArrayList<Person> fetchAllP();
 public int update(Person person);
 public ArrayList<Person> fetchAllBanned();
 public int delete(Person person);
 public Person fetchAdmin();
 public int ban(Person person);
 public int unban(Person person);
}
