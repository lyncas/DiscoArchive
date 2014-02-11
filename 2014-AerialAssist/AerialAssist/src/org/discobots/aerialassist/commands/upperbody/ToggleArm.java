package org.discobots.aerialassist.commands.upperbody;

import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Developer
 */
public class ToggleArm extends CommandBase {
    
    public ToggleArm() {
    }
    protected void initialize() {
        rollerSub.setExtended(!rollerSub.isExtended());
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
