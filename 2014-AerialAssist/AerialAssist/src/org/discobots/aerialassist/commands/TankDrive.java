/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.utils.GamePad;
import org.discobots.aerialassist.HW;

/**
 *
 * @author Patrick
 */
public class TankDrive extends CommandBase {
    
    GamePad J;
    
    public TankDrive(GamePad j) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrain);
        J=j;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.tankDrive(0,0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double x=J.getLX();
        double y=J.getRX();
                
        drivetrain.tankDrive(x,y);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return drivetrain.currentState;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.tankDrive(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
