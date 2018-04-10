package common;

import java.io.Serializable;
import java.sql.ResultSet;

public class Answer implements Serializable {

    public enum Status {
        OK,
        ERROR
    }

    public Status status;
    public String message;

    public long session;
    public Role role;

    public ResultSet rs;

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

    public Answer(Status status, String message, ResultSet rs) {
        this.status = status;
        this.message = message;
        this.rs = rs;
    }
}
