package org.discobots.aerialassist;

import org.discobots.aerialassist.utils.AngleController;
import org.discobots.aerialassist.commands.CommandBase;


public class HW {

    /**---------------------------------
     * Motors
     ---------------------------------*/
    public static final int leftRearMotor = 1, rightRearMotor = 2,
                            leftFrontMotor = 3, rightFrontMotor= 4,
<<<<<<< HEAD
                            rollerMotor = 5;
    public static final int compressorRelay = 1, pressureSwitchAnalog = 6,
                            pressureSwitchSlot=1, pressureSwitchChannel=1, 
            //Port 3 is bad so I changed driveShiftASolenoid to 5 and pressureSwitchAnalog to 6.
                            driveShiftASolenoid = 5, driveShiftBSolenoid = 4,
                            extenderASolenoid = 1, extenderBSolenoid = 2;
=======
                            rollerMotor = 5,
                            motorModule = 1;
    /**---------------------------------
     * Pneumatics
     ---------------------------------*/
    public static final int /*compressorDigital = 2,*/ compressorRelay = 8,
                             pressureSwitchAnalog = 6;
                           
    //Port 3 is bad so I changed driveShiftASolenoid to 5 and pressureSwitchAnalog to 6.
    public static final int extenderSolenoid = 1, 
                            driveShiftSolenoidForward = 7, driveShiftSolenoidReverse = 6, 
                            solonoidModule = 1;
>>>>>>> fe939c7a57b87e6580649bd80549a40324e01691
    
    /**---------------------------------
     * Sensors
     ---------------------------------*/
    public static final int gyroChannel = 2, accelModule = 1; //I2C on digital module 1
}
