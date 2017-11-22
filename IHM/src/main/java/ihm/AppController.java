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
    private Label Tp;
    @FXML
    private Label tempAmbiante;
    @FXML
    private Label pointRosee;
    @FXML
    private Label H;

    //Button
    @FXML
    private Button btn_Set_ConsigneTemp;
    @FXML
    private Button btn_Reset_ConsigneTemp;
    @FXML
    private Button btn_Set_Kp;
    @FXML
    private Button btn_Reset_Kp;
    @FXML
    private Button btn_Set_Ki;
    @FXML
    private Button btn_Reset_Ki;
    @FXML
    private Button btn_Set_Kd;
    @FXML
    private Button btn_Reset_Kd;


    //Setting param
    @FXML
    private TextField consigneTemp;
    @FXML
    private TextField Kp;
    @FXML
    private TextField Ki;
    @FXML
    private TextField Kd;


    @FXML
    private LineChart hist;


    @FXML
    public void initialize() {

        //Création du modèle arduino.
        this.arduinoStates = ArduinoStates.getArduinoStates();


        //Bind property
        this.Tp.textProperty().bind(arduinoStates.propertyTp);




        //BTN LISTERNERS


        this.btn_Set_Ki.setOnAction((event) -> {
            // TODO: 21/11/2017
        });
        this.btn_Reset_Ki.setOnAction((event) -> {
            // TODO: 21/11/2017
        });

        this.btn_Set_Kp.setOnAction((event) -> {
            // TODO: 21/11/2017
        });
        this.btn_Reset_Kp.setOnAction((event) -> {
            // TODO: 21/11/2017
        });

        this.btn_Set_Kd.setOnAction((event) -> {
            // TODO: 21/11/2017

        });
        this.btn_Reset_Kd.setOnAction((event) -> {
            // TODO: 21/11/2017

        });

    }

}
