#include "pid.h"
#include <AutoPID.h>

#define OUTPUT_MIN 0
#define OUTPUT_MAX 255
double KP = 250;
double KI = 1.5;
double KD = 0.001;
double tempPlaque, outputVal;
double setPoint = 0;
double targetTemp = 12;

AutoPID PID(&tempPlaque, &setPoint, &outputVal, OUTPUT_MIN, OUTPUT_MAX, KP, KI, KD);

void startPID(){
  PID.run();
}

double getOutputVal(){
  return outputVal;
}

void setSetPoint(double setPointIn){
  if(setPointIn > 0){
    setPoint = setPointIn * -1;
  }else{
    setPoint = setPointIn;
  }

}

double getSetPoint(){
  return setPoint;
}

void setTempPlaque(double tempPlaqueIn){
  tempPlaque = tempPlaqueIn;
}

double getKp(){
  return KP;
}

double getKi(){
  return KI;
}

double getKd(){
  return KD;
}

void setkp(const double p){
  KP = p;
}
void setki(const double i){
  KI = i;
}
void setkd(const double d){
  KD = d;
}

void setTargetTemp(double targetTempIn){
  targetTemp = targetTempIn;
}

double getTargetTemp(){
  return targetTemp;
}
