/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.frc.ascent.commands.shoot;

import org.discobots.frc.ascent.commands.CommandBase;

/**
 *
 * @author Nolan
 */
public class ToggleMainShoot extends CommandBase {

    protected void initialize() {
        shooterSubsystem.setMainShootPosition(!shooterSubsystem.getMainShootPosition());
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

