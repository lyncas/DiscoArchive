/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Developer
 */
public class ControlLEDState extends CommandBase {
    
    public ControlLEDState() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        int ultrasonicVal = drivetrainSub.getUltrasonicIntakeAverageValue();
        boolean meetsMinDistance = ultrasonicVal > 108;
        boolean meetsMaxDistance = ultrasonicVal < 156;
        boolean meetsPressure = compressorSub.getPressurePSI() > 65;
        boolean readyToShoot = meetsMinDistance && meetsMaxDistance && meetsPressure;
        if (readyToShoot) {
            drivetrainSub.writeLEDState(true);
        } else {
            drivetrainSub.writeLEDState(false);
        }
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
