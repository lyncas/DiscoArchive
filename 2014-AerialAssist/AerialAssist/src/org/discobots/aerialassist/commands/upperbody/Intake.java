package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Nolan Shah
 */
public class Intake extends CommandBase {
    
    private int direction;
    public static final int OUT = 1;
    public static final int IN = -1;
    
    public Intake(int direction) {
        requires(rollerSub);
        this.direction = direction;
    }
    
    protected void initialize() {
    }

    protected void execute() {
        rollerSub.setSpeed(direction);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        rollerSub.setSpeed(0);
    }

    protected void interrupted() {
    }
    
}
