#include <Arduino.h>
#include <AutoPID.h>
#include <DHT.h>
#include <math.h>

#define OUTPUT_MIN 0
#define OUTPUT_MAX 255
#define KP 250
#define KI 1.5
#define KD 0.001
#define DHTPIN 2
#define DHTTYPE DHT22



float getTemp(float ohm);
int getPreviousIndex(float coef);
int getNextIndex(float coef);

int sensor = A0;
int fridge = 6;
int R25 = 9411.0;
int targetTemp = 12;

float refTemp[21] = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
float coefs[21] = {2.8665, 2.2907, 1.8438, 1.492, 1.2154, 1.0, 0.82976, 0.68635, 0.57103, 0.48015, 0.40545, 0.3417, 0.28952, 0.24714, 0.21183, 0.18194, 0.1568, 0.13592, 0.11822, 0.1034, 0.090741};

double tempPlaque, outputVal;
double setPoint = -12;

double alpha;
double a = 17.27;
double b = 237.7;
double rosee;


AutoPID PID(&tempPlaque, &setPoint, &outputVal, OUTPUT_MIN, OUTPUT_MAX, KP, KI, KD);
DHT dht(DHTPIN, DHTTYPE);

void setup() {
  pinMode(fridge, OUTPUT);
  Serial.println("DHT22 test!");
  dht.begin();
  Serial.begin(9600);
}

void loop() {
  float volt = ((analogRead(sensor) * 5.0) / 1023.0);
  double ohm = (10000.0 * volt)/ (5.0 - volt);
  tempPlaque = getTemp(ohm) * -1;



  delay(2000);

 double humidity = dht.readHumidity();
 double tempAmbiant = dht.readTemperature();


 if (isnan(humidity) || isnan(tempAmbiant))
   Serial.println("Failed to read from DHT sensor");


  alpha = ((a * tempAmbiant) / (b + tempAmbiant)) + log(humidity/100);
  rosee = (b * alpha) / (a - alpha);


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
 Serial.print(outputVal);
 Serial.print("/255  ");
 Serial.print("Point de rosee: ");
 Serial.print(rosee);
 Serial.println(" *C");

 if (Serial.available() > 0) {
   setPoint = Serial.parseInt() * -1;
 }else{
   setPoint = rosee * -1;
 }

 PID.run();
 analogWrite(fridge, outputVal);


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
