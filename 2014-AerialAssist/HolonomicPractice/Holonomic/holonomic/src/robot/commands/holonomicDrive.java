/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.commands;

import robot.GamePad;

/**
 *
 * @author Patrick
 */
public class holonomicDrive extends CommandBase {
    
    GamePad J;
    
    public holonomicDrive(GamePad j) {
        requires(drivetrain);
        
        J=j;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.holonomic(0, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrain.holonomic(J.getLX(),J.getLY(),J.getRX());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.holonomic(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
