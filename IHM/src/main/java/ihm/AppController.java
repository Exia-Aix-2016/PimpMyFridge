package ihm;

import Modele.ArduinoStates;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;


/**
 * Controller de l'application
 */
public class AppController implements Observer {

    private ArduinoStates arduinoStates;

    @FXML
    private Label temp;

    @FXML
    private TextField consigne;
    @FXML
    private TextField coefP;
    @FXML
    private TextField coefI;
    @FXML
    private TextField coefD;

    @FXML
    private LineChart hist;

    XYChart.Series series = new XYChart.Series();




    @FXML
    public void initialize() {

        coefI.textProperty().bindBidirectional(coefD.textProperty());


        //Création du modèle arduino.
        this.arduinoStates = new ArduinoStates();
    }



    @Override
    public void update(Observable o, Object arg) {

    }


    /**
     * Permet de bind les valeurs fxml avec celles du modèle.
     */
    private void bindingValue(){


    }
}
