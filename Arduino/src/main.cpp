#include <Arduino.h>

#include "sensor_dht.h"
#include "pid.h"
#include "main.h"



float getTemp(float ohm);
int getPreviousIndex(float coef);
int getNextIndex(float coef);

int sensor = A0;
int fridge = 6;
int R25 = 9411.0;


float refTemp[21] = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
float coefs[21] = {2.8665, 2.2907, 1.8438, 1.492, 1.2154, 1.0, 0.82976, 0.68635, 0.57103, 0.48015, 0.40545, 0.3417, 0.28952, 0.24714, 0.21183, 0.18194, 0.1568, 0.13592, 0.11822, 0.1034, 0.090741};
char orders[4][5]={"Tt","Kp","Ki","Kd"};

Order receiveDatas(){
  Order Sorder;
  int numbOfData = Serial.available();
  if(numbOfData > 0){
    String trame = "";
    for(int i = 0; i < numbOfData; i++){
      trame += (char)Serial.read();
    }

    if(trame.charAt(0) == '<'){

      int cursor = 0;
      String order, value;

      for(int i = 1; i < trame.length() && trame.charAt(i) != ':'; i++){
        order += trame.charAt(i);
        cursor++;
      }
      cursor += 2;
      Sorder.ord = order;

      for(int i = cursor; i < trame.length() && trame.charAt(i) != '>'; i++){
        value += trame.charAt(i);
        cursor++;
      }
      Sorder.value = value.toDouble();
    }
  }
  return Sorder;
}

void setup() {
  pinMode(fridge, OUTPUT);
  startDHT();
  Serial.begin(9600);
}

void loop() {
  float volt = ((analogRead(sensor) * 5.0) / 1023.0);
  double ohm = (10000.0 * volt)/ (5.0 - volt);
  double tempPlaque = getTemp(ohm) * -1;

  setTempPlaque(tempPlaque);

  double humidity = getHumidity();
  double tempAmbiant = getTempAmbiant();

  scanDHT();


//RECEPTION
  Order ordre = receiveDatas();
  if(ordre.ord == "Tt"){
    setTargetTemp(ordre.value);
  }else if(ordre.ord == "Kp"){
    setkp(ordre.value);
  }else if(ordre.ord == "Ki"){
    setki(ordre.value);
  }else if(ordre.ord == "Kd"){
    setkd(ordre.value);
  }

//ENVOIE
Serial.print("<");
Serial.print("Ta:");
Serial.print(tempAmbiant);
Serial.print("|");
Serial.print("Tp:");
Serial.print(getTemp(ohm));
Serial.print("|");
Serial.print("H:");
Serial.print(humidity);
Serial.print("|");
Serial.print("Pr:");
Serial.print(calcRosee(tempAmbiant, humidity));
Serial.print("|");
Serial.print("Pw:");
Serial.print(getOutputVal());
Serial.print("|");
Serial.print("Tt:");
Serial.print(getTargetTemp());
Serial.print("|");
Serial.print("Kp:");
Serial.print(getKp());
Serial.print("|");
Serial.print("Ki:");
Serial.print(getKi());
Serial.print("|");
Serial.print("Kd:");
Serial.print(getKd(), 5);
Serial.println(">");


 if(calcRosee(tempAmbiant, humidity) > getTargetTemp()){
   setSetPoint(calcRosee(tempAmbiant, humidity));
 }else{
   setSetPoint(getTargetTemp());
 }

startPID();

 analogWrite(fridge, getOutputVal());

}

int getNextIndex(float coef){
  int result;
  for(int i = 0; i < sizeof(coefs); i++){
      if(coefs[i] < coef){
        result = i;
        break;
      }
  }

  return result;
}

int getPreviousIndex(float coef){
  int result;
  for(int i = 0; i < sizeof(coefs); i++){
      if(coefs[i] > coef){
        result = i;
      }else{
        break;
      }
  }

  return result;
}

float getTemp(float ohm){
  float coef = ohm / R25;

  int previousIndex = getPreviousIndex(coef);
  int nextIndex = getNextIndex(coef);

  float a = (refTemp[previousIndex] - refTemp[nextIndex]) / (coefs[previousIndex] - coefs[nextIndex]);
  float b = refTemp[previousIndex] - (a * coefs[previousIndex]);

  return a * coef + b;
}
