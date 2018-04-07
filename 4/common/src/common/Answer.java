package common;

import java.io.Serializable;

public class Answer implements Serializable {

    public enum Status {
        OK,
        ERROR
    }

    public Status status;
    public String message;

    public long session;

    public Answer(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Answer(Status status, String message, long session) {
        this.status = status;
        this.message = message;
        this.session = session;
    }
}
