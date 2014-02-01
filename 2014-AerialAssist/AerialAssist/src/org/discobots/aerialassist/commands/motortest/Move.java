/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.motortest;

import edu.wpi.first.wpilibj.Preferences;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Dylan
 */
public class Move extends CommandBase {
    
    Preferences pref;
    
    long time;
    int delta;
    double power;
    
    
    public Move(int d, double p) {
        requires(RMotor);
        delta = pref.getInt("Time", d);
        power = pref.getDouble("Power", p);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        time=System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        RMotor.set(power);
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return System.currentTimeMillis()>=time+delta;    
    }

    // Called once after isFinished returns true
    protected void end() {
        RMotor.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
