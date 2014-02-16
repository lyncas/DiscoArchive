/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Patrick
 */
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
        double l = oi.getRawAnalogStickALY();
        double r = -oi.getRawAnalogStickARY();
        
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
        
        SmartDashboard.putNumber("Gyro Angle:  ",drivetrainSub.getGyroAngle());
        SmartDashboard.putNumber("Gyro Rate:  ",drivetrainSub.getGyroRate());
        
        drivetrainSub.tankDrive(l, r);
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
