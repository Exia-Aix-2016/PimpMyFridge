package ihm;

import Modele.ArduinoStates;
import Modele.ChartData;
import Utils.RunnableSerial;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.sound.midi.Soundbank;
import java.util.HashMap;


/**
 * Controller de la vue de l'application
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
    private Button btn_Set_Kp;

    @FXML
    private Button btn_Set_Ki;

    @FXML
    private Button btn_Set_Kd;


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

    //private ObservableList<String> XaxisCategories = FXCollections.observableArrayList();
    private int TickResolution = 100;


    /**
     * Initialisation IHM
     * @see javafx
     * */
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

        //Configure axis
        NumberAxis XAxis  = (NumberAxis)hist.getXAxis();
        NumberAxis YAxis  = (NumberAxis)hist.getYAxis();

        XAxis.setAutoRanging(false);
        YAxis.setAutoRanging(false);

        YAxis.setLowerBound(0);
        YAxis.setUpperBound(30);

        XAxis.setTickLabelsVisible(false);

        //Configure series
        XYChart.Series serieTa = new XYChart.Series();
        XYChart.Series serieTp = new XYChart.Series();
        XYChart.Series serieH = new XYChart.Series();
        XYChart.Series seriePr = new XYChart.Series();
        XYChart.Series seriePw = new XYChart.Series();

        serieTa.setName("Temperature ambiante");
        serieTp.setName("Temperature plaque");
        serieH.setName("Humiditee");
        seriePr.setName("Point de rosee");
        seriePw.setName("Alimentation");

        this.hist.getData().add(serieTa);
        this.hist.getData().add(serieTp);
        this.hist.getData().add(serieH);
        this.hist.getData().add(seriePr);
        this.hist.getData().add(seriePw);

        //Update series
        this.arduinoStates.getChartData().addListener((ListChangeListener.Change<? extends ChartData> c) -> {
            c.next();
            if (c.wasAdded()) {
                for (ChartData added: c.getAddedSubList()) {
                    serieTa.getData().add(new XYChart.Data<>(added.tick, added.Ta));
                    serieTp.getData().add(new XYChart.Data<>(added.tick, added.Tp));
                    serieH.getData().add(new XYChart.Data<>(added.tick, added.H));
                    seriePr.getData().add(new XYChart.Data<>(added.tick, added.Pr));
                    seriePw.getData().add(new XYChart.Data<>(added.tick, added.Pw));

                    if (added.tick > TickResolution) {
                        XAxis.setLowerBound(added.tick-TickResolution);
                        XAxis.setUpperBound(added.tick);

                        serieTa.getData().remove(0, 1);
                        serieTp.getData().remove(0, 1);
                        serieH.getData().remove(0, 1);
                        seriePr.getData().remove(0, 1);
                        seriePw.getData().remove(0, 1);
                    }
                }
            }
        });


        //BTN LISTERNERS/Send Order

        //PID : Ki
        this.btn_Set_Ki.setOnAction((event) -> {
            String txt = this.Ki.getText();
            try {
                Double val = Double.parseDouble(txt);
                if (val >= 0) {
                    RunnableSerial.getInstance().write("<Ki:"+txt+">");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    });

        //PID : Kp
        this.btn_Set_Kp.setOnAction((event) -> {
            String txt = this.Kp.getText();
            try {
                Double val = Double.parseDouble(txt);
                if (val >= 0) {
                    RunnableSerial.getInstance().write("<Kp:"+txt+">");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //PID : Kd
        this.btn_Set_Kd.setOnAction((event) -> {
            String txt = this.Kd.getText();
            try {
                Double val = Double.parseDouble(txt);
                if (val >= 0) {
                    RunnableSerial.getInstance().write("<Kd:"+txt+">");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //Temperature Target
        this.btn_Set_Tt.setOnAction((event) -> {
            String txt = this.Tt.getText();
            try {
                Double val = Double.parseDouble(txt);
                if (val >= 0) {
                    RunnableSerial.getInstance().write("<Tt:"+txt+">");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

}
