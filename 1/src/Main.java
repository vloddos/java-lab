import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        int i, n;
        /*double[] X = new double[]{-15.925746845311053, -3.5183126916653564, 11.836294728747523, -24.292710200132387,
                -28.16723406982122, 4.034040112397375, -55.542953922439246, -36.041188927155204, 1.7461873381761084,
                5.0311336733256855, 8.037866562135479};
        double[] F = new double[]{1.6747584878641044, -27.039005500899368, 18.08586230445251, 60.54059811334703,
                0.901919834955498, 9.623742705871436, -15.19626731929019, 65.53955319599548, 3.887731580058406,
                58.76520240887673, 27.632185408187606};*/

        Scanner s = new Scanner(new File("test.txt"));//.useDelimiter("[\\s,]*");

        n = s.nextInt();
        double[] X = new double[n + 1];
        double[] F = new double[n + 1];

        Polynom.getData(s, X, F);
        Approx a1 = new Approx(X, F, 15, "LU");
        System.out.println(Arrays.toString(a1.a));
    }

}

class Solver {

    public int n;
    public double[][] P;
    public double[][] T;
    public String method;


    public Solver(double[][] A, String method) {
        this.n = A.length;
        this.P = new double[A.length][A.length];
        this.T = new double[A.length][A.length];
        this.method = method;

        if (method.equals("Gauss"))
            this.Gauss(A);
        else if (method.equals("LU"))
            this.LU(A);
        else if (method.equals("QR"))
            this.QR(A);
    }

    public void Gauss(double[][] A) {
        int i, j, k;
        for (i = 0; i < this.n; ++i) {
            if (A[i][i] == 0)
                for (j = i + 1; j < this.n; ++j)
                    if (A[j][i] != 0) {
                        double[] tmp = A[i];
                        A[i] = A[j];
                        A[j] = tmp;
                        break;
                    }

            if (A[i][i] == 0) {//?????????????
                System.out.println("error");
                return;
            }

            for (j = i + 1; j < this.n; ++j)
                for (k = this.n - 1; k >= 0; --k)
                    A[j][k] -= A[i][k] * A[j][i] / A[i][i];
        }

        this.T = A;
        for (i = 0; i < this.n; ++i)
            for (j = 0; j < this.n; ++j)
                this.P[i][j] = i == j ? 1 : 0;
    }

    public void LU(double[][] A) {
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

    public double[] LU(double[] F) {
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

    public void QR(double[][] A) {

    }

    public double[] QR(double[] F) {
        double[] x = new double[this.n];
        //...
        return x;
    }

    public double[] getSolve(double[] F) {
        if (this.method.equals("LU"))
            return this.LU(F);
        else
            return this.QR(F);
    }
}

class Polynom {

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

class Interp extends Polynom {

    public Interp(double[] X, double[] F, String method) {
        super(X.length - 1);

        int i, j;
        double[][] A = new double[this.n + 1][this.n + 1];
        for (i = 0; i <= this.n; ++i)
            for (j = 0; j <= this.n; ++j)
                A[i][j] = Math.pow(X[i], j);

        Solver s = new Solver(A, method);
        this.a = s.getSolve(F);
    }
}

class Approx extends Polynom {

    public double m;//m<=n

    public Approx(double[] X, double[] F, int m, String method) {
        super(m);

        int i, j, k;
        double[] b = new double[m + 1];
        double[][] A = new double[m + 1][m + 1];

        for (i = 0; i <= m; ++i)
            for (j = 0; j <= m; ++j)
                for (k = 0; k < X.length; ++k)
                    A[i][j] += Math.pow(X[k], i + j);

        for (i = 0; i <= m; ++i)
            for (k = 0; k < X.length; ++k)
                b[i] += F[k] * Math.pow(X[k], i);

        Solver s = new Solver(A, method);
        this.a = s.getSolve(b);
    }
}
/*
constructor
define
init block
 */








/*Path path=Paths.get("C:\\Users\\asus-dns\\Desktop\\универ\\эвм\\java\\1\\src\\input.txt");
        Scanner in = new Scanner(path,"UTF-8");
        int a = in.nextInt();
        int b = in.nextInt();
        System.out.println(a + " " + b);*/

//foreach
//nextint nextdouble
        /*Console c = System.console();
        if (c == null) {
            System.out.println("123");
            return;
        }
        char[] p = c.readPassword();
        System.out.println(p.toString());*/
/*
int i, j;
        double[][] a = new double[3][3];
        for (i = 0; i < a.length; ++i)
            for (j = 0; j < a.length; ++j)
                a[i][j] = j + i * a.length;

        System.out.println(Arrays.deepToString(a));
        double[][] b = a.clone();
        b[2][2] = 5;
        System.out.println(Arrays.deepToString(a));
        System.out.println(Arrays.deepToString(b));
 */

/*
int i, j;
        double[][] a = {
                {1.8037768223930248, 0.2165082286221518, 0.19664929044227908},
                {0.3782095144033928, 2.097320778980664, 2.69353450971893},
                {0.4237566344305702, 1.7495059478831054, 2.636282229861859}
        };
        System.out.println(Arrays.deepToString(a));
        Solver s1 = new Solver(a,"LU");
        System.out.println(Arrays.deepToString(s1.T));
        System.out.println(Arrays.deepToString(s1.P));*/
        /*Scanner sc = new Scanner(new File("C:\\Users\\asus-dns\\Desktop\\универ\\эвм\\java\\1", "input.txt"));
        while (sc.hasNextDouble())
            System.out.println(sc.nextDouble());
        */