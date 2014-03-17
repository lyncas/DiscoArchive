package org.discobots.aerialassist.commands.drive;

import org.discobots.aerialassist.commands.CommandBase;

public class StickDrive extends CommandBase {

    private float vPrev, turnPrev;
    private float rampThreshold = 0.1f;
    double velocity;
    double turn;
    double driveLeft = 0;
    double driveRight = 0;

    public StickDrive() {
        requires(drivetrainSub);
        vPrev = 0;
        turnPrev = 0;
    }

    protected void initialize() {
        drivetrainSub.tankDrive(0, 0);
    }

    protected void execute() {
        velocity = oi.getRawAnalogStickALY();
        turn = oi.getRawAnalogStickARX();

        if (Math.abs(velocity) < .05) {
            velocity = 0;
        }
        if (Math.abs(turn) < .05) {
            turn = 0;
        }

        if (vPrev - velocity > rampThreshold) {
            velocity = vPrev - rampThreshold;
        } else if (velocity - vPrev > rampThreshold) {
            velocity = vPrev + rampThreshold;
        }
        if (turnPrev - turn > rampThreshold) {
            turn = turnPrev - rampThreshold;
        } else if (turn - turnPrev > rampThreshold) {
            turn = turnPrev + rampThreshold;
        }

        vPrev = (float) velocity;
        turnPrev = (float) turn;

        if (velocity >= 0) {
            driveLeft = -(velocity - turn);
            driveRight = velocity + turn;
        }

        if (velocity < 0) {
            driveLeft = -(velocity + turn);
            driveRight = velocity - turn;
        }

        drivetrainSub.tankDrive(driveLeft, driveRight);

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
