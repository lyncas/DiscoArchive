/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.frc.ascent.commands.shoot;

import edu.wpi.first.wpilibj.command.Command;
import org.discobots.frc.ascent.commands.CommandBase;
import org.discobots.frc.ascent.commands.DoNothing;
import org.discobots.frc.ascent.subsystems.Shooter;

/**
 * TODO: 
 * Add Cycling code for ShooterBang
 * 
 * @author Developer
 */
public class CycleShootCommand extends CommandBase {

    public CycleShootCommand() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Command c = shooterSubsystem.getCurrentCommand();
        if (c instanceof DoNothing) {
            new ShooterOpen().start();
        } else {
            new DoNothing().start();
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
