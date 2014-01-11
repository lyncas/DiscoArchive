/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.commands;

import robot.GamePad;

/**
 *
 * @author Sam
 */
public class ArcadeDrive extends CommandBase {
    GamePad j1;
    
    public ArcadeDrive(GamePad j) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrain);
        j1=j;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.arcade(0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrain.arcade(j1.getY(), j1.getX());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.arcade(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        drivetrain.arcade(0, 0);
    }
}
