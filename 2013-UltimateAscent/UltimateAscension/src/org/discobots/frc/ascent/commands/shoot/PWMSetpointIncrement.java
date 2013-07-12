package org.discobots.frc.ascent.commands.shoot;

import org.discobots.frc.ascent.commands.CommandBase;

public class PWMSetpointIncrement extends CommandBase {
    private final double incrementBy;
    public PWMSetpointIncrement(double incrementBy) {
        this.incrementBy = incrementBy;
    }

    protected void initialize() {
        shooterSubsystem.setSetpointPWM(shooterSubsystem.getSetpointPWM() + incrementBy);
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
