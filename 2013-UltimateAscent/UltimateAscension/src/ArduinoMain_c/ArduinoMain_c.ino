/* 
 * Arduino LED Controller
 * 
 * Byte | Data
 * 1 : Red Value (0 to 255)
 * 2 : Green Value (0 to 255)
 * 3 : Blue Value (0 to 255)
 * 4 : Mode Value (1-Normal,2-Blink)
 * 5 : (optional) Blink Duration bits 16-9
 * 6 : (optional) Blink Duration bits 8-1
 */

#include <Wire.h>

const byte I2C_ADDRESS = 21;
const int LED_PIN_RED = 0;
const int LED_PIN_GREEN = 0;
const int LED_PIN_BLUE = 0;

const byte LED_MODE_NONE = 0;
const byte LED_MODE_BLINK = 1;

byte ledMode = 0;
byte ledR = 255;
byte ledG = 255;
byte ledB = 255;
unsigned int ledDelay = 0;
unsigned long prevTime = 0;

void setup() {
  Wire.begin(I2C_ADDRESS);
  Wire.onReceive(recieveEvent);
  prevTime = millis();
}

void loop() {
  switch (ledMode) {
    case LED_MODE_NONE:
      analogWrite(LED_PIN_RED, ledR);
      analogWrite(LED_PIN_GREEN, ledG);
      analogWrite(LED_PIN_BLUE, ledB);
      break;
    case LED_MODE_BLINK:
      if (millis() - prevTime >= ledDelay) {
//             <---------------------------------------------------------
      }
      break;
  }
  
  delay(50);
}

void recieveEvent(int a) {
  if (a >= 4) {
    ledR = Wire.read();
    ledG = Wire.read();
    ledB = Wire.read();
    byte m = Wire.read();
    if (m == LED_MODE_BLINK) {
      byte a = Wire.read();
      byte b = Wire.read();
      unsigned int d = a << 8 || b;
      ledMode = m;
      ledDelay = d;
    } else {
      ledMode = LED_MODE_NONE;
    }
  } else {
    ledR = 255;
    ledG = 0;
    ledB = 0;
    ledMode = LED_MODE_NONE;
  }
} 
