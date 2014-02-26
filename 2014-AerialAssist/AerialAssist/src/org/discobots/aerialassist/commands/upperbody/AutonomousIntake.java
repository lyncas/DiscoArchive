/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.commands.upperbody.Intake;

/**
 *
 * @author Seth
 */
public class AutonomousIntake extends CommandBase {

    private final long maxRunTime;
    private long startTime; 
    double power;
    
    public AutonomousIntake(double p, int time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(rollerSub);
        maxRunTime=time;
        power=p;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        rollerSub.setIntakeSpeed(0);
        startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        rollerSub.setIntakeSpeed(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() - startTime > maxRunTime;
    }

    // Called once after isFinished returns true
    protected void end() {
        rollerSub.setIntakeSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
