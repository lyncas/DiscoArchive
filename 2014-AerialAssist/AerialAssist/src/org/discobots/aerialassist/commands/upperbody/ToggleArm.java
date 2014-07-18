package org.discobots.aerialassist.commands.upperbody;

import edu.wpi.first.wpilibj.DigitalInput;
import org.discobots.aerialassist.commands.CommandBase;

public class ToggleArm extends CommandBase {

    DigitalInput armLimitSwitch;
    public boolean check;

    public ToggleArm() {
    }
    
    public ToggleArm(boolean on) {
        check = on;
    }

    protected void initialize() {
        rollerSub.setExtended(check);
        if (!compressorSub.isEnabled()) {
            compressorSub.on();
        }
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        rollerSub.setIntakeSpeed(0);
    }

    protected void interrupted() {
        end();
    }
}
