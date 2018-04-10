package server;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import common.*;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class Server {

    private Socket socket;
    private static TreeMap<Long, Integer> session_id = new TreeMap<>();

    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "rootmysql";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String[] args) {
        try (
                ServerSocket socket = new ServerSocket(9027);
                Connection con = DriverManager.getConnection(url, user, password);
                Statement stmt = con.createStatement()
        ) {
            Server.con = con;
            Server.stmt = stmt;
            System.out.println(Server.con);
            System.out.println(socket);
            while (true)
                new Server(socket.accept()).run();
        } catch (Exception err) {
            System.out.println("ERROR: " + err);
        }
    }

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream src = new ObjectInputStream(socket.getInputStream())
        ) {
            Query query = (Query) src.readObject();
            Answer answer = parseQuery(query);
            out.writeObject(answer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Answer parseQuery(Query query) {
        try {
            switch (query.type) {
                case REGISTRATION:
                    rs = stmt.executeQuery("SELECT id FROM `bibliography`.`user` WHERE login = " + "'" + query.login + "'");
                    if (rs.next())
                        return new Answer(Answer.Status.ERROR, "This login is already taken");
                    stmt.executeUpdate(
                            "INSERT INTO `bibliography`.`user` (`login`, `pw`, `name`, `surname`) VALUES (" +
                                    "'" + query.login + "'," +
                                    "'" + query.pw + "'," +
                                    "'" + query.name + "'," +
                                    "'" + query.surname + "'" +
                                    ");"
                    );
                    return new Answer(Answer.Status.OK, "Registration is successful");
                case LOGIN:
                    rs = stmt.executeQuery("SELECT * FROM `bibliography`.`user` WHERE login = " + "'" + query.login + "'");
                    if (rs.next())
                        if (query.pw.equals(rs.getString("pw"))) {
                            long session = new Random().nextLong();
                            session_id.put(session, rs.getInt("id"));
                            System.out.println("session " + session + " open");
                            return new Answer(Answer.Status.OK, "", session, Role.values()[rs.getInt("role")]);
                        } else return new Answer(Answer.Status.ERROR, "Wrong password");
                    return new Answer(Answer.Status.ERROR, "Non-existent login");
                case SESSION_CLOSE:
                    session_id.remove(query.session);
                    System.out.println("session " + query.session + " closed");
                    return new Answer(Answer.Status.OK, "The session is closed");
                case REQUEST_AUTHORSHIP:
                    try {
                        stmt.executeUpdate(
                                "INSERT INTO `bibliography`.`request_authorship` (`user_id`) VALUES ('" +
                                        session_id.get(query.session) + "');"
                        );
                    } catch (MySQLIntegrityConstraintViolationException e) {
                        return new Answer(Answer.Status.ERROR, "You have already requested authorship");
                    }
                    return new Answer(Answer.Status.OK, "Your request accepted");
                case SELECT:
                    //rs = stmt.executeQuery("SELECT * FROM `bibliography`.`" + query.table + "`");
                    break;


            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return null;
    }
}