/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.upperbody;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Patrick
 */
public class ChooChoo extends CommandBase {

    boolean finished;
    private final long maxRunTime;
    private long startTime;

    public ChooChoo() {
        requires(catapultSub);
        maxRunTime = 4000;
        finished = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
        if (!rollerSub.isExtended()) 
        {
            finished = true;
        } 
        else 
        {
            if (finished)
                finished=!finished;
            catapultSub.run();
            System.out.println("ChooChooCommand: Is Initialized");

        }
        startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (!catapultSub.getLimitValue()) {
            finished = true;
        }
        System.out.println("ChooChooCommand: Is Executed");
        SmartDashboard.putNumber("ChooChoo Time Remaining", (int) (maxRunTime - (System.currentTimeMillis() - startTime)));
        SmartDashboard.putBoolean("ChooChoo Switch Value", catapultSub.getLimitValue());
        SmartDashboard.putBoolean("ChooChoo Status", finished);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished || (System.currentTimeMillis() - startTime > maxRunTime);
    }

    // Called once after isFinished returns true
    protected void end() {
        catapultSub.stop();
        System.out.println("ChooChooCommand: Has Stopped");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
