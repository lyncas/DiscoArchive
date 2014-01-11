
package edu.wpi.first.wpilibj.templates.commands;

/**
 *
 * @author bradmiller
 */
public class Arcade extends CommandBase {

    public Arcade() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double move=oi.getAnalogStickLY();
        double turn=oi.getAnalogSticLX();
        drivetrain.arcadeDrive(move, turn);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.arcadeDrive(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
