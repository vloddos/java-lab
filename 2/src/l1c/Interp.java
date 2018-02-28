package l1c;

public class Interp extends Polynom {

    public Interp(double[] X, double[] F, String method) throws Exception {
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
