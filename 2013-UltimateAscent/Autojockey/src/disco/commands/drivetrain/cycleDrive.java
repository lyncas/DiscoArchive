/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import disco.commands.CommandBase;

public class cycleDrive extends CommandBase {
    private static int mode=1;
    private boolean done;
    private static final int numModes=1;

    public cycleDrive() {

    }

    // Called just before this Command runs the first time
    protected void initialize() {
        done=false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        switch(mode){
            case 0: new LerpDrive().start();
                    break;
        }
        mode= mode<numModes-1 ? mode+1 : 0;
        done=true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
