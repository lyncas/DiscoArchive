package org.discobots.aerialassist.commands.upperbody;

import edu.wpi.first.wpilibj.DigitalInput;
import org.discobots.aerialassist.HW;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Developer
 */
public class ToggleArm extends CommandBase {
    
    DigitalInput armLimitSwitch;
    
    public ToggleArm() {
        armLimitSwitch = new DigitalInput(HW.armLimitSwitchChannel);
    }
    protected void initialize() {
        rollerSub.setIntakeSpeed(0);
        rollerSub.setExtended(rollerSub.isExtended());  //I reversed it because isExtended now returns the opposite.
        if (!compressorSub.isEnabled())
        {
            compressorSub.on();
        }
    }
    protected void execute() {
        if (rollerSub.isExtended())
            rollerSub.setIntakeSpeed(.2*Intake.IN);
    }
    protected boolean isFinished() {
        return armLimitSwitch.get();
    }
    protected void end() {
        rollerSub.setIntakeSpeed(0);
    }
    protected void interrupted() {
        end();
    }
}
