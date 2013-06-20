/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain.manual;

import disco.commands.CommandBase;


public class FloatDrive extends CommandBase {

    public FloatDrive() {
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	drivetrain.tankDrive(0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	drivetrain.tankDrive(0, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
	drivetrain.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}