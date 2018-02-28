package l1c;

import java.util.Scanner;

public class Polynom {

    public int n;//polynom pow
    public double a[];//a.length=n+1

    public static void getData(Scanner s, double[] X, double[] F) {
        int i;

        for (i = 0; i < X.length; ++i)
            X[i] = Double.parseDouble(s.next());

        for (i = 0; i < X.length; ++i)
            F[i] = Double.parseDouble(s.next());
    }

    public Polynom(int n) {
        this.n = n;
    }

    public double getValue(double x) {
        double result = 0;
        for (int i = 0; i <= this.n; ++i)
            result += this.a[i] * Math.pow(x, i);
        return result;
    }
}
