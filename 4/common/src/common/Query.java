package common;

import java.io.Serializable;

public class Query implements Serializable {

    //reg
    public String login;
    public String pw;
    public String name;
    public String surname;

    public long session;
    //public String text;
    public Type type;
    public String table;

    public enum Type {
        REGISTRATION,
        LOGIN,
        SELECT,
        DELETE,
        INSERT,
        UPDATE
    }

    //type???
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

    //SELECT
    /*public Query(Type type,long session, String table,){

    }*/


    public static void main() {
        System.out.println("test1488");

    }
}