package common;

import java.io.Serializable;

public class Answer implements Serializable {

    public enum Status {
        OK,
        ERROR
    }

    public Status status;
    public String message;

    public Answer(Status status, String message) {
        this.status = status;
        this.message = message;
    }
}
