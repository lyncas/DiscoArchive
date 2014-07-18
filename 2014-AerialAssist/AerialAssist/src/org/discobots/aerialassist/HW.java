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
    public static final int motorModule = 1;
    public static final int leftRearMotor = 6, rightRearMotor = 1,
                            leftFrontMotor = 7, rightFrontMotor= 2;
    public static final int leftRearMiniMotor = 8, rightRearMiniMotor = 3,
                            leftFrontMiniMotor = 9, rightFrontMiniMotor= 4;
    public static final int intakeMotor = 5;
    
    /**--------------------------------
     * Relays
     ---------------------------------*/
    public static final int compressorRelay = 1;
    public static final int spikeReplacementVictor = 10;
    
    /**--------------------------------
     * Solenoids
     ---------------------------------*/
    // Port 3 & 4 are bad on actual robot.
    public static final int intakeSolenoidA = 1, intakeSolenoidB = 5;
    public static final int launcherSolenoidA = 2, launcherSolenoidB = 6,
                            launcherSolenoidC = 7;
    
    /**---------------------------------
     * Sensors
     ---------------------------------*/
    public static final int forwardEncoderA = 10,forwardEncoderB = 11;
    public static final int  pressureSwitch = 1, pressureSensor = 5;
    
    /**---------------------------------
     * Outputs
     ---------------------------------*/
    public static final int ledRelay = 8;
}
 