/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robt.commands.flip;

import robt.commands.arm.*;
import edu.wpi.first.wpilibj.Joystick;
import robt.commands.CommandBase;
import robt.utils.GamePad;

/**
 *
 * @author Developer
 */
public class VariFlip extends CommandBase {
    
    private Joystick joy1;
    private GamePad gp;
    
    public VariFlip() {
        // Use requires() here to declare subsystem dependencies
        requires(flipper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        joy1 = oi.getJoy1();
        if (joy1 instanceof GamePad) {
            gp = (GamePad) joy1;
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (gp.getRawButton(gp.BTN_X)) {//SHOOT
            if (flipper.isTouchingBall()) {
                flipper.setPower(-1);
            } else {
                flipper.setPower(-0.3);
            }
        } else if (gp.getRawButton(gp.BTN_Y)) {
            flipper.setPower(0.35);
        } else {
            flipper.setPower(0);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        flipper.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
