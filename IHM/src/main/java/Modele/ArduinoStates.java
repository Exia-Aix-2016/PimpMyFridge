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

    public static ArduinoStates getArduinoStates() {
        if (ArduinoStates.instance == null) {
            ArduinoStates.instance = new ArduinoStates();
        }

        return ArduinoStates.instance;
    }

    @Override
    public void update(Observable o, Object arg) {

            this.addState((HashMap<String, Double>) arg);

    }

    private boolean addState(final HashMap<String, Double> serial){

        System.out.println(serial);

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

    public final State getState(){
        return this.stateHistory.peek();
    }
    @org.jetbrains.annotations.Contract(pure = true)
    public final State getState(final int index){
        return this.stateHistory.get(index);
    }


    //GETTER
    public StringProperty getPropertyTa() {
        return this.propertyTa;
    }
    public StringProperty getPropertyTp() {
        return this.propertyTp;
    }
    public StringProperty getPropertyH() {
        return this.propertyH;
    }

    public StringProperty getPropertyPr() {
        return this.propertyPr;
    }

    public StringProperty getPropertyTt() {
        return this.propertyTt;
    }

    public StringProperty getPropertyKp() {
        return propertyKp;
    }
    public StringProperty getPropertyKi() {
        return propertyKi;
    }
    public StringProperty getPropertyKd() {
        return propertyKd;
    }

    public ObservableList<ChartData> getChartData() {
        return chartData;
    }
}