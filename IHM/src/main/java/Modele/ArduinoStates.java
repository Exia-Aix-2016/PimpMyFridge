package Modele;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;


public class ArduinoStates implements Observer{

    private Stack<State> stateHistory;

    public StringProperty propertyTp = new SimpleStringProperty();

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
        });

        return true;
    }

    public final State getState(){
        return this.stateHistory.peek();
    }
    @org.jetbrains.annotations.Contract(pure = true)
    public final State getState(final int index){
        return this.stateHistory.get(index);
    }
}
