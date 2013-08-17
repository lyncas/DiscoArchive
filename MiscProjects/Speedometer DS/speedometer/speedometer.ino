#include <Servo.h> 

Servo drDre;
int shooterSpeed = 0; // recieved value from laptop, 0-9
int speedometerSpeed = 0; // value sent to Motor Controller 29
int x = 0; // addition math variable
int statusLed = 6; // status light pin
int lightLed = 7; // lights status
int soundLed = 8; // sound status
int compressorLed = 10; //compressor status
int lightswitchState = 0; // light switch state
int soundswitchState = 0; // sound switch state
const int lightSwitch = 2; // light switch pin
const int soundSwitch = 4; // sound switch pin
String compressorState = b; // compressor status
void setup()
  {
    Serial.begin(9600); // Sets up communication with the serial monitor
    drDre.attach(9); 
    pinMode(statusLed, OUTPUT);
    pinMode(lightLed, OUTPUT);
    pinMode(soundLed, OUTPUT);
    pinMode(compressorLed, OUTPUT);
    pinMode(lightSwitch, INPUT);
    pinMode(soundSwitch, INPUT);  
}

void loop()
  {
     if (Serial.available()>0) // Checks for a character in the serial monitor
       {
         digitalWrite(led, HIGH);
         int shooterSpeed = Serial.parseInt();
           
           if (shooterSpeed = 0)
             {
               drDre.write(90);
             }
           else
             {
               int x = shooterSpeed * 2;
               int speedometerSpeed = x + 110;
               drDre.write(speedometerSpeed);
             }
       }
     else   // blink until signal is detected
        {
           digitalWrite(statusLed, HIGH);
           delay(1000);
           digitalWrite(statusLed, LOW);
           delay(1000);
        }
     compressorState = Serial.read();
       if (compressorState = "a")
         {
           digitalWrite(compressorLed, HIGH);
         }
       if (compressorState = "b")
         {
           digitalWrite(compressorLed, LOW);
         }
      lightswitchState = digitalRead(lightSwitch);
      soundswitchState = digitalRead(soundSwitch);
      if (lightswitchState && soundswitchState == HIGH) // conditions for switch operations
        {
          Serial.println("0");
          digitalWrite(lightLed, HIGH);
          digitalWrite(soundLed, HIGH);
        }
      if (lightswitchState && soundswitchState == LOW)
        {
          Serial.println("1");
          digitalWrite(lightLed, LOW);
          digitalWrite(soundLed, LOW);
        }
      if (lightswitchState == HIGH && soundswitchState == LOW)
        {
          Serial.println("2");
          digitalWrite(lightLed, HIGH);
          digitalWrite(soundLed, LOW);
        }
      if (lightswitchState == LOW && soundswitchState == HIGH) 
        {
          Serial.println("3");
          digitalWrite(lightLed, LOW);
          digitalWrite(soundLed, HIGH);
        }
      
}


