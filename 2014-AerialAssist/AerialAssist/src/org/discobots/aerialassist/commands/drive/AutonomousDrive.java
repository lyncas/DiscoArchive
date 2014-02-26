/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Seth
 */
public class AutonomousDrive extends CommandBase {
    
    private final long maxRunTime;
    private long startTime; 
    int magnitude; 
    int direction;
    int rotation;
    int left; 
    int right;
    
    public AutonomousDrive(int mag, int dir, int rot, int time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrainSub);
        maxRunTime=time;
        magnitude = mag;
        direction = dir;
        rotation = rot;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrainSub.holonomicPolar(0, 0, 0); 
        startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrainSub.holonomicPolar(magnitude, direction, rotation);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (System.currentTimeMillis() - startTime > maxRunTime) {
            System.out.println("Killing AutonomousDrive");
        }
        return System.currentTimeMillis() - startTime > maxRunTime;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrainSub.holonomicPolar(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
