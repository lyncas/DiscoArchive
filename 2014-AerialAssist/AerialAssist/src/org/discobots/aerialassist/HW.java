package org.discobots.aerialassist;

public class HW {
    
    public static final double driveReduction = 1;
    public static final double wheelRadius = 2;
    public static final double encoderTicksPerRev=256/driveReduction;
    public static final double distancePerRev=2*Math.PI*wheelRadius;
    public static final double distancePerPulse=distancePerRev/encoderTicksPerRev;

    
    /**---------------------------------
     * Motors
     ---------------------------------*/
    public static final int leftRearMotor = 6, rightRearMotor = 1,
                            leftRearMiniMotor = 8, rightRearMiniMotor = 3,
                            leftFrontMotor = 7, rightFrontMotor= 2,
                            leftFrontMiniMotor = 9, rightFrontMiniMotor= 4,
                            rollerMotor = 5, //catapultMotor = 11,
                            //leftRearMotor = 1, rightRearMotor = 2,
                            //leftFrontMotor = 3, rightFrontMotor= 4,
                            //rollerMotor = 5, catapultMotor=7,
                            motorModule=1;

    /**--------------------------------
     * Pneumatics
     ---------------------------------*/
    public static final int /*compressorDigital = 2,*/ compressorRelay = 1,
                             pressureSwitch = 1, spikeReplacementVictor=10,
                             pressureSwitchPrototype = 3, pressureSensor = 5;
    
                           
    //Port 3 is bad so I changed driveShiftASolenoid to 5 and pressureSwitchAnalog to 6.
    // On actual robot, port 4 is bad.
    public static final int extenderSolenoidA = 1, extenderSolenoidB = 5,
                            pneumatapultSolenoidA = 2, pneumatapultSolenoidB = 6,
                            driveShiftSolenoidForward = 8, driveShiftSolenoidReverse = 7,
                            wingsSolenoid = 3,
                            solonoidModule = 1;
    
    /**---------------------------------
     * Sensors
     ---------------------------------*/
    public static final int gyroChannel = 2, chooChooTouchSensor = 11,
                            armLimitSwitchChannel = 6,
                            forwardEncoderA = 2,forwardEncoderB = 3, 
                            sidewayEncoderA = 4,sidewayEncoderB = 5;
}
