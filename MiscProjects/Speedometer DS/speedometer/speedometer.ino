#include <Servo.h> 

Servo drDre;
int shooterSpeed = 0; // recieved value from laptop, 0-9
int speedometerSpeed = 0; // value sent to Motor Controller 29
int x = 0; // addition math variable
int led = 6;

void setup()
  {
    Serial.begin(9600); // Sets up communication with the serial monitor
    drDre.attach(9);
    pinMode(led, OUTPUT);
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
}


