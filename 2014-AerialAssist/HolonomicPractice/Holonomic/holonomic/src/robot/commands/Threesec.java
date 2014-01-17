/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.commands;
import robot.GamePad;

/**
 *
 * @author Dylan
 */
public class Threesec extends CommandBase {
    long time;
    int delta;
    double power;
    public Threesec() {
        requires(RMotor);
        delta=RMotor.gett();
        power=RMotor.getp();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        time=System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        RMotor.set(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return System.currentTimeMillis()>=time+delta;    
    }

    // Called once after isFinished returns true
    protected void end() {
        RMotor.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
