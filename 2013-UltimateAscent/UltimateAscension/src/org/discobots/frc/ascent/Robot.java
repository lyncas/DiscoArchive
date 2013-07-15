/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.discobots.frc.ascent;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.discobots.frc.ascent.commands.CommandBase;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public void robotInit() {
        //System.out.println("Initializing Robot.");
        CommandBase.init();
        Dashboard.init();
        this.getWatchdog().setEnabled(false);
    }

    public void autonomousInit() {
        //System.out.println("Initializing Autonomous.");
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        //System.out.println("Initializing Teleop");
    }

    public void teleopPeriodic() {
        long a = System.currentTimeMillis();
        Scheduler.getInstance().run();
        //Dashboard.update();
        long b = System.currentTimeMillis();
        System.out.println(b - a);
        
    }
}
