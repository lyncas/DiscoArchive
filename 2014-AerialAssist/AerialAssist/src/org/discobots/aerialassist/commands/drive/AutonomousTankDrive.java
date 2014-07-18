package org.discobots.aerialassist.commands.drive;

import org.discobots.aerialassist.commands.CommandBase;

public class AutonomousTankDrive extends CommandBase {

    private final long maxRunTime;
    private long startTime;
    private double left;
    private double right;
    private double power = 0;

    public AutonomousTankDrive(double l, double r, int time) {
        requires(drivetrainSub);
        requires(rollerSub);
        maxRunTime = time;
        left = -l;
        right = r;
    }

    public AutonomousTankDrive(double l, double r, double p, int time) {
        requires(drivetrainSub);
        requires(rollerSub);
        maxRunTime = time;
        left = l;
        right = -r;
        power = p;
    }

    protected void initialize() {
        drivetrainSub.tankDrive(0, 0);
        rollerSub.setIntakeSpeed(0);
        startTime = System.currentTimeMillis();
    }

    protected void execute() {
        drivetrainSub.tankDrive(left, right);
        rollerSub.setIntakeSpeed(power);
    }

    protected boolean isFinished() {
        return System.currentTimeMillis() - startTime > maxRunTime;
    }

    protected void end() {
        drivetrainSub.tankDrive(0, 0);
        rollerSub.setIntakeSpeed(0);
    }

    protected void interrupted() {
        end();
    }
}
