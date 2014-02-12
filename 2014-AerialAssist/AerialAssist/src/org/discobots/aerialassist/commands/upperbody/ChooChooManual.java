/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Patrick
 */
public class ChooChooManual extends CommandBase {
    
    public ChooChooManual() {
        requires(catapultSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        if(catapultSub.getManual()) {
            catapultSub.run();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
        catapultSub.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
