#include <Servo.h> 

Servo drDre;
int shooterSpeed = 0; // recieved value from laptop, 0-9
int speedometerSpeed = 0; // value sent to Motor Controller 29
int x = 0; // addition math variable
void setup()
  {
    Serial.begin(9600); // Sets up communication with the serial monitor
    drDre.attach(9);
  }

void loop()
  {
   if (Serial.available()>0) // Checks for a character in the serial monitor
     {
       int shooterSpeed = Serial.parseInt();
       int x = shooterSpeed * 10;
       int speedometerSpeed = x + 90;
       drDre.write(speedometerSpeed);
       
     }
}


