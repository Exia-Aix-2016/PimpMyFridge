package ihm;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;




public class AppController {


    @FXML
    private TextField pro;
    @FXML
    private TextField consigne;
    @FXML
    private TextField deriv;
    @FXML
    private TextField integ;

    @FXML
    private LineChart hist;

    XYChart.Series series = new XYChart.Series();


    @FXML
    public void initialize() {

        consigne.textProperty().addListener((obs,old,niu)->{
            System.out.println(niu);


        });
    }









}
