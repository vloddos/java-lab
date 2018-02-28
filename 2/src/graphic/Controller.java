package graphic;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.io.File;
import java.net.URL;
import java.util.*;

import javafx.stage.FileChooser;

import l1c.*;

public class Controller implements Initializable {

    public boolean mode = false;
    public LineChart linechart;
    public Spinner spinner;
    public Button bmode;
    public FileChooser filechooser = new FileChooser();
    public File input = null;

    public static double[] X;
    public static double[] F;

    public static double xmin;
    public static double xmax;
    public static double delta;

    public static void getData(File file) throws Exception {
        Scanner s = new Scanner(file);

        int n = s.nextInt();
        X = new double[n + 1];
        F = new double[n + 1];

        Polynom.getData(s, X, F);

        //add draw function with borders
        xmin = Arrays.stream(X).reduce(Double.POSITIVE_INFINITY, (a, b) -> Math.min(a, b));
        xmax = Arrays.stream(X).reduce(Double.NEGATIVE_INFINITY, (a, b) -> Math.max(a, b));
        delta = (xmax - xmin) / 100;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //linechart.setLegendVisible(false);
        filechooser.setTitle("select file");
    }

    public void setFile(ActionEvent event) throws Exception {
        input = filechooser.showOpenDialog(null);

        if (input == null)
            return;

        //change reading
        getData(input);
        System.out.println(Arrays.toString(X));
        System.out.println(Arrays.toString(F));

        spinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1, X.length - 1, 1)
        );
    }

    public void changeMode(ActionEvent event) {
        bmode.setText(mode ? "change" : "view");
        mode = !mode;
    }

    public void drawApprox(ActionEvent event) throws Exception {
        //CSS???
        linechart.getData().clear();

        //add set of point
        XYChart.Series point = new XYChart.Series();
        point.setName("point set");

        for (int i = 0; i < X.length; ++i)
            point.getData().add(new XYChart.Data(X[i], F[i]));

        linechart.getData().add(point);

        //draw approx
        Approx a1 = new Approx(X, F, (int) spinner.getValue(), "LU");
        XYChart.Series aplot = new XYChart.Series();
        aplot.setName("approx");

        double i;

        for (i = xmin; i < xmax; i += delta)
            aplot.getData().add(new XYChart.Data(i, a1.getValue(i)));

        linechart.getData().add(aplot);

        //draw interp
        Interp i1 = new Interp(X, F, "LU");
        XYChart.Series iplot = new XYChart.Series();
        iplot.setName("interp");

        for (i = xmin; i < xmax; i += delta)
            iplot.getData().add(new XYChart.Data(i, i1.getValue(i)));

        linechart.getData().add(iplot);
    }
}