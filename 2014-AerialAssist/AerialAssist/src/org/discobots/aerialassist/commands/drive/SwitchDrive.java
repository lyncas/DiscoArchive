/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Dylan
 */
public class SwitchDrive extends CommandBase {
    boolean which;
    public SwitchDrive(boolean check) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        which=check;
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(which)
            drivetrain.PneuOut();
        else
            drivetrain.PneuIn();
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
