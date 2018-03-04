import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        //add qr

        double[] X, F;
        double[][] XF = Polynom.getData(new File("test.txt"));
        X = XF[0];
        F = XF[1];
        Approx a1 = new Approx(X, F, 15, "LU");
        System.out.println(Arrays.toString(a1.a));
        for (int i = 0; i <= a1.n; ++i)
            System.out.println(F[i] - a1.getValue(X[i]));
    }
}

class Solver {

    private int n;
    private double[][] P;
    private double[][] T;
    private String method;

    public Solver(double[][] A, String method) throws Exception {
        this.n = A.length;
        this.P = new double[A.length][A.length];
        this.T = new double[A.length][A.length];
        this.method = method;

        Solver.class.getDeclaredMethod(method, double[][].class).invoke(this, (Object) A);
    }

    public double[][] getP() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(baos);
        ous.writeObject(this.P);
        ous.close();
        return (double[][]) (new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))).readObject();
    }

    public double[][] getT() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(baos);
        ous.writeObject(this.T);
        ous.close();
        return (double[][]) (new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))).readObject();
    }

    private void Gauss(double[][] A) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(baos);
        ous.writeObject(A);
        ous.close();
        this.T = (double[][]) (new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))).readObject();

        int i, j, k;
        for (i = 0; i < this.n; ++i) {
            if (this.T[i][i] == 0)
                for (j = i + 1; j < this.n; ++j)
                    if (this.T[j][i] != 0) {
                        double[] tmp = this.T[i];
                        this.T[i] = this.T[j];
                        this.T[j] = tmp;
                        break;
                    }

            if (this.T[i][i] == 0) {
                this.P = null;
                this.T = null;
                return;
            }

            for (j = i + 1; j < this.n; ++j)
                for (k = this.n - 1; k >= 0; --k)
                    this.T[j][k] -= this.T[i][k] * this.T[j][i] / this.T[i][i];
        }

        for (i = 0; i < this.n; ++i)
            for (j = 0; j < this.n; ++j)
                this.P[i][j] = i == j ? 1 : 0;
    }

    private void LU(double[][] A) {
        int i, j, k;
        for (i = 0; i < this.n; ++i)
            for (j = i; j < this.n; ++j) {
                this.T[i][j] = A[i][j];
                for (k = 0; k < i; ++k)
                    this.T[i][j] -= this.P[i][k] * this.T[k][j];

                this.P[j][i] = A[j][i];
                for (k = 0; k < i; ++k)
                    this.P[j][i] -= this.P[j][k] * this.T[k][i];
                this.P[j][i] /= this.T[i][i];
            }
    }

    private double[] LU(double[] F) {
        int i, j;
        double[] x = new double[this.n];
        double[] y = new double[this.n];

        for (i = 0; i < this.n; ++i) {
            y[i] = F[i];
            for (j = 0; j < i; ++j)
                y[i] -= y[j] * this.P[i][j];
        }

        for (i = this.n - 1; i >= 0; --i) {
            x[i] = y[i];
            for (j = n - 1; j > i; --j)
                x[i] -= x[j] * this.T[i][j];
            x[i] /= this.T[i][i];
        }

        return x;
    }

    private void QR(double[][] A) {

    }

    private double[] QR(double[] F) {
        double[] x = new double[this.n];
        //...
        return x;
    }

    public double[] getSolve(double[] F) throws Exception {
        return (double[]) Solver.class.getDeclaredMethod(this.method, double[].class).invoke(this, F);
    }
}

class Polynom {

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

class Interp extends Polynom {

    public Solver s;

    public Interp(double[] X, double[] F, String method) throws Exception {
        super(X.length - 1);

        int i, j;
        double[][] A = new double[this.n + 1][this.n + 1];
        for (i = 0; i <= this.n; ++i)
            for (j = 0; j <= this.n; ++j)
                A[i][j] = Math.pow(X[i], j);

        this.s = new Solver(A, method);
        this.a = this.s.getSolve(F);
    }
}

class Approx extends Polynom {

    public Solver s;
    public double[] b;

    public Approx(double[] X, double[] F, int m, String method) throws Exception {
        super(m);

        int i, j, k;
        this.b = new double[m + 1];
        double[][] A = new double[m + 1][m + 1];

        for (i = 0; i <= m; ++i)
            for (j = 0; j <= m; ++j)
                for (k = 0; k < X.length; ++k)
                    A[i][j] += Math.pow(X[k], i + j);

        for (i = 0; i <= m; ++i)
            for (k = 0; k < X.length; ++k)
                this.b[i] += F[k] * Math.pow(X[k], i);

        this.s = new Solver(A, method);
        this.a = this.s.getSolve(this.b);
    }
}