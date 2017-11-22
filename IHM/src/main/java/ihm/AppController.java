package ihm;

import Modele.ArduinoStates;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
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
    private static AppController instance;


    //Displaying data
    @FXML
    private Label Tp;
    @FXML
    private Label Ta;
    @FXML
    private Label Pr;
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
    public TextField Tt;

    @FXML
    private TextField Kp;
    @FXML
    private TextField Ki;
    @FXML
    private TextField Kd;


    @FXML
    private LineChart hist;

    @FXML
    private Label Tt_actuel;
    @FXML
    private Label Kp_actuel;
    @FXML
    private Label Ki_actuel;
    @FXML
    private Label Kd_actuel;


    @FXML
    public void initialize() {


        //Création du modèle arduino.
        this.arduinoStates = ArduinoStates.getArduinoStates();


        //Bind property
        this.Tp.textProperty().bind(this.arduinoStates.getPropertyTp());
        this.H.textProperty().bind(this.arduinoStates.getPropertyH());
        this.Ta.textProperty().bind(this.arduinoStates.getPropertyTa());
        this.Pr.textProperty().bind(this.arduinoStates.getPropertyPr());

        this.Tt_actuel.textProperty().bind(this.arduinoStates.getPropertyTt());
        this.Kp_actuel.textProperty().bind(this.arduinoStates.getPropertyKp());
        this.Ki_actuel.textProperty().bind(this.arduinoStates.getPropertyKi());
        this.Kd_actuel.textProperty().bind(this.arduinoStates.getPropertyKd());




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
