package org.discobots.aerialassist;

import org.discobots.aerialassist.utils.DiscoGyro;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class HW {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
    
    public static final int leftFrontMotor = 1;
    public static final int leftRearMotor = 4;
    public static final int rightFrontMotor = 2;
    public static final int rightRearMotor = 3;
    
    //public static DiscoGyro gyro = new DiscoGyro(1);
}
