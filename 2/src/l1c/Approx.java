package l1c;

public class Approx extends Polynom {

    public Approx(double[] X, double[] F, int m, String method) throws Exception {
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
