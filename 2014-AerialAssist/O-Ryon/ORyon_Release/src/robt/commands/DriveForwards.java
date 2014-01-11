/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.commands;

/**
 *
 * @author Sam
 * A command to autonomously drive forwards for some time, in milliseconds.
 */
public class DriveForwards extends CommandBase {
    int time;
    long startTime;
    
    public DriveForwards(int msec) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
        time=msec;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        startTime=System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrain.tankDriveUnsmoothed(0.4, 0.4);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() > startTime+time;
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
}
