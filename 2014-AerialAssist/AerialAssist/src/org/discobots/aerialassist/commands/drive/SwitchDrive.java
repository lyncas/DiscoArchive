/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Dylan
 */
public class SwitchDrive extends CommandBase {

    private boolean newMode;

    public SwitchDrive(boolean check) {
        requires(drivetrain);
        newMode = check;
    }

    public SwitchDrive() {
        this(!drivetrain.getDriveState());
    }

    protected void initialize() {
        if (newMode) { // newMode == Drivetrain.TRACTION (true)
            drivetrain.shiftTraction();
            new TankDrive().start();
        } else { // newMode == Drivetrain.MECANUM (false)
            drivetrain.shiftMecanum();
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
