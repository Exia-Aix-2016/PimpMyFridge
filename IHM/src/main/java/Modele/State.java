package Modele;

import javafx.beans.property.DoubleProperty;

import java.time.*;
 public class State {


    private double Ta;//Température ambiante
    private double Tp;//Température plaque
    private double H;//Humidité
    private double Pr;//Point de rosée
    private double Pw;//Puissance envoyé

     //Ordre
     private double consigneTemp;
     private double Kp;
     private double Ki;
     private double Kd;
    private Instant time;//instant de l'état.


    public State(final double Ta, final double Tp, final double H, final double Pr, final double Pw, final double consigneTemp,
                 final double Kp, final double Ki, final double Kd){

        this.Ta = Ta;
        this.Tp = Tp;
        this.H = H;
        this.Pr = Pr;
        this.Pw = Pw;

        //Ordre.
        this.consigneTemp = consigneTemp;
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;


        this.time = Instant.now();
    }
}
