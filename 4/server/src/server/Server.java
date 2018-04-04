package server;

import common.Query;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.net.*;

//Пример реализации однопоточного сервера (способен поддерживать не более одного соединения):
public class Server {

    private Socket soc;
    private static TreeMap<Long, Integer> session_id = new TreeMap<>();

    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "rootmysql";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String[] args) {

        try {
            con = DriverManager.getConnection(url, user, password);
            //stmt = con.createStatement();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {//try()???????
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
        }

        try (ServerSocket soc = new ServerSocket(9027)) {
            //Слушаем порт:
            while (true) {
                new Server(soc.accept()).run();    //Создаем объект соединения и запускаем обработчик;
            }
        } catch (Exception err) {
            System.out.println("ERROR: " + err);
        }
    }

    public Server(Socket _soc) {
        soc = _soc;
    }

    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
                ObjectInputStream src = new ObjectInputStream(soc.getInputStream())
        ) {
            Query query = (Query) src.readObject();    //Читаем данные из сокета;

            /*System.out.println(query.user + ": " + query.text);
            query.text = "ANSWER: " + query.text;
            out.writeObject(query);    //Пишем данные в сокет;*/
        } catch (Exception err) {
            //...
        }
    }

    public void parseQuery(Query query) {
        try {
            switch (query.type) {
                case REGISTRATION:
                    rs = con.createStatement().executeQuery(
                            "SELECT id FROM `bibliography`.`user` WHERE login = " + "'" + query.login + "'"
                    );
                    if (rs.next()) {
                        //...
                        break;
                    }
                    con.createStatement().executeUpdate(
                            "INSERT INTO `bibliography`.`user` (`login`, `pw`, `name`, `surname`) VALUES (" +
                                    "'" + query.login + "'," +
                                    "'" + query.pw + "'," +
                                    "'" + query.name + "'," +
                                    "'" + query.surname + "'" +
                                    ");"
                    );

                    break;
                case LOGIN:
                    long session = new Random().nextLong();///???????
                    break;

            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }
}