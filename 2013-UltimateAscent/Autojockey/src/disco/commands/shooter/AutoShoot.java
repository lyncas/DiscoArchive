/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.shooter;

import disco.commands.CommandBase;
import disco.commands.pneumatics.Shoot;

public class AutoShoot extends CommandBase {

    private int number_to_shoot;
    private double maxDelay=1000;
    private double minDelay = 250;
    private int count;
    private long shotTime;
    private long nowTime;

    public AutoShoot(int number, int maxDelay) {
        this.number_to_shoot = number;
        this.maxDelay = maxDelay;
        count = 0;
    }

    public AutoShoot(int number, int maxDelay, int minDelay) {
        this.number_to_shoot = number;
        this.maxDelay = maxDelay;
        this.minDelay = minDelay;
        count = 0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        count = 0;
        shotTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        nowTime = System.currentTimeMillis();
        if ((CommandBase.shooter.isOnTarget() && nowTime > (shotTime + minDelay)) || nowTime > (shotTime + maxDelay)) {
            new Shoot().start();
            shotTime = System.currentTimeMillis();
            count++;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return count >= number_to_shoot;
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
