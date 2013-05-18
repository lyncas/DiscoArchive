package disco.commands.shooter;

import disco.commands.CommandBase;


/**
 * This class must be improved before it can be included in main code.
 * It must be consistent. Currently whatever speed the wheel was spinning at will be stored.
 *
 */
public class AutoSetRPM extends CommandBase {
    boolean done;
    public AutoSetRPM() {
    }
    protected void initialize() {
        done = false;
    }

    protected void execute() {
        if (shooter.getSetpoint() == 5700 && shooter.difference == 000) {
            shooter.setSetpoint(7000);
            shooter.difference = 0;
        } else if (shooter.getSetpoint() == 7000 && shooter.difference == 000) {
            shooter.setSetpoint(shooter.prevRPM);
            shooter.difference = shooter.prevRPM;
        } else {
            shooter.prevDiff = shooter.difference;
            shooter.prevRPM = shooter.getFrontRPM();
            shooter.setSetpoint(5700);
            shooter.difference = 000;
        }
        done = true;
    }

    protected boolean isFinished() {
        return done;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
}
