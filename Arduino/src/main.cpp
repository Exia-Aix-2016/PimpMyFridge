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

void receiveDatas(){
  int numbOfData = Serial.available();
  char charRead[numbOfData];
  bool openedOrder = false;
  char value;
  char order[32];
  char valu[32];
  int sizeOrder = 0;
  int sizeValue = 0;
  int cursor = 0;

  for(int i = 0; i < numbOfData; i++){
    charRead[i] = Serial.read();
  }

  if(charRead[0] == '<'){
    openedOrder = true;
    cursor = 1;
  }



  if(openedOrder == true){
    String orderStr = "";
    String valueStr = "";

    int k=0;
    for(int i=cursor; charRead[i] != ':'; i++){
      order[k] = charRead[i];
      cursor++;
      sizeOrder++;
      k++;


      Serial.println("Ok2");
    }
    cursor++;
    for(int i=0; i < sizeOrder; i++){
      orderStr = orderStr + order[i];
      Serial.println("Ok3");
    }

    int j=0;
    for(int i=cursor; charRead[i] != '>'; i++){
      Serial.println("Ok4");
      valu[j] = charRead[i];
      sizeValue++;
      j++;
    }
    for(int i=0; i < sizeValue; i++){
      Serial.println("Ok5");
      valueStr = valueStr + valu[i];
    }

    double valueDbl = valueStr.toDouble();



    Serial.print(orderStr);
    Serial.print(" ");
    Serial.println(valueDbl);
    Serial.println("Ok6");


    if(orderStr == orders[0]){
      Serial.println("Order tt");
    }

  }


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

  receiveDatas();


/*Serial.print("<");
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
Serial.print(getSetPoint() * -1);
Serial.print("|");
Serial.print("Kp:");
Serial.print(getKp());
Serial.print("|");
Serial.print("Ki:");
Serial.print(getKi());
Serial.print("|");
Serial.print("Kd:");
Serial.print(getKd());
Serial.println(">");*/


  if (Serial.available() > 0) {
   setTargetTemp(Serial.parseInt());
   setSetPoint(getTargetTemp());
 }else if(calcRosee(tempAmbiant, humidity) > getTargetTemp()){
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
