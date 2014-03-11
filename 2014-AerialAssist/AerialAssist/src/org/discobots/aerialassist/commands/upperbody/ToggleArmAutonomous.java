/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.upperbody;

import edu.wpi.first.wpilibj.DigitalInput;
import org.discobots.aerialassist.HW;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Seth
 */
public class ToggleArmAutonomous extends CommandBase {
    
    public boolean check;
    
    public ToggleArmAutonomous(boolean on) throws Exception {
        throw new Exception();
        //check = on;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        if(compressorSub.canRunPneumatics())
            rollerSub.setExtended(check);  //I reversed it because isExtended now returns the opposite.
        if (!compressorSub.isEnabled())
            compressorSub.on();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (rollerSub.isExtended())
            rollerSub.setIntakeSpeed(.2*Intake.IN);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (!check)
            return rollerSub.getLimit();
        else
            return true;
        }

    // Called once after isFinished returns true
    protected void end() {
        rollerSub.setIntakeSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
