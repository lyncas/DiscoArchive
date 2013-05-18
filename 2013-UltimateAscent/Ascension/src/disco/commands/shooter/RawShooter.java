/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.shooter;

import disco.commands.CommandBase;

public class RawShooter extends CommandBase {
    private boolean done;
    
    public RawShooter() {
        requires(shooter);
        requires(compressor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        done=false;
	compressor.set(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
            shooter.setFrontPower(shooter.frontPWM);
            shooter.setBackPower(shooter.backPWM);
    }        /*
        double set=shooter.getSetpoint();
        if(shooter.getFrontRPM()<set-1000){
            shooter.setFrontPower(1);
        }
        else {
            shooter.setFrontPower(shooter.frontPWM);
        }
        if(shooter.getBackRPM()<set-150){
            shooter.setBackPower(1);
        }
        else {
            shooter.setBackPower(shooter.backPWM);
        }
        shooter.setOnTarget(true);
*/
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        shooter.setPower(0);
	compressor.set(true);
        shooter.setOnTarget(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
