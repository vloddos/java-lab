package common;

import java.io.Serializable;

public class Answer implements Serializable {

    public enum Status {
        OK,
        ERROR
    }

    public Status status;
}
