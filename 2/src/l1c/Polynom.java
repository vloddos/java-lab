package l1c;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class Polynom {

    public int n;//polynom pow
    public double a[];//a.length=n+1

    public static double[][] getData(File file) throws Exception {
        Scanner s = new Scanner(file);
        s.useDelimiter("\\s+");
        s.useLocale(Locale.ENGLISH);
        int i, n = s.nextInt();
        double[][] XF = new double[][]{new double[n + 1], new double[n + 1]};
        for (i = 0; i <= n; ++i)
            XF[0][i] = s.nextDouble();
        for (i = 0; i <= n; ++i)
            XF[1][i] = s.nextDouble();
        return XF;
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
