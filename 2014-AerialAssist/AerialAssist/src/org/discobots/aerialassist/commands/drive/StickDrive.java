/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Developer
 */
public class StickDrive extends CommandBase {

    private float vPrev, turnPrev;
    private float rampThreshold = 0.1f;
    double velocity;
    double turn;
    double driveLeft = 0;
    double driveRight = 0;
    
    public StickDrive() {
        requires(drivetrainSub);
        vPrev = 0;
        turnPrev = 0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrainSub.tankDrive(0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        velocity = oi.getRawAnalogStickALY();
        turn = oi.getRawAnalogStickARX();
        
        if (Math.abs(velocity)<.05)
            velocity=0;
        if (Math.abs(turn)<.05)
            turn=0;
        
        if (vPrev - velocity > rampThreshold) {
            velocity = vPrev - rampThreshold;
        } else if (velocity - vPrev > rampThreshold) {
            velocity = vPrev + rampThreshold;
        }
        if (turnPrev - turn > rampThreshold) {
            turn = turnPrev - rampThreshold;
        } else if (turn - turnPrev > rampThreshold) {
            turn = turnPrev + rampThreshold;
        }

        vPrev = (float) velocity;
        turnPrev = (float) turn;
        
        if (velocity >= 0)
        {
            driveLeft = -(velocity - turn);
            driveRight = velocity + turn;
        }
        
        if (velocity < 0)
        {
            driveLeft = -(velocity + turn);
            driveRight = velocity - turn;
        }
        
        drivetrainSub.tankDrive(driveLeft, driveRight);
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
