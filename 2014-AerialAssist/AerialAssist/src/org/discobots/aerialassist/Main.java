/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.discobots.aerialassist;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.utils.Dashboard;
import org.discobots.aerialassist.OI;
import org.discobots.aerialassist.commands.Autonomous;
import org.discobots.aerialassist.commands.drive.AutonomousStaticDrive;
import org.discobots.aerialassist.commands.drive.SwitchDrive;
import org.discobots.aerialassist.commands.upperbody.ToggleArm;
import org.discobots.aerialassist.subsystems.Drivetrain;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Main extends IterativeRobot {
    
    SendableChooser autonomousChooser;
    Command autonomousCommand;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    public static final String AUTONCHOOSER_ = "Autonomous Chooser";
    public void robotInit() {
        CommandBase.init();
        Dashboard.init();
        autonomousChooser = new SendableChooser();
        autonomousChooser.addObject("Do Nothing", new Integer(0));
        autonomousChooser.addDefault("1", new Integer(1));
        autonomousChooser.addObject("2", new Integer(2));
        autonomousChooser.addObject("Three Ball", new Integer(3));
        autonomousChooser.addObject("4", new Integer(4));
        SmartDashboard.putNumber(AUTONCHOOSER_, 1);
    }

    public void autonomousInit() {
        
        autonomousCommand = new Autonomous((int) SmartDashboard.getNumber(AUTONCHOOSER_, 3));
        autonomousCommand.start();
        //new AutonomousStaticDrive(10, 0).start();
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        Dashboard.update();
    }

    public void teleopInit() {
        if (autonomousCommand != null)
        autonomousCommand.cancel();
        new SwitchDrive(SwitchDrive.MODE_OMNIWHEEL, SwitchDrive.MODE_NULL).start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        long begin=System.currentTimeMillis();
        Scheduler.getInstance().run();
        Dashboard.update();
        SmartDashboard.putNumber("Main Loop Time", System.currentTimeMillis()-begin);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        Scheduler.getInstance().run();
        Dashboard.update();
        LiveWindow.run();
    }
    
    public void disabledPeriodic() {
        
    }
    
    public void disabledInit() {
        System.out.println("Disabled.");
        new ToggleArm(true).start();
    }
}
