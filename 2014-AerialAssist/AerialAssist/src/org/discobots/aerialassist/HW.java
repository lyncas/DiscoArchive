package org.discobots.aerialassist;

import org.discobots.aerialassist.utils.AngleController;
import org.discobots.aerialassist.commands.CommandBase;

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
    public static final int leftFrontMotor= 3, rightFrontMotor= 4,
                            leftRearMotor = 1, rightRearMotor = 2;
    
    /**---------------------------------
     * Controllers / PIDs
     ---------------------------------*/
    public static final AngleController angleController = new AngleController(0.5,0.0,0.0,CommandBase.drivetrain.getGyro());

    /**---------------------------------
     * Gyro / Accelerometer
     ---------------------------------*/
    public static final int gyroChannel = 1;
    public static final int accelModule = 1; //I2C on digital module 1
}
