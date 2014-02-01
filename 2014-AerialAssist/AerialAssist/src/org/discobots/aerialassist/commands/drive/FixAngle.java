package org.discobots.aerialassist.commands.drive;

import org.discobots.aerialassist.HW;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.utils.AngleController;

/**
 *
 * @author Patrick
 */
public class FixAngle extends CommandBase {

    private AngleController angleController = new AngleController(0.5, 0.0, 0.0, CommandBase.drivetrain.getGyro());

    private double target;
    private boolean useOwnData;

    public FixAngle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        target = 0.0;
        useOwnData = true;
    }

    public FixAngle(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        target = angle;
        useOwnData = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (useOwnData) {
            angleController.setSetpoint();
        } else {
            angleController.setSetpoint(target);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
