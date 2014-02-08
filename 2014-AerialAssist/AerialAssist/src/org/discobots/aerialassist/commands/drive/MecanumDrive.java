/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import com.sun.squawk.util.MathUtils;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Patrick
 */
public class MecanumDrive extends CommandBase {

    public MecanumDrive() {
        requires(drivetrainSub);
    }

    protected void initialize() {
        drivetrainSub.holonomicPolar(0, 0, 0);
        System.out.println("Mecanum wheels engaged\n");
    }

    protected void execute() {
        // Get input from gamepad
        double x = oi.getRawAnalogStickALX();
        double y = oi.getRawAnalogStickALY();
        double rotation = oi.getRawAnalogStickARX();
        // Deadzone
        if (Math.abs(x) < 0.05) x = 0;
        if (Math.abs(y) < 0.05) y = 0;
        if (Math.abs(rotation) < 0.05) rotation = 0;
        
        double magnitude = Math.sqrt(x * x + y * y);
        double angle = MathUtils.atan2(y, x) * 180.0 / Math.PI;

        double gyroAngle = drivetrainSub.getGyroAngle();

        drivetrainSub.holonomicPolar(magnitude, angle/* - gyroAngle*/, rotation);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        drivetrainSub.holonomicPolar(0, 0, 0);
    }

    protected void interrupted() {
        end();
    }
}
