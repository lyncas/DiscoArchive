package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;

public class ToggleArmAutonomous extends CommandBase {

    public static int maxRunTime = 500; // milli
    private long endTime;
    private boolean check;

    public ToggleArmAutonomous(boolean on) throws Exception {
        throw new Exception();
    }

    protected void initialize() {
        rollerSub.setExtended(check);
        endTime = System.currentTimeMillis() + maxRunTime;
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
        if (System.currentTimeMillis() > endTime) {
            return true;
        } else {
            return false;
        }
    }

    protected void end() {
        rollerSub.setIntakeSpeed(0);
    }

    protected void interrupted() {
        end();
    }
}
