/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import disco.commands.CommandBase;
import disco.commands.drivetrain.manual.LerpDrive;

public class cycleDrive extends CommandBase {

    private static int mode = 0;
    private static final int numModes = 3;

    public cycleDrive() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        switch (mode) {
            case 0:
                new LerpDrive().start();
                break;
        }
        mode = mode < numModes - 1 ? mode + 1 : 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
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
