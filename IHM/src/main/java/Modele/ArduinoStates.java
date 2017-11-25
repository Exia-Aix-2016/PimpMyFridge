package Modele;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

/**
 * Gestionnaire des états de l'arduino
 * Chaque changement d'état de l'arduino (température, humidité, etc...)
 * est géré par ce Gestionnaire.
 * Il s'agit d'un observer qui observera RunnableSerial
 * @see State
 * @see Observer
 * @see Utils.RunnableSerial
 * */
public class ArduinoStates implements Observer{

    private Stack<State> stateHistory;

    private StringProperty propertyTp = new SimpleStringProperty();
    private StringProperty propertyTa = new SimpleStringProperty();
    private StringProperty propertyH = new SimpleStringProperty();
    private StringProperty propertyPr = new SimpleStringProperty();
    private StringProperty propertyPw = new SimpleStringProperty();
    private StringProperty propertyTt = new SimpleStringProperty();
    private StringProperty propertyKp = new SimpleStringProperty();
    private StringProperty propertyKi = new SimpleStringProperty();
    private StringProperty propertyKd = new SimpleStringProperty();

    private ObservableList<ChartData> chartData = FXCollections.observableArrayList();

    private static ArduinoStates instance;

    private ArduinoStates(){
        this.stateHistory = new Stack<>();
    }

    /**
     * Il s'agit d'un Singleton
     * @return l'instance ArduinoStates.
     */
    public static ArduinoStates getArduinoStates() {
        if (ArduinoStates.instance == null) {
            ArduinoStates.instance = new ArduinoStates();
        }
        return ArduinoStates.instance;
    }

    /**
     * Quand l'arduino emet des données l'observable RunnableSerial les récupère et
     * notifiera l'arduinoState qui créera un nouvel etat
     * @see Utils.RunnableSerial
     * @see Observer
     * @see Observable
     * @param o objet observé
     * @param arg données envoyé par l'observable
     */
    @Override
    public void update(Observable o, Object arg) {
            this.addState((HashMap<String, Double>) arg);
    }

    /**
     * Permet de rajouter un Etat. (permettra d'avoir un historique des mesures de l'arduino)
     * @param serial Données serialisé de l'arduino
     * @return true si l'intégrité des données est vérifiée.
     */
    private boolean addState(final HashMap<String, Double> serial){

        //Création du nouveau state.
        State state = new State(serial.get("Ta"),
                                serial.get("Tp"),
                                serial.get("H"),
                                serial.get("Pr"),
                                serial.get("Pw"),
                                serial.get("Tt"),
                                serial.get("Kp"),
                                serial.get("Ki"),
                                serial.get("Kd"));

        Platform.runLater(() -> {
            this.propertyTp.setValue(String.valueOf(state.getTp()));
            this.propertyH.setValue(String.valueOf(state.getH()));
            this.propertyTa.setValue(String.valueOf(state.getTa()));
            this.propertyKp.setValue(String.valueOf(state.getKp()));
            this.propertyKi.setValue(String.valueOf(state.getKi()));
            this.propertyKd.setValue(String.valueOf(state.getKd()));

            this.propertyPr.setValue(String.valueOf(state.getPr()));
            this.propertyPw.setValue(String.valueOf(state.getPw()));
            this.propertyTt.setValue(String.valueOf(state.getTt()));

            this.chartData.add(new ChartData(state));
        });

        this.stateHistory.push(state);

        if (stateHistory.size() > 150) {
            stateHistory.remove(0);
        }

        return true;
    }

    /**
     * @return Le dernier état enregistré.
     */
    public final State getState(){
        return this.stateHistory.peek();
    }

    /**
     *
     * @param index de l'état souhaité
     * @return l'état à l'index indiqué.
     */
    @org.jetbrains.annotations.Contract(pure = true)
    public final State getState(final int index){
        return this.stateHistory.get(index);
    }


    //GETTER
    /**
     * @return Température ambiante
     * @see StringProperty
     */
    public StringProperty getPropertyTa() {
        return this.propertyTa;
    }
    /**
     * @return Température plaque
     * @see StringProperty
     */
    public StringProperty getPropertyTp() {
        return this.propertyTp;
    }
    /**
     * @return Humidité
     * @see StringProperty
     */
    public StringProperty getPropertyH() {
        return this.propertyH;
    }

    /**
     * @return Point de rosée
     * @see StringProperty
     */
    public StringProperty getPropertyPr() {
        return this.propertyPr;
    }

    /**
     * @return Température cible
     * @see StringProperty
     */
    public StringProperty getPropertyTt() {
        return this.propertyTt;
    }

    /**
     * @return PID : coefficient p
     * @see StringProperty
     */
    public StringProperty getPropertyKp() {
        return propertyKp;
    }
    /**
     * @return PID : coefficient i
     * @see StringProperty
     */
    public StringProperty getPropertyKi() {
        return propertyKi;
    }
    /**
     * @return PID : coefficient d
     * @see StringProperty
     */
    public StringProperty getPropertyKd() {
        return propertyKd;
    }

    /**
     * @return Une liste de données observable permettant de dessiner les données sur le graphique.
     * @see StringProperty
     * @see ChartData
     * @see ObservableList
     */
    public ObservableList<ChartData> getChartData() {
        return chartData;
    }
}