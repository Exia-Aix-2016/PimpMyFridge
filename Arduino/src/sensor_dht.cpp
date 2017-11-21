#include "sensor_dht.h"
#include <DHT.h>
#include <math.h>

#define DHTPIN 2
#define DHTTYPE DHT22

double alpha;
double a = 17.27;
double b = 237.7;
double rosee;
double humidity;
double tempAmbiant;

DHT dht(DHTPIN, DHTTYPE);

void checkDHT(){
  if (isnan(humidity) || isnan(tempAmbiant)){
    Serial.println("Failed to read from DHT sensor");
  }
}

void startDHT(){
  dht.begin();
}

double calcRosee(double tempAmbiant, double humidity){
  alpha = ((a * tempAmbiant) / (b + tempAmbiant)) + log(humidity/100);
  rosee = (b * alpha) / (a - alpha);
  return rosee;
}

double getHumidity(){
  return dht.readHumidity();
}

double getTempAmbiant(){
  return dht.readTemperature();
}
