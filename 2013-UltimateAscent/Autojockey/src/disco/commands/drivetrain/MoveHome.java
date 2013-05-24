/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import disco.commands.CommandBase;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;


public class MoveHome extends CommandBase {
    DifferentialPilot p;
    Navigator nav;

    /*
     * distance in wheel diameter units
     */
    public MoveHome() {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
	nav=drivetrain.getNavigator();
	p=drivetrain.getPilot();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	nav.clearPath();
	nav.addWaypoint(0, 0, 90);
	nav.followPath();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return nav.pathCompleted();
    }

    // Called once after isFinished returns true
    protected void end() {
	p.stop();
        drivetrain.tankDrive(0, 0);
        drivetrain.disableControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}