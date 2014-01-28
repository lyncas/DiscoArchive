package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Nolan Shah
 */
public class IntakeToro extends CommandBase {
    
    private boolean outtake;
    
    public IntakeToro(boolean outtake) {
        requires(eltoro);
        this.outtake = outtake;
    }
    
    protected void initialize() {
    }

    protected void execute() {
        eltoro.setSpeed(outtake ? -0.8 : 0.8);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        eltoro.setSpeed(0);
    }

    protected void interrupted() {
    }
    
}
