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
public class AppController {

    private ArduinoStates arduinoStates;


    //Displaying data
    @FXML
    private Label tempPlaque;
    @FXML
    private Label tempAmbiante;
    @FXML
    private Label pointRosee;


    //Button


    @FXML
    private Button btn_Set_Temp;
    @FXML
    private Button btn_Reset_Temp;
    @FXML
    private Button btn_Set_coefP;
    @FXML
    private Button btn_Reset_coefP;
    @FXML
    private Button btn_Set_coefI;
    @FXML
    private Button btn_Reset_coefI;
    @FXML
    private Button btn_Set_coefD;
    @FXML
    private Button btn_Reset_coefD;


    //Setting param
    @FXML
    private TextField consigneTemp;
    @FXML
    private TextField coefP;
    @FXML
    private TextField coefI;
    @FXML
    private TextField coefD;

    @FXML
    private LineChart hist;


    @FXML
    public void initialize() {

        //Création du modèle arduino.
        this.arduinoStates = new ArduinoStates();


        //Bind property

        this.arduinoStates.getCoefI().bind(this.coefI.textProperty());



        //BTN LISTERNERS


        this.btn_Set_coefI.setOnAction((event) -> {
            // TODO: 21/11/2017
        });
        this.btn_Reset_coefI.setOnAction((event) -> {
            // TODO: 21/11/2017
        });

        this.btn_Set_coefP.setOnAction((event) -> {
            // TODO: 21/11/2017
        });
        this.btn_Reset_coefP.setOnAction((event) -> {
            // TODO: 21/11/2017
        });

        this.btn_Set_coefD.setOnAction((event) -> {
            // TODO: 21/11/2017

        });
        this.btn_Reset_coefD.setOnAction((event) -> {
            // TODO: 21/11/2017

        });

    }

}
