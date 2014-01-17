/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands;

import com.sun.squawk.util.MathUtils;
import org.discobots.aerialassist.GamePad;

/**
 *
 * @author Patrick
 */
public class MecanumDrive extends CommandBase {
    
    GamePad J;
    
    public MecanumDrive(GamePad j) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrain);
        J=j;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.holonomicPolar(0,0,0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double x=J.getLX();
        double y=J.getLY();
        double rotation=J.getRX();
        
        double magnitude = Math.sqrt(x*x+y*y);
        double angle = MathUtils.atan2(y,x)*180.0/Math.PI;
        System.out.println(angle);
        drivetrain.holonomicPolar(magnitude, angle, rotation);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.holonomicPolar(0,0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
