/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.commands.arm;

import edu.wpi.first.wpilibj.Joystick;
import robt.commands.CommandBase;
import robt.utils.GamePad;

/**
 *
 * @author Sam Dietrich / Team ORyon
 */
public class VariIntake extends CommandBase {

    private Joystick joy1;
    private GamePad gp;

    public VariIntake() {
        // Use requires() here to declare subsystem dependencies
        requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        joy1 = oi.getJoy1();
        if (joy1 instanceof GamePad) {
            gp = (GamePad) joy1;
        } else{
            throw new IllegalStateException("Must use logitech gamepda");
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(gp.getRawButton(gp.TRIGGER_L)){
            intake.setIntake(-1);
        }
        else if(gp.getRawButton(gp.TRIGGER_R)){
            intake.setIntake(1);
        }
        else{
            intake.setIntake(0);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        intake.setIntake(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
