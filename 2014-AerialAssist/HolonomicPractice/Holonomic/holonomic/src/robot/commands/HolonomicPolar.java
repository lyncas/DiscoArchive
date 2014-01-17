/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.GamePad;

/**
 *
 * @author Patrick
 */
public class HolonomicPolar extends CommandBase {
    
    GamePad J;
    
    public HolonomicPolar(GamePad j) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrain);
        J=j;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.holonomicPolar(0, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrain.holonomicPolar(J.getMagnitude(J.STICK_L), J.getDirectionRadians(J.STICK_L), J.getRX());
        SmartDashboard.putNumber("Right Stick X:  ", J.getRX());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.holonomicPolar(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
