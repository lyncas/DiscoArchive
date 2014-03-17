package org.discobots.aerialassist.commands;

public class SetPneumaticsRunnable extends CommandBase {

    private boolean set;

    public SetPneumaticsRunnable(boolean a) {
        set = a;
    }

    public SetPneumaticsRunnable() {
        set = !compressorSub.canRunPneumatics();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        compressorSub.setRunPneumatics(set);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
