package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;

public class SwitchShot extends CommandBase {

    public SwitchShot() {
        requires(pneumatapultSub);
    }

    protected void initialize() {
    }

    protected void execute() {
        pneumatapultSub.changeMode();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
