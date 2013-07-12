package org.discobots.frc.ascent.commands.shoot;

import org.discobots.frc.ascent.commands.CommandBase;

public class ShooterOpen extends CommandBase {
    
    public ShooterOpen() {
        requires(shooterSubsystem);
    }

    protected void initialize() {
    }

    protected void execute() {
        shooterSubsystem.setFrontPWM(shooterSubsystem.getSetpointPWM());
        shooterSubsystem.setBackPWM(shooterSubsystem.getSetpointPWM());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooterSubsystem.setFrontPWM(0);
        shooterSubsystem.setBackPWM(0);
    }

    protected void interrupted() {
        end();
    }
}