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
     private double Tt;
     private double Kp;
     private double Ki;
     private double Kd;
    private Instant time;//instant de l'état.


    public State(final double Ta, final double Tp, final double H, final double Pr, final double Pw, final double Tt,
                 final double Kp, final double Ki, final double Kd){

        this.Ta = Ta;
        this.Tp = Tp;
        this.H = H;
        this.Pr = Pr;
        this.Pw = Pw;

        //Ordre.
        this.Tt = Tt;
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;


        this.time = Instant.now();

        System.out.println(this);
    }

     public double getTp() {
         return Tp;
     }
 }
