package org.discobots.frc.ascent.commands.shoot;

import org.discobots.frc.ascent.commands.CommandBase;

public class RPMSetpointIncrement extends CommandBase {
    private final double incrementBy;
    public RPMSetpointIncrement(double incrementBy) {
        this.incrementBy = incrementBy;
    }

    protected void initialize() {
        shooterSubsystem.setSetpointRPM(shooterSubsystem.getSetpointRPM() + incrementBy);
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
