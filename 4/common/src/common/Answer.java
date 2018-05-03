package common;

import java.io.Serializable;
import java.util.ArrayList;

public class Answer implements Serializable {

    public enum Status {
        OK,
        ERROR
    }

    public Status status;
    public String message;

    public long session;
    public Role role;

    public ArrayList<ArrayList<String>> al;//test

    public Answer(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Answer(Status status, String message, long session, Role role) {
        this.status = status;
        this.message = message;
        this.session = session;
        this.role = role;
    }

    public Answer(Status status, String message, ArrayList<ArrayList<String>> al) {
        this.status = status;
        this.message = message;
        this.al = al;
    }
}
