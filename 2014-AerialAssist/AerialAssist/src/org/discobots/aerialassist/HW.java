package org.discobots.aerialassist;

import org.discobots.aerialassist.controllers.AngleController;

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
    /**---------------------------------
     * Motors
     ---------------------------------*/
    public static final int leftFrontMotor= 1, rightFrontMotor= 2,
                            leftRearMotor = 4, rightRearMotor = 3;
    
    /**---------------------------------
     * Controllers / PIDs
     ---------------------------------*/
    public static final AngleController leftFrontController = new AngleController(0.5,0.0,0.0);
    public static final AngleController leftRearController = new AngleController(0.5,0.0,0.0);
    public static final AngleController rightFrontController = new AngleController(0.5,0.0,0.0);
    public static final AngleController rightRearController = new AngleController(0.5,0.0,0.0);

    /**---------------------------------
     * Gyro / Accelerometer
     ---------------------------------*/
    public static final int gyroChannel = 1;
    public static final int accelModule = 1; //I2C on digital module 1
}
