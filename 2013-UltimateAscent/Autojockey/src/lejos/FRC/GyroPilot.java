/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lejos.FRC;

import disco.utils.DiscoGyro;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.CompassPilot;

/**
 *
 * @author Developer
 */
public class GyroPilot extends CompassPilot {

    public GyroPilot(DiscoGyro gyro, float wheelDiameter,
            float trackWidth, RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
        this(gyro, wheelDiameter, trackWidth, leftMotor, rightMotor, false);
    }

    public GyroPilot(DiscoGyro gyro, float wheelDiameter,
            float trackWidth, RegulatedMotor leftMotor, RegulatedMotor rightMotor, boolean reverse) {
        super(gyro, wheelDiameter, trackWidth, leftMotor, rightMotor, reverse);
    }
    
    public synchronized void calibrate(){
        System.out.println("Cannot reset gyro");
}
}
