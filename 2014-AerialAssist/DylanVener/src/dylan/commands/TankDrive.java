/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dylan.commands;
import dylan.utils.GamePad;
import dylan.commands.CommandBase;
import dylan.templates.OI;

/**
 *
 * @author Dylan
 */
public class TankDrive extends CommandBase {
    private GamePad gp1;
    private double left;
    private double right;
    public TankDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(CommandBase.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        gp1 = (GamePad) oi.getJoy1();     
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        left=gp1.getLY();
        right=gp1.getRY();
        drivetrain.tank(left,right);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.tank(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
