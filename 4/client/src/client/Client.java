package client;

import common.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    public static long session;

    public static void request(Query query) throws Exception {
        Socket socket = new Socket("localhost", 9027);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(query);
        Answer answer = (Answer) in.readObject();
        View.INSTANCE.parseAnswer(query.type, answer);
    }
}