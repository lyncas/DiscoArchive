/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import disco.commands.CommandBase;
import disco.utils.GamePad;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * Linear interpolation drive between b on left and right edges of joystick
 * range and a at corners. Allows fine control with the right joystick PID
 * CURRENTLY DISABLED
 */
public class LerpDrive extends CommandBase {

    private double move = 0, turn = 0;
    private double driveLeft = 0, driveRight = 0;
    private Joystick joy1;
    private GamePad gp;

    private double threshold = 0.1;
    private double a = 0.25;
    private double b = 1;

    protected PIDController turnControl;
    private double m_kP = 0.001,
	    m_kI = 0.00005,
	    m_kD = 0;
    protected double m_correction = 0;
    protected int m_leftInitial = 0;
    protected int m_rightInitial = 0;
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


    public LerpDrive() {
	// Use requires() here to declare subsystem dependencies
	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	joy1 = oi.getJoy1();
	if (joy1 instanceof GamePad) {
	    gp = (GamePad) joy1;
	}

	m_leftInitial = drivetrain.getLeftEncoder();
	m_rightInitial = drivetrain.getRightEncoder();

	turnControl = new PIDController(m_kP, m_kI, m_kD, turnSource, turnOutput);
	turnControl.enable();
	turnControl.setSetpoint(0); //minimize error
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	calculateInputs();
	//PID stuff
	if (turn == 0 && move != 0) {
	    //turnControl.enable(); //DON'T UNCOMMENT UNLESS ENCODERS WORK
	    turn = -m_correction / 2.0;
	} else {
	    //driver is doing something else. start over.
	    turnControl.disable();
	    m_leftInitial = drivetrain.getLeftEncoder();
	    m_rightInitial = drivetrain.getRightEncoder();
	}
	//end PID stuff

	//The important part
	if (move >= 0 && turn >= 0) {//Q1
	    driveLeft = turn * b + move * (1 - turn * b);
	    driveRight = -1 * turn * b + move * ((1 - turn * (a + 1)) + turn * b);
	} else if (move >= 0 && turn < 0) {//Q2
	    driveLeft = turn * b + move * (1 + turn * (1 + a) - turn * b);
	    driveRight = -1 * turn * b + move * (1 + turn * b);
	} else if (move < 0 && turn >= 0) {//Q4
	    driveLeft = turn * b - move * (turn * (a + 1) - 1 - turn * b);
	    driveRight = -1 * turn * b - move * (-1 + turn * b);
	} else if (move < 0 && turn < 0) {//Q3
	    driveLeft = turn * b - move * (-1 - turn * b);
	    driveRight = -1 * turn * b - move * (-1 - turn * (a + 1) + turn * b);
	}

	drivetrain.tankDrive(driveLeft, driveRight);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
	drivetrain.arcadeDrive(0, 0);
	turnControl.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }

    protected void calculateInputs() {
	if (gp != null) {
	    move = gp.getLY();
	    move = Math.abs(move) > threshold ? move : 0;
	    turn = gp.getLX();
	    turn = Math.abs(turn) > threshold ? turn : 0;

	    move += gp.getRY() / 2;
	    turn += gp.getRX() / 2;

	    if (Math.abs(move) > 1) {
		move /= Math.abs(move);
	    }
	    if (Math.abs(turn) > 1) {
		turn /= Math.abs(turn);
	    }
	} else {
	    throw new IllegalStateException("JoyArcadeTwoSpeed only works with gamepads for now.");
	}
    }

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
}