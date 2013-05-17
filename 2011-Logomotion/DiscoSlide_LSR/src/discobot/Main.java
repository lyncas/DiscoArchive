/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package discobot;

import edu.wpi.first.wpilibj.*;
import Utils.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Main extends IterativeRobot {

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

    public void robotInit() {
        Disabled.robotInit();
    }

    public void disabledInit() {
        Disabled.init();
    }

    public void disabledContinuous() {
        Disabled.continuous();
    }

    public void disabledPeriodic() {
        Disabled.periodic();
    }

    public void autonomousInit() {
        if(Disabled.doubleTubeAuton) {
            DoubleTubeAutonomous.init();
        } else {
            SingleTubeAutonomous.init();
        }
    }

    public void autonomousContinuous() {
        if(Disabled.doubleTubeAuton) {
            DoubleTubeAutonomous.continuous();
        } else {
            SingleTubeAutonomous.continuous();
        }
    }
    public void autonomousPeriodic() {
        if(Disabled.doubleTubeAuton) {
            DoubleTubeAutonomous.periodic();
        } else {
            SingleTubeAutonomous.periodic();
        }
    }

    public void teleopInit() {
        Teleop.init();
    }

    public void teleopContinuous() {
        Teleop.continuous();
    }

    public void teleopPeriodic() {
        Teleop.periodic();
    }
}
