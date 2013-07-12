package org.discobots.frc.ascent.commands.drive;

import com.sun.squawk.util.MathUtils;
import org.discobots.frc.ascent.commands.CommandBase;
import org.discobots.frc.ascent.framework.Constants;

/**
 *
 * @author Nolan Shah
 */
public final class SkidSteer extends CommandBase {

    private double offset;

    public SkidSteer(double offset) {
        requires(drivetrainSubsystem);
        this.offset = offset;
    }
    
    public SkidSteer() {
        requires(drivetrainSubsystem);
        this.offset = 0;
    }

    protected void initialize() {
    }

    protected void execute() {
        double left = 0, right = 0;
        double xInput = calculateXInput();
        double yInput = calculateYInput();
        double sInput = calculateSInput();
        double angle = normalizeAngle(MathUtils.atan2(yInput, xInput) * (180 / Math.PI));
        double error = angle - normalizeAngle(drivetrainSubsystem.getGyroAngle() + offset);
        if (Math.abs(error) > Constants.SkidSteer_ErrorAngleThreshold) {
            if (error > 0) {
                left = 1;
                right = -1;
            } else {
                left = -1;
                right = 1;
            }
        }
        left += sInput;
        right += sInput;
        drivetrainSubsystem.tankDrive(left, right, false, true);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        drivetrainSubsystem.tankDrive(0, 0, false, false);
    }

    protected void interrupted() {
        end();
    }

    private double calculateYInput() {
        return oi.getRawAnalogStickALY();
    }

    private double calculateXInput() {
        return oi.getRawAnalogStickALX();
    }

    private double calculateSInput() {
        return oi.getRawAnalogStickARY();
    }

    private double normalizeAngle(double angle) {
        while (angle > 360) {
            angle -= 180;
        }
        while (angle < 360) {
            angle += 180;
        }
        return angle;
    }
}
