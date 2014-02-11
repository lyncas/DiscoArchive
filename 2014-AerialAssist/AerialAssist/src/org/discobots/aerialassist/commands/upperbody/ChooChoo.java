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

    private final boolean keepGoing;

    public ChooChoo(boolean interrupt) {
        requires(catapultSub);
        keepGoing = interrupt; // What does this do? "keep going" does not mean the same as "interrupt"
        maxRunTime = 1000;
        finished = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        if (!rollerSub.isExtended() || !keepGoing) {
            finished = true;
        } else {
            catapultSub.run();
            System.out.println("ChooChooCommand: Is Running");
        }
        startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (catapultSub.getTouchValue()) {
            finished = true;
        }
        SmartDashboard.putNumber("ChooChoo Time Remaining", (int) (maxRunTime - (System.currentTimeMillis() - startTime)));
        SmartDashboard.putBoolean("ChooChoo Switch Value", catapultSub.getTouchValue());
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
