package org.discobots.aerialassist.commands.drive;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import org.discobots.aerialassist.HW;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.utils.AngleController;

/**
 *
 * @author Patrick
 */
public class FixAngle extends CommandBase implements PIDSource, PIDOutput {

    private PIDController angleController = new PIDController(1f/45f, 0.0, 0.0, this, this);
    private float output;
    private final float target;

    public FixAngle(float angle) {
        requires(drivetrainSub);
        angleController.setOutputRange(-1.0, 1.0);
        target = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        angleController.setSetpoint(target);
        angleController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //float ou
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return pidGet() - target < 1.0f;
    }

    // Called once after isFinished returns true
    protected void end() {
        angleController.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }

    public double pidGet() {
        return drivetrainSub.getGyroAngle();
    }

    public void pidWrite(double output) {
        this.output = (float) output;
    }
}
