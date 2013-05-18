/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.pneumatics;

import disco.commands.CommandBase;
import disco.subsystems.Shifter;


public class ShiftUp extends CommandBase {
    boolean done;

    public ShiftUp() {
	requires(shifter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	done=false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	if(!done){
	    shifter.setLeftShifter(Shifter.GEAR_HIGH);
	    shifter.setRightShifter(Shifter.GEAR_HIGH);
	}
	done=true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;//we want this to return false because it is an override for the autoShift. If this ever ends autoShift will take over again.
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