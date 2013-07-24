/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.frc.ascent;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.frc.ascent.commands.CommandBase;

/**
 *
 * @author Nolan
 */
public class Dashboard {
    public static void init() {
        
    }
    public static void update() {
        // Robot
        
        // Subsystem: Drivetrain
        
        // Subsystem: Shooter
        SmartDashboard.putNumber("Shooter PWM setpoint", CommandBase.shooterSubsystem.getSetpointPWM());
        
        // Subsystem: Collector
        SmartDashboard.putString("Arm Position", CommandBase.collectorSubsystem.getArmPosition() ? "Down":"Up");
        
        //Subsystem: Compressor
        SmartDashboard.putBoolean("Tanks full", CommandBase.compressorSubsystem.getPressure());
        SmartDashboard.putBoolean("Compressor enabled", CommandBase.compressorSubsystem.isCompressorEnabled());
        
        // Subsystem: Shared
    }
}
