
package disco.commands.drivetrain;

import disco.commands.CommandBase;

public class NegateDrive extends CommandBase {
    
    public NegateDrive() {
    }

    protected void initialize() {
        drivetrain.setNegated(!drivetrain.isNegated());
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
