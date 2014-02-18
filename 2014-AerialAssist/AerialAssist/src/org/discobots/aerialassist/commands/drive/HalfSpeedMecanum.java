package org.discobots.aerialassist.commands.drive;

import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Developer
 */
public class HalfSpeedMecanum extends CommandBase {
    
    double angle;
    
    public HalfSpeedMecanum(double newAngle) {
        requires(drivetrainSub);
        angle = newAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrainSub.holonomicPolar(0, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrainSub.holonomicPolar(.5, angle, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrainSub.holonomicPolar(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
