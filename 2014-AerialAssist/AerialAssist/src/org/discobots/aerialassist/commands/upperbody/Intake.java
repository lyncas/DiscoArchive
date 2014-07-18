package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;

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
        rollerSub.setIntakeSpeed(direction);
    }

    protected boolean isFinished() {
        return false; // Automatically terminated by OI
    }

    protected void end() {
        rollerSub.setIntakeSpeed(0);
    }

    protected void interrupted() {
        end();
    }
}
