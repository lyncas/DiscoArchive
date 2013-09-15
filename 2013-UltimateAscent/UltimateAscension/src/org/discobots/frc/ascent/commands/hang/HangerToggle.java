/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.discobots.frc.ascent.commands.hang;

import org.discobots.frc.ascent.commands.CommandBase;

/**
 *
 * @author Nolan Shah
 */
public class HangerToggle extends CommandBase {

    protected void initialize() {
        hangerSubsystem.setHangerPosition(!hangerSubsystem.getHangerPosition());
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
