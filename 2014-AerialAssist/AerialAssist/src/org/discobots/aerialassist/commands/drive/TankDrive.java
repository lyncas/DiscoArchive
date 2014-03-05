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
        
        lPrev = (float)l;
        rPrev = (float)r;
        
        drivetrainSub.tankDrive(l, r);
        
      
/*      double magnitude = 0;
        
        double x = oi.getRawAnalogStickALX();
        double y = oi.getRawAnalogStickALY();
        double angle = MathUtils.atan2(y, x) * 180.0 / Math.PI;
        drivetrainSub.setAngleControllerSetpoint(angle);
        double gyroAngle = drivetrainSub.getGyroAngle();
        double ACO = drivetrainSub.getAngleControllerOutput();
        if (ACO < .05)
            magnitude = Math.sqrt(x * x + y * y);
        double left = magnitude + ACO;
        double right = magnitude - ACO;
        if (left > 1)
        {
            right/=left;
            left/=left;
        }
        if (right > 1)
        {
            left/=right;
            right/=right;
        }
        if (Math.abs(ACO) > .05)
        {
            if (drivetrainSub.isFieldCentricEnabled()) 
                drivetrainSub.tankDrive(left, right);
            else
                drivetrainSub.tankDrive(l, r);
        }
        else
            drivetrainSub.tankDrive(magnitude, magnitude);
*/        
        
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
