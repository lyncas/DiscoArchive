/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.pneumatics;

import disco.commands.CommandBase;
import disco.subsystems.Shooter;

/**
 *
 * @author Developer
 */
public class ClearIn extends CommandBase {
    private boolean done;
    private double timeout=0.5;
    
    public ClearIn() {
        // Use requires() here to declare subsystem dependencies
        //requires(shooter);
        
        //this.setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        done=false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!done){
            shooter.setPneuClear(Shooter.IN);
        }
        done=true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return isTimedOut();
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}