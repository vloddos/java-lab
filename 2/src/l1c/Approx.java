package l1c;

public class Approx extends Polynom {

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
