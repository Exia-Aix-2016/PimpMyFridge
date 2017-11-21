#include <Arduino.h>

#include "sensor_dht.h"
#include "pid.h"

float getTemp(float ohm);
int getPreviousIndex(float coef);
int getNextIndex(float coef);

int sensor = A0;
int fridge = 6;
int R25 = 9411.0;
int targetTemp = 12;

float refTemp[21] = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
float coefs[21] = {2.8665, 2.2907, 1.8438, 1.492, 1.2154, 1.0, 0.82976, 0.68635, 0.57103, 0.48015, 0.40545, 0.3417, 0.28952, 0.24714, 0.21183, 0.18194, 0.1568, 0.13592, 0.11822, 0.1034, 0.090741};



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

  delay(2000);

  checkDHT();

  double humidity = getHumidity();
  double tempAmbiant = getTempAmbiant();

Serial.print("Humidite: ");
Serial.print(humidity);
Serial.print(" \%  ");
Serial.print("Temperature ambiante: ");
Serial.print(tempAmbiant);
Serial.print(" *C  ");
Serial.print("Temperature plaque: ");
Serial.print(getTemp(ohm));
Serial.print(" *C  ");
Serial.print("Puissance envoyée: ");
Serial.print(getOutputVal());
Serial.print("/255  ");
Serial.print("Point de rosee: ");
Serial.print(calcRosee(tempAmbiant, humidity));
Serial.println(" *C");

  if (Serial.available() > 0) {
   setSetPoint(Serial.parseInt() * -1);
 }else{
   setSetPoint(calcRosee(tempAmbiant, humidity) * -1);
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
