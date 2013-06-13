/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import disco.commands.CommandBase;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;

public class LejosDriveCommandTemplate extends CommandBase {

    DifferentialPilot pilot;
    Navigator nav;
    PoseProvider pp;
    Driver driver;
    
    public LejosDriveCommandTemplate() {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
        pilot = drivetrain.getPilot();
        nav = drivetrain.getNavigator();
        pp = drivetrain.getPoseProvider();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        driver=new Driver();
        driver.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return driver.isAlive();
    }

    // Called once after isFinished returns true
    protected void end() {
        driver=null;
        pilot.stop();
        drivetrain.tankDrive(0, 0);
        drivetrain.disableControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }

    
    class Driver extends Thread {
        //what does this need to know?
        public Driver(){
            
        }

        //Put what you want the command to do here
        public void run() {
            
        }
        
        //return true when command is over
        public boolean isDone(){
            return true;
        }
        
    }
}