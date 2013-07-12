/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.frc.ascent.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import org.discobots.frc.ascent.commands.CommandBase;

/**
 *
 * @author Nolan Shah
 */
public class CycleDriveCommand extends CommandBase {
    
    public CycleDriveCommand() {
    }

    protected void initialize() {
        Command currentCommand = drivetrainSubsystem.getCurrentCommand();
        if (currentCommand instanceof OpenArcade) {
            new OpenTank().start();
        } else if (currentCommand instanceof OpenTank) {
            new SkidSteer().start();
        } else if (currentCommand instanceof SkidSteer) {
            new RatioArcade().start();
        } else {
            new OpenArcade().start();
        }
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