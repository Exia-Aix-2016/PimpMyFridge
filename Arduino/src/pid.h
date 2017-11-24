#ifndef PID_
#define PID_


void startPID();
double getOutputVal();
void setSetPoint(double);
double getSetPoint();
void setTempPlaque(double);
double getKp();
double getKi();
double getKd();
 void setkp(const double p);
 void setki(const double i);
 void setkd(const double d);
void setTargetTemp(double);
double getTargetTemp();

#endif
