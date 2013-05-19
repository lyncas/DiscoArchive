/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import disco.commands.CommandBase;
import lejos.robotics.navigation.DifferentialPilot;


public class PilotTurn extends CommandBase {
    DifferentialPilot p;
    double angle;

    /*
     * CCW+
     */
    public PilotTurn(double angle) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
	p=drivetrain.getPilot();
	this.angle=angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	p.rotate(angle, true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !p.isMoving();
    }

    // Called once after isFinished returns true
    protected void end() {
	p.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}