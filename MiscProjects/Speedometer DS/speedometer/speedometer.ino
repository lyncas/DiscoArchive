#include <Servo.h> 

Servo drDre;
int shooterSpeed = 0; // recieved value from laptop, 0-9
int speedometerSpeed = 0; // value sent to Motor Controller 29
int x = 0; // addition math variable
int led = 6; // status light pin
int lightswitchState = 0; // light switch state
int soundswitchState = 0; // sound switch state
const int lightSwitch = 2; // light switch pin
const int soundSwitch = 4; // sound switch pin

void setup()
  {
    Serial.begin(9600); // Sets up communication with the serial monitor
    drDre.attach(9); 
    pinMode(led, OUTPUT);
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
           digitalWrite(led, HIGH);
           delay(1000);
           digitalWrite(led, LOW);
           delay(1000);
        }
      lightswitchState = digitalRead(lightSwitch);
      soundswitchState = digitalRead(soundSwitch);
      if (lightswitchState && soundswitchState == HIGH) // conditions for switch operations
        {
          Serial.println("0");
        }
      if (lightswitchState && soundswitchState == LOW)
        {
          Serial.println("1");
        }
      if (lightswitchState == HIGH && soundswitchState == LOW)
        {
          Serial.println("2");
        }
      if (lightswitchState == LOW && soundswitchState == HIGH) 
        {
          Serial.println("3");
        }
      
}


