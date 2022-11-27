package DAO;

import FarmEntity.Person;
import Interfaces.IPersonDao;


import java.sql.*;
import java.util.ArrayList;

public class PersonDao implements IPersonDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status

    public int insert(Person person) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "insert into person(name,surname,phone,login,password,role,banned) "
                    + "values(?,?,?,?,?,?,false)";
            ps = con.prepareStatement(query);
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getPhone());
            ps.setString(4, person.getLogin());
            ps.setString(5, person.getPassword());
            ps.setString(6, person.getRole());
            st = ps.executeUpdate();
            System.out.println("inserted person " + st);
        } catch (SQLSyntaxErrorException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }
    public int update(Person person){
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE farm.person SET name=?,surname=?,phone=?,login=?, password=?"
                    + " WHERE id=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getPhone());
            ps.setString(4, person.getLogin());
            ps.setString(5, person.getPassword());
            ps.setInt(6, person.getId());
            st = ps.executeUpdate();;
        } catch (Exception e) {
            st = -1;
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;

    }
    public int delete(Person person) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "delete from person where id=? ";
            ps = con.prepareStatement(query);
            ps.setLong(1, person.getId());
            st = ps.executeUpdate();
            System.out.println("deleted student " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }
    public ArrayList<Person> fetchAll() {
        ArrayList<Person> personArrayListList = new ArrayList<>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from person "+
                    "where role='manager';";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setPhone(rs.getString("phone"));
                person.setLogin(rs.getString("login"));
                person.setPassword(rs.getString("password"));
                person.setRole(rs.getString("role"));
                person.setBanned(rs.getBoolean("banned"));
                personArrayListList.add(person);
                //teacherList.getItems().add(teacher); для listView
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return personArrayListList;
    }
    public Person fetchByLogin(String login) {
        Person person = new Person();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM farm.person where login=?";
            ps = con.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                person.setId(rs.getInt("id"));
                person.setRole(rs.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return person;
    }
    public int ban(Person person){
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE person SET banned=true "
                    + "WHERE id=? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, person.getId());
            st = ps.executeUpdate();;
        } catch (Exception e) {
            st = -1;
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;

    }
    public int unban(Person person){
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE person SET banned = false "
                    + "WHERE id=? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, person.getId());
            st = ps.executeUpdate();;
        } catch (Exception e) {
            st = -1;
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;

    }
    public Person fetchById(Person person){
        Person person1 = new Person();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM farm.person where id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, person.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                person1.setRole(rs.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return person1;
    }
    public Person fetchByUser(Person person) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM farm.person where login=? and password=?";
            ps = con.prepareStatement(query);
            ps.setString(1, person.getLogin());
            ps.setString(2, person.getPassword());
            rs = ps.executeQuery();
            while (rs.next()) {
                person.setId(rs.getInt("id"));
                person.setRole(rs.getString("role"));
                person.setBanned(rs.getBoolean("banned"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return person;
    }
    public Person fetchAdmin() {
        Person person = new Person();
        con = ConnectionFactory.getConnection();
        try {
            String query = "select * from person where role=?";
            ps = con.prepareStatement(query);
            ps.setString(1, "admin");
            rs = ps.executeQuery();
            while (rs.next()) {
                person.setId(rs.getInt("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return person;
    }
}
