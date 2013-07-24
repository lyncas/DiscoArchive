/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.frc.ascent.commands.collect;

import org.discobots.frc.ascent.commands.CommandBase;

/**
 *
 * @author Nolan Shah
 */
public class ArmToggle extends CommandBase {
    
    public ArmToggle() {
    }

    protected void initialize() {
        collectorSubsystem.setArmPosition(!collectorSubsystem.getArmPosition());
    }

    protected void execute() {
        
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
}