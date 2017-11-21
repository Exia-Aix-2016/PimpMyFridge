package ihm;

import Modele.ArduinoStates;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import java.util.Observable;
import java.util.Observer;


/**
 * Controller de l'application
 */
public class AppController implements Observer {

    private ArduinoStates arduinoStates;

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

        //Création du modèle arduino.
        this.arduinoStates = new ArduinoStates();








        //LISTERNERS

        this.consigne.textProperty().addListener((obs,old,niu)->{
            System.out.println(niu);


        });

        this.pro.textProperty().addListener((obs,old,niu)->{
            System.out.println(niu);


        });

        this.deriv.textProperty().addListener((obs,old,niu)->{
            System.out.println(niu);


        });

        this.integ.textProperty().addListener((obs,old,niu)->{
            System.out.println(niu);


        });

    }



    @Override
    public void update(Observable o, Object arg) {

    }
}
