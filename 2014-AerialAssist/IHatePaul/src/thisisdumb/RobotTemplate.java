/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package thisisdumb;


import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    Talon leftFront, leftRear, rightFront, rightRear;
    RobotDrive drive;
    
    public static final int leftFrontMotor= 3, rightFrontMotor= 4,
                            leftRearMotor = 1, rightRearMotor = 2;
    
    GamePad potato;
    
    public void operatorControl() {
        potato = new GamePad(1,GamePad.MODE_D);
        
        leftFront = new Talon(1, leftFrontMotor);
        leftRear = new Talon(1, leftRearMotor);
        rightFront = new Talon(1, rightFrontMotor);
        rightRear = new Talon(1, rightRearMotor);
        drive = new RobotDrive(leftFront, leftRear, rightFront, rightRear);

        drive.setSafetyEnabled(false);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        //drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        
        while(true) {
            double left = potato.getLY();
            double right = -potato.getRY();
            /*if (Math.abs(left) < 0.5) {
                left = 0;
            }
            if (Math.abs(right) < 0.5) {
                right = 0;
            }*/
            drive.tankDrive(left, right);
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
}
