package Modele;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;

public class ArduinoStates {

    //Valeur mesure
    private DoubleProperty temperaturePlaque;
    private DoubleProperty temperatureAmbiante;
    private DoubleProperty pointRosee;

    //PID
    private DoubleProperty coefP;
    private DoubleProperty coefI;
    private DoubleProperty coefD;

    public double getPointRosee() {
        return pointRosee.get();
    }

    public double getTemperatureAmbiante() {
        return temperatureAmbiante.get();
    }

    public double getTemperaturePlaque() {
        return temperaturePlaque.get();
    }

    public double getCoefD() {
        return coefD.get();
    }

    public double getCoefI() {
        return coefI.get();
    }

    public double getCoefP() {
        return coefP.get();
    }

    public void setCoefD(double coefD) {
        this.coefD.set(coefD);
    }

    public void setCoefI(double coefI) {
        this.coefI.set(coefI);
    }

    public void setCoefP(double coefP) {
        this.coefP.set(coefP);
    }

    public void setPointRosee(double pointRosee) {
        this.pointRosee.set(pointRosee);
    }

    public void setTemperatureAmbiante(double temperatureAmbiante) {
        this.temperatureAmbiante.set(temperatureAmbiante);
    }

    public void setTemperaturePlaque(double temperaturePlaque) {
        this.temperaturePlaque.set(temperaturePlaque);
    }

    public DoubleProperty pointRoseeProperty() {
        return pointRosee;
    }
}
