/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.subsystems.Drivetrain;

/**
 *
 * @author Dylan
 */
public class SwitchDrive extends CommandBase {

    private boolean newMode;

    public SwitchDrive(boolean check) {
        requires(drivetrainSub);
        newMode = check;
    }

    public SwitchDrive() {
        this(!drivetrainSub.getDriveState());
    }

    protected void initialize() {
        if (newMode == Drivetrain.TRACTION) {// (true)
            drivetrainSub.shiftTraction();
            new TankDrive().start();
        } else if (newMode == Drivetrain.MECANUM) { // (false)
            drivetrainSub.shiftMecanum();
            new MecanumDrive().start();
        }
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
