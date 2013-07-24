/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.frc.ascent.commands.collector;

import org.discobots.frc.ascent.commands.CommandBase;

/**
 *
 * @author Nolan Shah
 */
public class CollectorSetPositionToggle extends CommandBase {
    
    public CollectorSetPositionToggle() {
    }

    protected void initialize() {
        collectorSubsystem.setCollectorPosition(!collectorSubsystem.getCollectorPosition());
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