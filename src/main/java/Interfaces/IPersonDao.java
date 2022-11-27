package Interfaces;

import FarmEntity.Person;

import java.util.ArrayList;

public interface IPersonDao {
 public int insert(Person person);
 public Person fetchById(Person person);
 public Person fetchByLogin(String login);
 public Person fetchByUser(Person person);
 public ArrayList<Person> fetchAll();
 public int update(Person person);
 public int delete(Person person);
 public Person fetchAdmin();
 public int ban(Person person);
 public int unban(Person person);
}
