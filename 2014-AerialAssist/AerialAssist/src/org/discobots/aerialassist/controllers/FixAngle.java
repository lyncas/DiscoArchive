/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.controllers;

import org.discobots.aerialassist.HW;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Patrick
 */
public class FixAngle extends CommandBase {
        
    private double target;
    private boolean useOwnData;
    public FixAngle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        target=0.0;
        useOwnData=true;
    }
    
    public FixAngle(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        target=angle;
        useOwnData=false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(useOwnData){
            HW.angleController.setSetpoint();
        }else{
            HW.angleController.setSetpoint(target);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
