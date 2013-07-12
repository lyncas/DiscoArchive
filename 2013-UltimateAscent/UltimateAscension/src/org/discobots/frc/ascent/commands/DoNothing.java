package org.discobots.frc.ascent.commands;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Nolan Shah
 */
public class DoNothing extends CommandBase {
    
    public DoNothing(Subsystem subsystem) {
        requires(subsystem);
    }
    public DoNothing() {
        
    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
