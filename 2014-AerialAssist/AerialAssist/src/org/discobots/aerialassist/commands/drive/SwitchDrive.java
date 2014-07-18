package org.discobots.aerialassist.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import org.discobots.aerialassist.commands.CommandBase;

public class SwitchDrive extends CommandBase {

    public static final int MODE_NULL = 0;
    public static final int MODE_AUTODETECT = 1;
    private int commandMode;
    public static final int MODE_CHEESYARCADE = 2;
    public static final int MODE_TANK = 3;
    public static final int MODE_TANKFIELDCENTRIC = 4;
    public static final int MODE_STICKDRIVE = 5;

    public SwitchDrive(int commandMode) {
        this.commandMode = commandMode;
    }

    protected void initialize() {
        if (commandMode == MODE_CHEESYARCADE) {
            new CheesyArcadeDrive().start();
        } else if (commandMode == MODE_TANK) {
            new TankDrive().start();
        } else if (commandMode == MODE_AUTODETECT) {
            Command driveCommand = drivetrainSub.getCurrentCommand();
            if (driveCommand instanceof CheesyArcadeDrive) {
                new TankDrive().start();
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
