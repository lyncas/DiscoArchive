/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.commands.drive;

import robt.utils.GamePad;
import edu.wpi.first.wpilibj.Joystick;
import robt.commands.CommandBase;

/**
 * Linear interpolation drive between b on left and right edges of joystick
 * range and a at corners. Allows fine control with the right joystick PID
 * CURRENTLY DISABLED
 */
public class Lerpdrive extends CommandBase {

    private double move = 0, turn = 0;
    private double driveLeft = 0, driveRight = 0;
    private Joystick joy1;
    private GamePad gp;

    private double threshold = 0.05;
    private double a = 0.25;
    private double b = 1;




    public Lerpdrive() {
	// Use requires() here to declare subsystem dependencies
	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	joy1 = oi.getJoy1();
	if (joy1 instanceof GamePad) {
	    gp = (GamePad) joy1;
	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

	//Touching pyramid?

        calculateInputs();
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
	drivetrain.tankDriveUnsmoothed(driveLeft, driveRight);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
	drivetrain.tankDriveUnsmoothed(0, 0);
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

}