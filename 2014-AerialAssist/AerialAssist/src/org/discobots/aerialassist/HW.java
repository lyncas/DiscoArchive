package org.discobots.aerialassist;

public class HW {

    /**---------------------------------
     * Motors
     ---------------------------------*/
    public static final int leftRearMotor = 1, rightRearMotor = 6,
                            leftRearMiniMotor = 3, rightRearMiniMotor = 8,
                            leftFrontMotor = 2, rightFrontMotor= 7,
                            leftFrontMiniMotor = 4, rightFrontMiniMotor= 9,
                            rollerMotor = 5, //catapultMotor = 11,
                            //leftRearMotor = 1, rightRearMotor = 2,
                            //leftFrontMotor = 3, rightFrontMotor= 4,
                            //rollerMotor = 5, catapultMotor=7,
                            motorModule=1;

    /**---------------------------------
     * Pneumatics
     ---------------------------------*/
    public static final int /*compressorDigital = 2,*/ compressorRelay = 1,
                             pressureSwitch = 1, spikeReplacementVictor=10,
                             pressureSwitchPrototype = 3;
    
                           
    //Port 3 is bad so I changed driveShiftASolenoid to 5 and pressureSwitchAnalog to 6.
    public static final int extenderSolenoidA = 1, extenderSolenoidB = 5,
                            pneumatapultSolenoidA = 2, pneumatapultSolenoidB = 6,
                            driveShiftSolenoidForward = 8, driveShiftSolenoidReverse = 7, 
                            solonoidModule = 1;
    
    /**---------------------------------
     * Sensors
     ---------------------------------*/
    public static final int gyroChannel = 2, chooChooTouchSensor = 11,
                            armLimitSwitchChannel = 4,
                            forwardEncoderA = 1,forwardEncoderB = 2,
                            sidewayEncoderA = 3,sidewayEncoderB = 4;
}
