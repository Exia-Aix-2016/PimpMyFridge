package ihm;

import Modele.ArduinoStates;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Utils.RunnableSerial;

import java.util.HashMap;


/**
 * Controller de l'application
 */
public class AppController {

    private ArduinoStates arduinoStates;

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
    private Button btn_Set_Tt;
    @FXML
    private Button btn_Reset_Tt;
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


    private HashMap<String, Double> createAnOrder(String key, Double value){

        return null;
    }

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



        XYChart.Series series = new XYChart.Series();

        series.setName("Temp");
        this.hist.getData().add(series);

        this.arduinoStates.getSerieTp().addListener((ListChangeListener.Change<? extends XYChart.Data> c) -> {
            c.next();
            if (c.wasAdded()) {
                for (XYChart.Data added: c.getAddedSubList()) {
                    series.getData().add(added);
                }
            }
        });


        //BTN LISTERNERS



        //PID : Ki
        this.btn_Set_Ki.setOnAction((event) -> {
            RunnableSerial.getInstance().write("<Ki:"+ this.Ki.getText()+">");
        });
        this.btn_Reset_Ki.setOnAction((event) -> {
            RunnableSerial.getInstance().write("<Ki:-1>");
        });

        //PID : Kp
        this.btn_Set_Kp.setOnAction((event) -> {
            RunnableSerial.getInstance().write("<Kp:"+ this.Kp.getText() +">");
        });
        this.btn_Reset_Kp.setOnAction((event) -> {
            RunnableSerial.getInstance().write("<Kp:-1>");
        });

        //PID : Kd
        this.btn_Set_Kd.setOnAction((event) -> {
            RunnableSerial.getInstance().write("<Kd:"+ this.Kd.getText()+">");
        });
        this.btn_Reset_Kd.setOnAction((event) -> {
            RunnableSerial.getInstance().write("<Kd:-1>");
        });

        //Temperature Target
        this.btn_Set_Tt.setOnAction((event) -> {
            RunnableSerial.getInstance().write("<Tt:"+ this.Tt.getText() +">");

        });
        this.btn_Reset_Tt.setOnAction((event) -> {
            RunnableSerial.getInstance().write("<Tt:-1>");
        });

    }

}
