package Modele;

import java.util.*;


public class ArduinoStates implements Observer{

    private Stack<State> stateHistory;

    public ArduinoStates(){
        this.stateHistory = new Stack<>();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.addState((HashMap<String, String>) arg);
    }

    private boolean addState(final HashMap<String, String> serial){

        //Test empty zone in hash
        for (String key : serial.keySet()) {

            if(serial.get(key).isEmpty()){
                return false;

            }
        }


        State state = new State(Double.parseDouble(serial.get("Ta")),
                                Double.parseDouble(serial.get("Tp")),
                                Double.parseDouble(serial.get("H")),
                                Double.parseDouble(serial.get("Pr")),
                                Double.parseDouble(serial.get("Pw")),
                                Double.parseDouble(serial.get("consigneTemp")),
                                Double.parseDouble(serial.get("Kp")),
                                Double.parseDouble(serial.get("Ki")),
                                Double.parseDouble(serial.get("Kd")));

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
