/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package disco;

import disco.commands.Autonomous;
import disco.commands.CommandBase;
import disco.utils.Dashboard;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MainAscent extends IterativeRobot {

    private static double executionTimeMillis=0;
    private static long lastTime=0;
    Command autonomousCommand;

    public MainAscent() {
	super();
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
            // Initialize all subsystems
            System.out.println("\n\nInitializing robot...");
            CommandBase.init();
            System.out.println("CommandBase initialization successful");
            getWatchdog().setEnabled(false);
            lastTime=System.currentTimeMillis();
            //NetworkTable.setServerMode();
            Dashboard.init();
            Dashboard.putStuff();
            System.out.println("Robot initialization successful");
    }

    public void disabledInit() {
        HW.preferences.save();
    }

    public void disabledPeriodic() {
            lastTime = System.currentTimeMillis();
            Scheduler.getInstance().run();
            Dashboard.putSensors();
            executionTimeMillis=System.currentTimeMillis()-lastTime;

    }

    public void autonomousInit() {
	// schedule the autonomous command (example)
	autonomousCommand = (Command) (Dashboard.autonChooser.getSelected());
        if(autonomousCommand!=null) {
            autonomousCommand.start();
        }
        else{
            autonomousCommand=new Autonomous(Autonomous.MODE_SAFE);
            autonomousCommand.start();
        }
    }

    public void autonomousPeriodic() {
	lastTime = System.currentTimeMillis();
	Scheduler.getInstance().run();
        Dashboard.putSensors();
        executionTimeMillis=System.currentTimeMillis()-lastTime;
    }

    public void teleopInit() {
        if(autonomousCommand!=null) {
            autonomousCommand.cancel();
        }
    }
    public void teleopPeriodic() {
        //System.out.println(CommandBase.drivetrain.getCurrentCommand().toString() + " " + CommandBase.drivetrain.getPWMRight() + " " + CommandBase.drivetrain.getPWMLeft());
        lastTime = System.currentTimeMillis();
	Scheduler.getInstance().run();
        Dashboard.putSensors();
        executionTimeMillis=System.currentTimeMillis()-lastTime;

    }

    public void testPeriodic() {
	LiveWindow.run();
    }

    public static double getExecutionTime(){
        return executionTimeMillis;
    }
}