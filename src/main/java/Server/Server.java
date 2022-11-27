package Server;

import DAO.*;
import FarmEntity.*;
import Interfaces.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Server implements Runnable {
        protected Socket clientSocket = null;
        ObjectInputStream sois;
        ObjectOutputStream soos;
        Gson gson = new Gson();

        public Server(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                sois = new ObjectInputStream(clientSocket.getInputStream());
                soos = new ObjectOutputStream(clientSocket.getOutputStream());
                while (true) {
                    System.out.println("Получение команды от клиента...");
                    String choice = sois.readObject().toString();
                    System.out.println(choice);
                    System.out.println("Команда получена");
                    switch (choice) {
                       /* case "registrationManager" -> {
                            System.out.println("Запрос к БД на проверку пользователя(таблица teachers), клиент: " + clientSocket.getInetAddress().toString());
                            Person manager = (Person) sois.readObject();
                            System.out.println(manager.toString());
                            IPersonDao dao = new PersonDao();
                             int res = dao.insert(manager);
                            if (res != -1 & res != -2) {
                                soos.writeObject("OK");
                            } else {
                                soos.writeObject("Incorrect Data");
                            }
                        }*/
                        case "SignUp" -> {
                            IPersonDao dao = new PersonDao();
                            ICustomerDao doc = new CustomerDao();
                            Person persona = dao.fetchAdmin();
                            if(persona.getId() != 0){
                                soos.writeObject("true");
                                System.out.println("Запрос к БД на проверку пользователя(таблица person), клиент: " + clientSocket.getInetAddress().toString());
                                String acc = sois.readObject().toString();
                                Person customer = gson.fromJson(acc,Person.class);
                                System.out.println(customer);
                                Person person1 = dao.fetchByLogin(customer.getLogin());
                                if(person1.getId()>0){
                                    soos.writeObject("This user is already existed");
                                }
                                soos.writeObject("OK");
                                dao.insert(customer);
                                Person person = dao.fetchByLogin(customer.getLogin());
                                System.out.println(person);
                                if (person.getId() != 0) {
                                    doc.insertCustomer(person);
                                    soos.writeObject("OK");
                                    soos.writeObject(gson.toJson(person));
                                } else {
                                    soos.writeObject("This user is already existed");
                                }
                            }else {
                                soos.writeObject("ok");
                                System.out.println("Запрос к БД на проверку пользователя(таблица person), клиент: " + clientSocket.getInetAddress().toString());
                                String acc = sois.readObject().toString();
                                Person customer = gson.fromJson(acc,Person.class);
                                System.out.println(customer);
                                dao.insert(customer);
                                Person person = dao.fetchByLogin(customer.getLogin());
                                System.out.println(person);
                                if (person.getId() != 0) {
                                    soos.writeObject("OK");
                                    soos.writeObject(gson.toJson(person));
                                } else {
                                    soos.writeObject("This user is already existed");
                                }
                            }

                        }
                        case "AddProduct" -> {
                            IProductDao dao = new ProductDao();
                            System.out.println("Запрос к БД на проверку пользователя(таблица person), клиент: " + clientSocket.getInetAddress().toString());
                            String acc = sois.readObject().toString();
                            Product product = gson.fromJson(acc,Product.class);
                            int a = 0;
                            Product product1 = dao.fetchByName(product);
                            if(product1.getId()>0){
                                soos.writeObject("This product is already existed");
                            }
                            soos.writeObject("OK");
                            a = dao.insertProduct(product);
                            if (a>0) {
                                soos.writeObject("OK");
                            } else {
                                soos.writeObject("This user is already existed");
                            }
                        }
                        case "AddPerson" -> {
                            IPersonDao dao = new PersonDao();
                            IManagerDao dom = new ManagerDao();
                                System.out.println("Запрос к БД на добавление продукта (таблица person), клиент: " + clientSocket.getInetAddress().toString());
                                String acc = sois.readObject().toString();
                                Person manager = gson.fromJson(acc,Person.class);
                                Person person = dao.fetchByLogin(manager.getLogin());
                            if (!(person.getId() > 0)) {
                                dao.insert(manager);
                                    dom.insertManager(person);
                                    soos.writeObject("OK");
                                } else {
                                    soos.writeObject("This user is already existed");
                                }
                            }
                        case "Sent" -> {
                            IPurchaseDao dap = new PurchaseDao();
                            ISentDao das = new SentDao();
                            IManagerDao dam = new ManagerDao();
                            System.out.println("Запрос к БД на добавление продукта (таблица person), клиент: " + clientSocket.getInetAddress().toString());
                            String acc = sois.readObject().toString();
                            Purchase order = gson.fromJson(acc,Purchase.class);
                            //проверка на отправленность
                           Purchase purchase =  dap.getPurchasedId(order);
                            if(purchase.getId() > 0){//нет такого оплаченного заказа
                                soos.writeObject("OK");
                                Sent sent = das.isSent(order);
                                if(sent.getId() > 0){//уже есть такой отправленный заказ
                                    soos.writeObject("gg");
                                }
                                soos.writeObject("OK");
                               Sent sent1 = dap.getCustomerId(purchase);
                               das.insertSent(sent1);
                               dam.increaseCount(order);
                               dap.sent(purchase);
                            }else{
                                soos.writeObject("gg");
                            }


                            //нахождение адресса клиента, занесение в Table sent c адрессом, увеличение count_of_order manager, отправлен true в purchase
                        }
                        case "UpdateProduct" -> {
                            IProductDao dao = new ProductDao();
                            System.out.println("Запрос к БД на обновление продукта(таблица person), клиент: " + clientSocket.getInetAddress().toString());
                            String acc = sois.readObject().toString();
                            Product product = gson.fromJson(acc,Product.class);
                            int a = 0;
                            a = dao.updateProduct3(product);
                            if (a > 0) {
                                soos.writeObject("OK");
                            } else {
                                soos.writeObject("This product wasn`t added");
                            }
                        }
                        case "UpdatePerson" -> {
                            IPersonDao dao = new PersonDao();
                            System.out.println("Запрос к БД на обновление пользователя(таблица person), клиент: " + clientSocket.getInetAddress().toString());
                            String acc = sois.readObject().toString();
                            Person person1 = gson.fromJson(acc,Person.class);
                            Person person = dao.fetchById(person1);
                            int a = 0;
                            if(person.getRole().equals("manager")){
                                a = dao.update(person1);
                            }
                            System.out.println(a);
                            if (a != 0) {
                                soos.writeObject("OK");
                            } else {
                                soos.writeObject("This user wasn`t added");
                            }
                        }
                        case "DeletePerson" -> {
                            IPersonDao dao = new PersonDao();
                            IManagerDao dom = new ManagerDao();
                            System.out.println("Запрос к БД на удаление пользователя(таблица person), клиент: " + clientSocket.getInetAddress().toString());
                            String acc = sois.readObject().toString();
                            Person person1 = gson.fromJson(acc,Person.class);
                            System.out.println(person1);
                            Person person = dao.fetchById(person1);
                            if(person.getRole() == null){
                                person.setRole("none");
                            }
                            int a = 0;
                            if(person.getRole().equals("manager")){
                                dom.deleteManager(person1);
                                a = dao.delete(person1);
                            }
                            System.out.println(a);
                            if (a != 0) {
                                soos.writeObject("OK");
                            } else {
                                soos.writeObject("This user wasn`t added");
                            }
                        }
                        case "DeleteProduct" -> {
                            IProductDao dao = new ProductDao();
                            System.out.println("Запрос к БД на удаление пользователя(таблица person), клиент: " + clientSocket.getInetAddress().toString());
                            String acc = sois.readObject().toString();
                            Product product = gson.fromJson(acc,Product.class);
                            int a = 0;
                            a = dao.deleteProduct(product);
                            if (a > 0) {
                                soos.writeObject("OK");
                            } else {
                                soos.writeObject("This user wasn`t added");
                            }
                        }
                        case "ViewPerson" -> {
                            IPersonDao dao = new PersonDao();
                            System.out.println("Запрос к БД на вывод пользователей(таблица person), клиент: " + clientSocket.getInetAddress().toString());
                            ArrayList<Person> list = dao.fetchAll();
                            if (list.size() != 0) {
                                soos.writeObject("OK");
                                Type fooType = new TypeToken<ArrayList<Person>>() {}.getType();
                                soos.writeObject(gson.toJson(list, fooType));
                            } else {
                                soos.writeObject("No data");
                            }
                        }
                         case "NewOrder" -> {
                            IOrderDao dao = new OrderDao();
                            System.out.println("Запрос к БД на вывод заказов(таблица orders), клиент: " + clientSocket.getInetAddress().toString());
                            String obj = sois.readObject().toString();
                            Cart cart = gson.fromJson(obj,Cart.class);
                            dao.insertOrder(cart);
                            Orders order = dao.getOrderId();
                             System.out.println(order);
                            if (order.getId() != 0) {
                                soos.writeObject("OK");
                                soos.writeObject(gson.toJson(order));
                                ICartDao daoc = new CartDao();
                                String obj1 = sois.readObject().toString();
                                Type fooType = new TypeToken<ArrayList<Cart>>() {}.getType();
                                ArrayList<Cart> r =  gson.fromJson(obj1,fooType);
                                   for (Cart c: r) {
                                         c.setOrdersId(order.getId());
                                          daoc.insert(c);
                                           }
                            } else {
                                soos.writeObject("Error to add order");
                            }
                        }
                        case"UpdateProductQuantity"->{
                            IProductDao dao = new ProductDao();
                            System.out.println("Запрос к БД на обновление продукции (таблица product), клиент: " + clientSocket.getInetAddress().toString());
                            String obj = sois.readObject().toString();
                            Type fooType = new TypeToken<ArrayList<Product>>() {}.getType();
                            ArrayList<Product> r =  gson.fromJson(obj,fooType);
                            for (Product product: r){
                                dao.updateProduct(product);
                            }
                        }
                        case"AddMoney"->{
                            ICustomerDao dao = new CustomerDao();
                            System.out.println("Запрос к БД на пополнение денег (таблица customer), клиент: " + clientSocket.getInetAddress().toString());
                            String obj = sois.readObject().toString();
                            Customer customer = gson.fromJson(obj,Customer.class);
                            dao.addMoney(customer);
                        }
                        case "ViewProduct" -> {
                            IProductDao dao = new ProductDao();
                            System.out.println("Запрос к БД на вывод продукции (таблица product), клиент: " + clientSocket.getInetAddress().toString());
                            ArrayList<Product> list = dao.fetchProducts();
                            if (list.size() != 0) {
                                soos.writeObject("OK");
                                Type fooType = new TypeToken<ArrayList<Product>>() {}.getType();
                                soos.writeObject(gson.toJson(list, fooType));
                            } else {
                                soos.writeObject("No data");
                            }
                        }
                        case "DeleteOrder" -> {
                            IOrderDao dao = new OrderDao();
                            ICartDao doc = new CartDao();
                            IPurchaseDao dop = new PurchaseDao();
                            IProductDao daop = new ProductDao();
                            System.out.println("Запрос к БД на удаление заказа(таблица order,cart), клиент: " + clientSocket.getInetAddress().toString());
                                String obj = sois.readObject().toString();
                                Cart cart = gson.fromJson(obj,Cart.class);
                                System.out.println(cart);
                                Orders orders = new Orders();
                                orders.setId(cart.getOrdersId());
                                Purchase purchase = dop.getPurchasedOrderId(orders);
                            if(purchase.getOrderId()>0){
                                soos.writeObject("gg");
                            }else {
                                soos.writeObject("OK");
                                ArrayList<Product> list = doc.restoreProducts(cart);
                                for (Product c : list) {
                                    daop.updateProduct2(c);
                                }
                                int b = doc.deleteCart(cart);
                                if (b > 0) {
                                    soos.writeObject("OK");
                                    int a = dao.deleteOrder(cart);
                                    System.out.println(a);
                                } else {
                                    soos.writeObject("No data");
                                    System.out.println("gg");
                                }
                            }

                        }
                        case "Ban"->{
                            IPersonDao dao = new PersonDao();
                            System.out.println("Запрос к БД на ban пользователя(таблица person), клиент: " + clientSocket.getInetAddress().toString());
                            String obj = sois.readObject().toString();
                            Person person1 = gson.fromJson(obj,Person.class);
                            System.out.println(person1);
                            Person person = dao.fetchById(person1);
                            if(person.getRole() == null){
                                person.setRole("none");
                            }
                            int a = 0;
                            if(!(person.getRole().equals("admin"))){
                                a = dao.ban(person1);
                            }
                            System.out.println(a);
                            if (a != 0) {
                                soos.writeObject("OK");
                            } else {
                                soos.writeObject("Admin cant ban him self");
                            }
                        }
                        case "UnBan"->{
                            IPersonDao dao = new PersonDao();
                            System.out.println("Запрос к БД на unban пользователя(таблица person), клиент: " + clientSocket.getInetAddress().toString());
                            String obj = sois.readObject().toString();
                            Person person1 = gson.fromJson(obj,Person.class);
                            System.out.println(person1);
                            Person person = dao.fetchById(person1);
                            if(person.getRole() == null){
                                person.setRole("none");
                            }
                            int a = 0;
                            if(!(person.getRole().equals("admin"))){
                                a = dao.unban(person1);
                            }
                            System.out.println(a);
                            if (a != 0) {
                                soos.writeObject("OK");
                            } else {
                                soos.writeObject("Admin cant unban him self");
                            }
                        }
                        case "PurchaseOrder" -> {
                            IOrderDao dao = new OrderDao();
                            ICustomerDao doc = new CustomerDao();
                            IPurchaseDao dop = new PurchaseDao();
                            IEarnedDao dae = new EarnedDao();
                            System.out.println("Запрос к БД на оплату заказа(таблица order,cart), клиент: " + clientSocket.getInetAddress().toString());
                            String obj = sois.readObject().toString();
                            Orders orders = gson.fromJson(obj,Orders.class);
                            System.out.println(orders);
                            Orders order = dao.getOrderPrice(orders);
                            int a = 0;
                            Purchase purchase = dop.getPurchasedOrderId(orders);
                            if(purchase.getOrderId()>0){
                                soos.writeObject("gg");
                            }else {
                                soos.writeObject("OK");
                                if (order.getTotal_price() > 0) {
                                    soos.writeObject("OK");
                                    a = doc.withdrawMoney(order);
                                    if (a > 0) {
                                        soos.writeObject("OK");
                                        dae.updateEarned(order);
                                        order.setPersonId(doc.getCustomerId(orders));
                                        dop.insertPurchasedOrder(order);
                                    } else {
                                        soos.writeObject("gg");
                                    }
                                } else {
                                    soos.writeObject("gg");
                                }
                            }
                        }
                            case "InfoOrder" -> {
                            ICartDao dao = new CartDao();
                            System.out.println("Запрос к БД на вывод заказов(таблица orders), клиент: " + clientSocket.getInetAddress().toString());
                            String obj = sois.readObject().toString();
                            Cart cart = gson.fromJson(obj,Cart.class);
                            ArrayList<Cart> list = dao.getInfoAboutOrder(cart);
                                System.out.println("все заебись");
                            if (list.size() != 0) {
                                System.out.println("все заебись");
                                soos.writeObject("OK");
                                Type fooType = new TypeToken<ArrayList<Cart>>() {}.getType();
                                soos.writeObject(gson.toJson(list, fooType));
                            } else {
                                System.out.println("все пизда");
                                soos.writeObject("No data");
                            }
                        }
                        case "ViewEarned" -> {
                            IEarnedDao dao = new EarnedDao();
                            System.out.println("Запрос к БД на вывод заработанных денег(таблица earned), клиент: " + clientSocket.getInetAddress().toString());
                            ArrayList<Earned> list = dao.getEarned();
                            if (list.size() != 0) {
                                soos.writeObject("OK");
                                Type fooType = new TypeToken<ArrayList<Earned>>() {}.getType();
                                soos.writeObject(gson.toJson(list, fooType));
                            } else {
                                soos.writeObject("No data");
                            }
                        }
                        case "ViewOrder" -> {
                            IOrderDao dao = new OrderDao();
                            System.out.println("Запрос к БД на вывод заказов(таблица orders), клиент: " + clientSocket.getInetAddress().toString());
                            String obj = sois.readObject().toString();
                            Cart cart = gson.fromJson(obj,Cart.class);
                            Vector<Orders> list = dao.getAllOrders(cart);
                            if (list.size() != 0) {
                                soos.writeObject("OK");
                                Type fooType = new TypeToken<Vector<Orders>>() {}.getType();
                                soos.writeObject(gson.toJson(list, fooType));
                            } else {
                                soos.writeObject("No data");
                            }
                        }
                        case "ViewBalance" -> {
                            ICustomerDao dao = new CustomerDao();
                            System.out.println("Запрос к БД на вывод счета (таблица customer), клиент: " + clientSocket.getInetAddress().toString());
                            String obj = sois.readObject().toString();
                            Customer customer = gson.fromJson(obj,Customer.class);
                            ArrayList<Customer> list = dao.getCustomerBalance(customer);
                            if (list.size() != 0) {
                                soos.writeObject("OK");
                                Type fooType = new TypeToken<ArrayList<Customer>>() {}.getType();
                                soos.writeObject(gson.toJson(list, fooType));
                            } else {
                                soos.writeObject("No data");
                            }
                        }
                        case "ViewPurchase" -> {
                            IPurchaseDao dao = new PurchaseDao();
                            ICustomerDao dac = new CustomerDao();
                            System.out.println("Запрос к БД на вывод оплаченных заказов (таблица purchase), клиент: " + clientSocket.getInetAddress().toString());
                            String obj = sois.readObject().toString();
                            Purchase purchase = gson.fromJson(obj,Purchase.class);
                            purchase.setCustomerId(dac.getCustomerIdP(purchase));
                            ArrayList<Purchase> list = dao.getPurchasedOrders(purchase);
                            if (list.size() != 0) {
                                soos.writeObject("OK");
                                Type fooType = new TypeToken<ArrayList<Purchase>>() {}.getType();
                                soos.writeObject(gson.toJson(list, fooType));
                            } else {
                                soos.writeObject("No data");
                            }
                        }
                        case "ViewPurchases" -> {
                            IPurchaseDao dao = new PurchaseDao();

                            System.out.println("Запрос к БД на вывод оплаченных заказов (таблица purchase), клиент: " + clientSocket.getInetAddress().toString());
                            ArrayList<Purchase> list = dao.getPurchase();
                            if (list.size() != 0) {
                                soos.writeObject("OK");
                                Type fooType = new TypeToken<ArrayList<Purchase>>() {}.getType();
                                soos.writeObject(gson.toJson(list, fooType));
                            } else {
                                soos.writeObject("No data");
                            }
                        }
                        case "LogIn" -> {
                            System.out.println("Выполняется авторизация пользователя....");
                            String acc = sois.readObject().toString();
                            Person auth = gson.fromJson(acc,Person.class);
                            System.out.println(auth.toString());

                            IPersonDao dao = new PersonDao();

                            Person person = dao.fetchByUser(auth);
                            System.out.println(person.toString());
                            if (person.getId() != 0 && person.getRole() != "") {
                                soos.writeObject("OK");
                                soos.writeObject(gson.toJson(person));
                            } else
                                soos.writeObject("There is no data!");
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Client disconnected.");
            }
        }
    }


