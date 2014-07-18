package org.discobots.aerialassist.commands.drive;

import org.discobots.aerialassist.commands.CommandBase;

public class TankDrive extends CommandBase {

    private float lPrev, rPrev;
    private float rampThreshold = 0.1f;

    public TankDrive() {
        requires(drivetrainSub);
        lPrev = 0;
        rPrev = 0;
    }

    protected void initialize() {
        drivetrainSub.tankDrive(0, 0);
        System.out.println("Traction wheels engaged\n");
    }

    protected void execute() {
        double r = oi.getRawAnalogStickARY();
        double l = -oi.getRawAnalogStickALY();

        if (Math.abs(l) < .05) {
            l = 0;
        }
        if (Math.abs(r) < .05) {
            r = 0;
        }

        if (lPrev - l > rampThreshold) {
            l = lPrev - rampThreshold;
        } else if (l - lPrev > rampThreshold) {
            l = lPrev + rampThreshold;
        }
        if (rPrev - r > rampThreshold) {
            r = rPrev - rampThreshold;
        } else if (r - rPrev > rampThreshold) {
            r = rPrev + rampThreshold;
        }

        lPrev = (float) l;
        rPrev = (float) r;

        drivetrainSub.tankDrive(-l, -r);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        drivetrainSub.tankDrive(0, 0);
    }

    protected void interrupted() {
        end();
    }
}
