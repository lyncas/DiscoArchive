/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands;

import disco.HW;

public class LoadData extends CommandBase {
    boolean done=false;

    public LoadData() {
        this.setRunWhenDisabled(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        done=false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        try{
            shooter.setSetpoint(HW.preferences.getDouble("shooter_setpoint", shooter.m_defaultSetpoint));
            shooter.m_defaultSetpoint=(HW.preferences.getDouble("shooter_setpoint", shooter.m_defaultSetpoint));
            shooter.difference=HW.preferences.getDouble("shooter_difference", 0);
            shooter.frontPWM=HW.preferences.getDouble("front_PWM", 0.6);
            shooter.backPWM=HW.preferences.getDouble("back_PWM", 0.8);
        }
        catch(Exception e){
            System.out.println("DATA LOAD EXCEPTION. IGNORE NEXT LINE");
        }
        done=true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println("Data load Successful!");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        System.out.println("DATA LOAD INTERRUPTED.");
    }
}
