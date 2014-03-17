package org.discobots.aerialassist.commands.drive;

import org.discobots.aerialassist.commands.CommandBase;

public class SetMiniCimUsage extends CommandBase {

    boolean a;

    public SetMiniCimUsage(boolean a) {
        this.a = a;
    }

    public SetMiniCimUsage() {
        this.a = !drivetrainSub.getMiniCimUsage();
    }

    protected void initialize() {
        drivetrainSub.setMiniCimUsage(a);
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
