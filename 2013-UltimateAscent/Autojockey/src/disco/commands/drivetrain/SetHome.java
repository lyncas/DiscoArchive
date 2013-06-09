/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import disco.commands.CommandBase;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;


public class SetHome extends CommandBase {
    PoseProvider pp;
    Navigator nav;

    /*
     * distance in wheel diameter units
     */
    public SetHome() {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
	nav=drivetrain.getNavigator();
	pp=drivetrain.getPoseProvider();
        this.setRunWhenDisabled(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	pp.setPose(new Pose(0,0,90));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.disableControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}