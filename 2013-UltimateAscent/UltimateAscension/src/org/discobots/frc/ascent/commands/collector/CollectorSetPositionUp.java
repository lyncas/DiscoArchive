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
public class CollectorSetPositionUp extends CommandBase {

    protected void initialize() {
        collectorSubsystem.setCollectorPosition(true);
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
