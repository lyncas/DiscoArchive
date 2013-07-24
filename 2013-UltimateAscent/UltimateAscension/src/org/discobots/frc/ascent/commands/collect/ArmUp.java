/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.frc.ascent.commands.collect;

import org.discobots.frc.ascent.commands.CommandBase;

/**
 *
 * @author Developer
 */
public class ArmUp extends CommandBase {
    
    public ArmUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        CommandBase.collectorSubsystem.setArmPosition(false);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
