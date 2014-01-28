/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.utils.GamePad;
import org.discobots.aerialassist.HW;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Patrick
 */
public class MecanumDrive extends CommandBase {

    public MecanumDrive() {
        requires(drivetrain);
    }

    protected void initialize() {
        drivetrain.holonomicPolar(0, 0, 0);
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

        double gyroAngle = drivetrain.getGyroAngle();

        drivetrain.holonomicPolar(magnitude, angle/* - gyroAngle*/, rotation);
    }

    protected boolean isFinished() {
        return drivetrain.getDriveState();
    }

    protected void end() {
        drivetrain.holonomicPolar(0, 0, 0);
    }

    protected void interrupted() {
        end();
    }
}
