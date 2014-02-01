package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Nolan Shah
 */
public class Intake extends CommandBase {
    
    private boolean direction;
    public static final boolean OUT = false;
    public static final boolean IN = true;
    
    public Intake(boolean direction) {
        requires(intakeSub);
        this.direction = direction;
    }
    
    protected void initialize() {
    }

    protected void execute() {
        intakeSub.setSpeed(direction ? -1 : 1);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        intakeSub.setSpeed(0);
    }

    protected void interrupted() {
    }
    
}
