package org.discobots.aerialassist.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Developer
 */
public class FixedSpeedMecanum extends CommandBase {
    
    double angle;
    double maxSpeed;
    
    public FixedSpeedMecanum(double newAngle) {
        requires(drivetrainSub);
        angle = newAngle;
        maxSpeed = SmartDashboard.getNumber("Fixed Speed", .5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrainSub.holonomicPolar(0, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrainSub.holonomicPolar(maxSpeed, angle, 0);
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
