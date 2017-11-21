package Modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;


public class ArduinoStates {

    //Valeur mesure
    private StringProperty temperaturePlaque;
    private StringProperty temperatureAmbiante;
    private StringProperty pointRosee;

    //PID
    private StringProperty coefP;
    private StringProperty coefI;
    private StringProperty coefD;


    public ArduinoStates(){

    }


    public StringProperty getPointRosee() {
        return pointRosee;
    }
    public StringProperty getTemperatureAmbiante() {
        return temperatureAmbiante;
    }
    public StringProperty getTemperaturePlaque() {
        return temperaturePlaque;
    }
    public StringProperty getCoefD() {
        return coefD;
    }
    public StringProperty getCoefI() {
        return coefI;
    }
    public StringProperty getCoefP() {
        return coefP;
    }

}
