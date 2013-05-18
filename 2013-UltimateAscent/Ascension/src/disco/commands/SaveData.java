/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands;

import disco.HW;
import disco.subsystems.Shooter;

public class SaveData extends CommandBase {
    boolean done=false;
    
    public SaveData() {
        this.setRunWhenDisabled(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        done=false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        try{
        //SHOOTER
        switch(shooter.getMode()){
            case Shooter.MODE_BANG:
                HW.preferences.putDouble("shooter_setpoint", shooter.getSetpoint());
                HW.preferences.putDouble("shooter_difference", shooter.difference);
                break;
            case Shooter.MODE_OPEN_LOOP:
                if(shooter.getFrontPower()>0){
                    HW.preferences.putDouble("front_PWM", shooter.getFrontPower());
                    HW.preferences.putDouble("back_PWM", shooter.getBackPower());
                }
                break;
        }        
        
        HW.preferences.save();
        }
        catch(Exception e){
            System.out.println("DATA SAVE EXCEPTION. IGNORE NEXT LINE.");
        }
        done=true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println("Robot data saved successfully!");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        System.out.println("ROBOT DATA NOT SAVED. INTERRUPTED.");
    }
}
