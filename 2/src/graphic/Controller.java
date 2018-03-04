package graphic;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.*;

import l1c.*;

public class Controller implements Initializable {

    public boolean mode = false;
    public LineChart linechart;
    public Spinner spinner;
    public Button bmode;
    public FileChooser filechooser = new FileChooser();
    public File input = null;
    public NumberAxis xAxis;
    public NumberAxis yAxis;

    public double[] X;
    public double[] F;

    public Approx approx;
    public Interp interp;

    public XYChart.Series pointset;

    public double x;
    public double y;
    public double xdelta;
    public double ydelta;
    public double scale = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        linechart.setLegendVisible(false);
        filechooser.setTitle("select file");
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
    }

    public void setFile(ActionEvent event) throws Exception {
        input = filechooser.showOpenDialog(null);
        if (input == null)
            return;

        linechart.getData().clear();
        double[][] XF = Polynom.getData(input);
        X = XF[0];
        F = XF[1];
        System.out.println(Arrays.toString(X));
        System.out.println(Arrays.toString(F));

        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, X.length - 1, 1));

        approx = new Approx(X, F, 1, "LU");
        interp = new Interp(X, F, "LU");

        //add set of point
        pointset = new XYChart.Series();
        pointset.setName("point set");
        for (int i = 0; i < X.length; ++i)
            pointset.getData().add(new XYChart.Data(X[i], F[i]));
        linechart.getData().add(pointset);

        xAxis.setLowerBound(Arrays.stream(X).min().getAsDouble());
        xAxis.setUpperBound(Arrays.stream(X).max().getAsDouble());
        xdelta = (xAxis.getUpperBound() - xAxis.getLowerBound()) / 100;

        yAxis.setLowerBound(Arrays.stream(F).min().getAsDouble());
        yAxis.setUpperBound(Arrays.stream(F).max().getAsDouble());
        ydelta = (yAxis.getUpperBound() - yAxis.getLowerBound()) / 100;
        System.out.println(xdelta + " " + ydelta + " " +
                xAxis.getLowerBound() + " " + xAxis.getUpperBound() + " " +
                yAxis.getLowerBound() + " " + yAxis.getUpperBound());
    }

    public void changeMode(ActionEvent event) {
        bmode.setText(mode ? "change" : "view");
        linechart.setOnMouseDragged(mode ? this::moveChart : this::movePoint);
        mode = !mode;
    }

    public void draw(ActionEvent event) {
        xAxis.setTickUnit(xdelta * 5);
        yAxis.setTickUnit(ydelta * 5);
        if (linechart.getData().size() > 1)
            linechart.getData().remove(1, 3);

        //draw approx
        if ((int) spinner.getValue() != approx.n)
            try {
                approx = new Approx(X, F, (int) spinner.getValue(), "LU");
            } catch (Exception e) {
            }
        double i;
        XYChart.Series aplot = new XYChart.Series();
        aplot.setName("approx");
        for (i = xAxis.getLowerBound(); i < xAxis.getUpperBound(); i += xdelta)
            aplot.getData().add(new XYChart.Data(i, approx.getValue(i)));
        linechart.getData().add(aplot);

        //draw interp
        XYChart.Series iplot = new XYChart.Series();
        iplot.setName("interp");
        for (i = xAxis.getLowerBound(); i < xAxis.getUpperBound(); i += xdelta)
            iplot.getData().add(new XYChart.Data(i, interp.getValue(i)));
        linechart.getData().add(iplot);
    }

    public void startMove(MouseEvent event) {
        x = xAxis.getValueForDisplay(event.getSceneX()).doubleValue();
        y = yAxis.getValueForDisplay(event.getSceneY()).doubleValue();
    }

    public void moveChart(MouseEvent event) {
        if (mode)
            return;
        double xx = xAxis.getValueForDisplay(event.getSceneX()).doubleValue();
        double yy = yAxis.getValueForDisplay(event.getSceneY()).doubleValue();
        xAxis.setLowerBound(xAxis.getLowerBound() + x - xx);
        xAxis.setUpperBound(xAxis.getUpperBound() + x - xx);
        yAxis.setLowerBound(yAxis.getLowerBound() + y - yy);
        yAxis.setUpperBound(yAxis.getUpperBound() + y - yy);
        draw(new ActionEvent());
        x = xx;
        y = yy;
    }

    public void movePoint(MouseEvent event) {
        if (!mode)
            return;
        double xx = xAxis.getValueForDisplay(event.getSceneX()).doubleValue();
        double yy = yAxis.getValueForDisplay(event.getSceneY()).doubleValue();
        int[] i = new int[1], imin = new int[1];
        Arrays.stream(X).reduce(Double.POSITIVE_INFINITY, (a, b) -> {
            ++i[0];
            if (Math.abs(b - x) < a) {
                imin[0] = i[0] - 1;
                return Math.abs(b - x);
            }
            return a;
        });
        //System.out.println(imin[0] + " " + x);
        double tmp = F[imin[0]];
        F[imin[0]] = yy;
        pointset.getData().set(imin[0], new XYChart.Data(X[imin[0]], F[imin[0]]));
        try {
            interp.a = interp.s.getSolve(F);
            for (int j = 0; j <= approx.n; ++j)
                approx.b[j] += (F[imin[0]] - tmp) * Math.pow(X[imin[0]], j);
            approx.a = approx.s.getSolve(approx.b);
        } catch (Exception e) {
        }
        draw(new ActionEvent());
        x = xx;
        y = yy;
    }

    public void changeScale(ScrollEvent event) {
        double tmp = Math.pow(2, Math.signum(event.getDeltaY()));
        if (mode || scale * tmp < 0.03125 || scale * tmp > 32)
            return;

        scale *= tmp;
        x = xAxis.getValueForDisplay(event.getSceneX()).doubleValue();
        y = yAxis.getValueForDisplay(event.getSceneY()).doubleValue();
        double xoffset = tmp > 1 ? x / tmp : -x;
        double yoffset = tmp > 1 ? y / tmp : -y;
        xdelta /= tmp;
        ydelta /= tmp;

        xAxis.setLowerBound(xAxis.getLowerBound() / tmp + xoffset);
        xAxis.setUpperBound(xAxis.getUpperBound() / tmp + xoffset);
        yAxis.setLowerBound(yAxis.getLowerBound() / tmp + yoffset);
        yAxis.setUpperBound(yAxis.getUpperBound() / tmp + yoffset);
        draw(new ActionEvent());
    }
}
//C:\Users\asus-dns\Desktop\универ\эвм\java\2\test\test2.txt