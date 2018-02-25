import java.io.*;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws Exception {
        Lexer lexer = new Lexer("234 +2342.222 - ( ) ASBFKA2 2");
        LinkedList<Lexem> ll = lexer.getLArray();
        for (Lexem l : ll) {
            System.out.println(l.getClass() + " " + l + " " + l.isValid());
        }
    }
}

abstract class TokenError extends Exception {

    public TokenError() {
        super();
    }

    public TokenError(String message) {
        super(message);
    }
}

class IncorrectLexem extends TokenError {

    public IncorrectLexem() {
        super();
    }

    public IncorrectLexem(String message) {
        super(message);
    }
}

class NumberException extends TokenError {

    public NumberException() {
        super();
    }

    public NumberException(String message) {
        super(message);
    }
}

class IdentException extends TokenError {

    public IdentException() {
        super();
    }

    public IdentException(String message) {
        super(message);
    }
}

class BSignException extends TokenError {

    public BSignException() {
        super();
    }

    public BSignException(String message) {
        super(message);
    }
}

class USignException extends TokenError {

    public USignException() {
        super();
    }

    public USignException(String message) {
        super(message);
    }
}

class BraceException extends TokenError {

    public BraceException() {
        super();
    }

    public BraceException(String message) {
        super(message);
    }
}

///*
interface Token extends Cloneable {

    default boolean isValid() {
        return false;
    }

    default void set(String S) throws TokenError {
    }

    String toString();
}

class Lexem implements Token, Serializable {

    protected String value;

    public Lexem(String value) {
        this.value = value;
    }

    public void set(String S) throws TokenError {
        this.value = "" + S;
    }

    public String toString() {
        return this.value;
    }
}

class Number extends Lexem {

    public Number(String value) {
        super(value);
    }

    public boolean isValid() {
        try {
            Integer.parseInt(this.value);
        } catch (NumberFormatException e1) {
            try {
                Double.parseDouble(this.value);
            } catch (NumberFormatException e2) {
                return false;
            }
        }
        return true;
    }

    public void set(String S) throws TokenError {
        super.set(S);
        if (!this.isValid())
            throw new NumberException();
    }
}

class Ident extends Lexem {

    public Ident(String value) {
        super(value);
    }

    public boolean isValid() {
        return !this.value.matches("_+|\\d.*|.*\\W.*");
    }

    public void set(String S) throws TokenError {
        super.set(S);
        if (!this.isValid())
            throw new IdentException();
    }
}

class BSign extends Lexem {

    public BSign(String value) {
        super(value);
    }

    public boolean isValid() {
        return this.value.matches("[-+*/^]");
    }

    public void set(String S) throws TokenError {
        super.set(S);
        if (!this.isValid())
            throw new BSignException();
    }
}

class USign extends Lexem {

    public USign(String value) {
        super(value);
    }

    public boolean isValid() {
        return this.value.matches("[-+]");
    }

    public void set(String S) throws TokenError {
        super.set(S);
        if (!this.isValid())
            throw new USignException();
    }
}

class Brace extends Lexem {

    public Brace(String value) {
        super(value);
    }

    public boolean isValid() {
        return this.value.matches("[()]");
    }

    public void set(String S) throws TokenError {
        super.set(S);
        if (!this.isValid())
            throw new BraceException();
    }
}

class Lexer {

    private LinkedList<Lexem> LArray = new LinkedList<>();

    public Lexer(String s) throws IncorrectLexem {
        boolean il = false;
        for (String i : s.split("\\s+")) {
            String j = i.substring(0, 1);
            if (j.matches("[-+*/^]"))
                this.LArray.add(i.length() > 1 ? new Number(i) : new BSign(i));
            else if (j.matches("\\d"))
                this.LArray.add(new Number(i));
            else if (j.matches("[()]"))
                this.LArray.add(new Brace(i));
            else
                this.LArray.add(new Ident(i));
            il = !this.LArray.getLast().isValid();
        }
        if (il)
            throw new IncorrectLexem();
    }

    public LinkedList<Lexem> getLArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(baos);
        ous.writeObject(this.LArray);
        ous.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (LinkedList<Lexem>) ois.readObject();
    }
}
//*/