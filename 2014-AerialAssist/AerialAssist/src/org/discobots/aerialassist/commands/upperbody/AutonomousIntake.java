package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;

public class AutonomousIntake extends CommandBase {

    private final long maxRunTime;
    private long startTime;
    double power;

    public AutonomousIntake(double p, int time) {
        requires(rollerSub);
        maxRunTime = time;
        power = p;
    }

    protected void initialize() {
        rollerSub.setIntakeSpeed(0);
        startTime = System.currentTimeMillis();
    }

    protected void execute() {
        rollerSub.setIntakeSpeed(power);
    }

    protected boolean isFinished() {
        return System.currentTimeMillis() - startTime > maxRunTime;
    }

    protected void end() {
        rollerSub.setIntakeSpeed(0);
    }

    protected void interrupted() {
        end();
    }
}
