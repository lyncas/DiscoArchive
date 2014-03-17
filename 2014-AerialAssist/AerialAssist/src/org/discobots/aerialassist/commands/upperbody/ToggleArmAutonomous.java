package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;

public class ToggleArmAutonomous extends CommandBase {

    public boolean check;

    public ToggleArmAutonomous(boolean on) throws Exception {
        throw new Exception();
    }

    protected void initialize() {
        if (compressorSub.canRunPneumatics()) {
            rollerSub.setExtended(check);
        }
        if (!compressorSub.isEnabled()) {
            compressorSub.on();
        }
    }

    protected void execute() {
        if (rollerSub.isExtended()) {
            rollerSub.setIntakeSpeed(.2 * Intake.IN);
        }
    }

    protected boolean isFinished() {
        if (!check) {
            return rollerSub.getLimit();
        } else {
            return true;
        }
    }

    protected void end() {
        rollerSub.setIntakeSpeed(0);
    }

    protected void interrupted() {
        end();
    }
}
