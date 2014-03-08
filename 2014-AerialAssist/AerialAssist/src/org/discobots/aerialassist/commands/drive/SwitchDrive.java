/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.commands.drive;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.subsystems.Drivetrain;

/**
 *
 * @author Dylan
 */
public class SwitchDrive extends CommandBase {

    public static final int MODE_NULL = 0;
    public static final int MODE_AUTODETECT = 1;
    private int omniOrTraction;
    public static final int MODE_OMNIWHEEL = 2;
    public static final int MODE_TRACTION = 3;
    private int commandMode;
    public static final int MODE_CHEESYARCADE = 2;
    public static final int MODE_TANK = 3;
    public static final int MODE_TANKFIELDCENTRIC = 4;
    public static final int MODE_STICKDRIVE = 5;

    public SwitchDrive(int omniOrTraction, int commandMode) {
        this.omniOrTraction = omniOrTraction;
        this.commandMode = commandMode;
    }

    protected void initialize() {
        if (omniOrTraction == MODE_OMNIWHEEL) {
            drivetrainSub.shiftOmni();
        } else if (omniOrTraction == MODE_TRACTION) {
            drivetrainSub.shiftTraction();
        } else if (omniOrTraction == MODE_AUTODETECT) {
            if (drivetrainSub.getDriveState() == Drivetrain.OMNI) {
                drivetrainSub.shiftTraction();
            } else if (drivetrainSub.getDriveState() == Drivetrain.TRACTION) {
                drivetrainSub.shiftOmni();
            }
        }
        
        if (commandMode == MODE_CHEESYARCADE) {
            new CheesyArcadeDrive().start();
        } else if (commandMode == MODE_TANK) {
            new TankDrive().start();
        } else if (commandMode == MODE_TANKFIELDCENTRIC) {
            new FieldCentricDrive().start();
        } else if (commandMode == MODE_STICKDRIVE) {
            new StickDrive().start();
        } else if (commandMode == MODE_AUTODETECT) {
            Command driveCommand = drivetrainSub.getCurrentCommand();
            if (driveCommand instanceof CheesyArcadeDrive) {
                new TankDrive().start();
            } else if (driveCommand instanceof TankDrive) {
                new FieldCentricDrive().start();
            } else if (driveCommand instanceof FieldCentricDrive) {
                new StickDrive().start();
            } else {
                new CheesyArcadeDrive().start();
            }
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
