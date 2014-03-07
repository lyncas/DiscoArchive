/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Patrick
 */
public class FieldCentricDrive extends CommandBase {
    public FieldCentricDrive() {
        requires(drivetrainSub);
    }

    protected void initialize() {
        drivetrainSub.tankDrive(0, 0);
        System.out.println("Traction wheels engaged\n");
    }

    protected void execute() {
        double magnitude = 0;
        double x = oi.getRawAnalogStickALX();
        double y = oi.getRawAnalogStickALY();
        double angle = MathUtils.atan2(y, x) * 180.0 / Math.PI;
        drivetrainSub.setAngleControllerSetpoint(angle);
        double gyroAngle = drivetrainSub.getGyroAngle();
        double ACO = drivetrainSub.getAngleControllerOutput();
        if (ACO < .05) {
            magnitude = Math.sqrt(x * x + y * y);
        }
        double left = -magnitude - ACO;
        double right = magnitude - ACO;
        if (left > 1) {
            right /= left;
            left /= left;
        }
        if (right > 1) {
            left /= right;
            right /= right;
        }
        if (Math.abs(ACO) > .05) {
            drivetrainSub.tankDrive(left, right);
        } else {
            drivetrainSub.tankDrive(magnitude, magnitude);
        }


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
