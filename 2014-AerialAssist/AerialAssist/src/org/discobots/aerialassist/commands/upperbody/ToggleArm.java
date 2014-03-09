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
    public boolean check;
    public ToggleArm(boolean on) {
        check = on;
        //armLimitSwitch = new DigitalInput(HW.armLimitSwitchChannel);
    }
    protected void initialize() {
//        rollerSub.setIntakeSpeed(0);    //Get rid of all of the commented out lines if there is a limit switch.
        if(compressorSub.canRunPneumatics())
            rollerSub.setExtended(check);  //I reversed it because isExtended now returns the opposite.
        if (!compressorSub.isEnabled()){
            compressorSub.on();
        }
    }
    protected void execute() {
        if (rollerSub.isExtended())
            rollerSub.setIntakeSpeed(.2*Intake.IN);
    }
    protected boolean isFinished() {
        return true;
 //       return armLimitSwitch.get();
    }
    protected void end() {
        rollerSub.setIntakeSpeed(0);
    }
    protected void interrupted() {
       end();
    }
}
