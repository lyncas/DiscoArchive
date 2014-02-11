/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.upperbody;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.HW;

/**
 *
 * @author Patrick
 */
public class ChooChoo extends CommandBase {
    
    private final boolean keepGoing;
    public long MAXTIME;
    private long time;
    DigitalInput limitSwitch;
    
    public ChooChoo(boolean interrupt) {
        requires(catapultSub);
        keepGoing = interrupt;
        MAXTIME = 1000;
//        limitSwitch = new DigitalInput(HW.digitalModule, HW.chooChooSensor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        catapultSub.run();
        if(!rollerSub.getExtender() || !keepGoing)
            end();
        time = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        boolean ready = false;
        boolean go = true;
        
        while (go) {
            SmartDashboard.putInt("Time to go: ", (int) (MAXTIME - (System.currentTimeMillis()-time)));
            SmartDashboard.putBoolean("Switch Value: ", limitSwitch.get());
            SmartDashboard.putBoolean("Ready: ", ready);
            SmartDashboard.putBoolean("Go: ", go);
            
            if(!limitSwitch.get()) {
                ready=true;
            } if (limitSwitch.get() && ready) {
                go = false;
            }
        }
        end();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() - time > MAXTIME;
    }

    // Called once after isFinished returns true
    protected void end() {
        catapultSub.stop();
        System.out.println("The launcher has stopped.");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
