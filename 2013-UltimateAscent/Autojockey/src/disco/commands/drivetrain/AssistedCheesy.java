/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import disco.commands.CommandBase;
import disco.utils.GamePad;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class AssistedCheesy extends CommandBase {

    protected PIDController turnControl;
    private double m_kP = 0.001,
            m_kI = 0.00005,
            m_kD = 0;
    protected double m_correction = 0;
    protected int m_leftInitial = 0;
    protected int m_rightInitial = 0;
    private double move;
    private double turn;
    private GamePad gamepad;
    private double skimGain = CheesyArcade.skimGain; // make changes to cheesyarcade
    public static double turnGain = CheesyArcade.turnGain;
    private double threshold = CheesyArcade.threshold;
    private PIDOutput turnOutput = new PIDOutput() {
        public void pidWrite(double output) {
            usePIDOutput(output);
        }
    };
    private PIDSource turnSource = new PIDSource() {
        public double pidGet() {
            return returnPIDInput();
        }
    };

    private double returnPIDInput() {
        return offsetLeft() - offsetRight();
    }

    private void usePIDOutput(double output) {
        m_correction = output;
    }

    protected int offsetLeft() {
        return drivetrain.getLeftEncoder() - m_leftInitial;
    }

    protected int offsetRight() {
        return drivetrain.getRightEncoder() - m_rightInitial;
    }

    public PIDController getController() {
        return turnControl;
    }

    public AssistedCheesy() {
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        if (oi.getJoy1() instanceof GamePad) {
            gamepad = (GamePad) oi.getJoy1();
        } else {
            throw new IllegalStateException("Gamepad is required to use this commmand.");
        }
        turnControl = new PIDController(m_kP, m_kI, m_kD, turnSource, turnOutput);
        //turnControl.enable();
        turnControl.setSetpoint(0);
    }

    protected void execute() {
        move = turn = 0;
        calculateInput();

        if (Math.abs(move) > threshold) {
            turn = turn * (turnGain * Math.abs(move));
        }

        double tempLeft = move + turn;
        double tempRight = move - turn;

        double left = tempLeft + skim(tempRight);
        double right = tempRight - skim(tempLeft);

        if (turn==0 && move!=0) {
            //we should correct
            turnControl.enable();
            left += left > 0 ? m_correction : m_correction;
            right -= right > 0 ? m_correction : m_correction;
            double max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1) {
                left = left / max;
                right = right / max;
            }
        } else {
            if (turnControl.isEnable()) {
                turnControl.disable();
            }
            m_leftInitial = drivetrain.getLeftEncoder();
            m_rightInitial = drivetrain.getRightEncoder();
        }

        drivetrain.tankDrive(left, right);
    }

    protected void end() {
        turnControl.disable();
        drivetrain.arcadeDrive(0, 0);
    }

    protected void interrupted() {
        end();
    }

    private double skim(double v) {
        if (v > 1.0) {
            return -((v - 1.0) * skimGain);
        } else if (v < -1.0) {
            return -((v + 1.0) * skimGain);
        } else {
            return 0;
        }
    }

    protected void calculateInput() {
        if (gamepad != null) {
            move = gamepad.getLY();
            move = Math.abs(move) > threshold ? move : 0;
            turn = gamepad.getLX();
            turn = Math.abs(turn) > threshold ? turn : 0;

            move += gamepad.getRY() / 2;
            turn += gamepad.getRX() / 2;
        } else {
            throw new IllegalStateException("Gamepad is required to use this commmand.");
        }
    }

    protected boolean isFinished() {
        return false;
    }
}
