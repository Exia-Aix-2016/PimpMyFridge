#ifndef MAIN_
#define MAIN_
typedef struct Order{
  String ord;
  double value;
} Order;
int getNextIndex(float coef);
int getPreviousIndex(float coef);
float getTemp(float ohm);
Order receiveDatas();

#endif
