package common;

import java.io.Serializable;

public class Query implements Serializable {

    public String login;
    public String pw;
    public String name;
    public String surname;

    public long session;

    public Type type;

    public String table;
    public String set;
    public String where;

    public enum Type {
        REGISTRATION,
        LOGIN,
        SELECT,
        DELETE,
        INSERT,
        UPDATE,
        SESSION_CLOSE,
        REQUEST_AUTHORSHIP
    }

    //REGISTRATION
    public Query(Type type, String login, String pw, String name, String surname) {
        this.type = type;
        this.login = login;
        this.pw = pw;
        this.name = name;
        this.surname = surname;
    }

    //LOGIN
    public Query(Type type, String login, String pw) {//log
        this.type = type;
        this.login = login;
        this.pw = pw;
    }

    //SELECT, DELETE
    public Query(Type type, long session, String table, String where) {
        this.type = type;
        this.session = session;
        this.table = table;
        this.where = where;
    }

    //UPDATE
    public Query(Type type, long session, String table, String set, String where) {
        this.type = type;
        this.session = session;
        this.table = table;
        this.set = set;
        this.where = where;
    }

    //SESSION_CLOSE, REQUEST_AUTHORSHIP
    public Query(Type type, long session) {
        this.type = type;
        this.session = session;
    }
}