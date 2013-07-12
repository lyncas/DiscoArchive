package org.discobots.frc.ascent.commands.drive;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import org.discobots.frc.ascent.HW;
import org.discobots.frc.ascent.commands.CommandBase;

/**
 * TODO:
 * Find PID Constants
 * 
 * 
 * @author Nolan Shah
 */
public final class RatioArcade extends CommandBase {
    private final double deadThreshold = 0.05;
    private final double PID_K = 0.0, PID_I = 0.0, PID_D = 0.0;
    private double pidLeftOutput, pidRightOutput;
    private PIDController leftController, rightController;
    private PIDSource leftControllerSource = new PIDSource() {
        public double pidGet() {
            return drivetrainSubsystem.getEncoderLeftRate();
        }
    };
    private PIDSource rightControllerSource = new PIDSource() {
        public double pidGet() {
            return drivetrainSubsystem.getEncoderRightRate();
        }
    };
    private PIDOutput leftControllerOutput = new PIDOutput() {
        public void pidWrite(double output) {
            pidLeftOutput = output;
        }
    };
    private PIDOutput rightControllerOutput = new PIDOutput() {
        public void pidWrite(double output) {
            pidRightOutput = output;
        }
    };
    
    public RatioArcade() {
        pidLeftOutput = 0;
        pidRightOutput = 0;
        requires(drivetrainSubsystem);
    }

    protected void initialize() {
        leftController = new PIDController(PID_K, PID_I, PID_D, leftControllerSource, leftControllerOutput);
        rightController= new PIDController(PID_K, PID_I, PID_D, rightControllerSource, rightControllerOutput);
        leftController.enable();
        rightController.enable();
    }

    protected void execute() {
        leftController.enable();
        rightController.enable();
        double move = calculateMoveInput();
        double turn = calculateTurnInput();
        double left = trimExtraOutput(calculateOutput(move, turn));
        double right =  trimExtraOutput(calculateOutput(move ,-turn));
        leftController.setSetpoint(left);
        rightController.setSetpoint(right);
        drivetrainSubsystem.tankDrive(pidLeftOutput, pidRightOutput, false, false);
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

    private double calculateMoveInput() {
        double move = oi.getRawAnalogStickALY() / 2;
        move += oi.getRawAnalogStickARY();
        move = Math.abs(move) < deadThreshold ? 0 : move;
        return Math.abs(move) > 1 ? 1 : move;
    }

    private double calculateTurnInput() {
        double turn = oi.getRawAnalogStickALX() / 2;
        turn += oi.getRawAnalogStickARX();
        turn = Math.abs(turn) < deadThreshold ? 0 : turn;
        return Math.abs(turn) > 1 ? 1 : turn;
    }

    private double calculateOutput(double move, double turn) {
        return turn + 0.12 * move * turn + move * move * move + 0.33 * move * turn * turn * Math.sin(turn) - move * move * turn - 0.39 * move * move * move * turn * turn;
    }

    private double trimExtraOutput(double a) {
        if (a > 1) {
            a = 1;
        }
        if (a < -1) {
            a = -1;
        }
        return a;
    }
}
