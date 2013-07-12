package org.discobots.frc.ascent.commands.shoot;

import org.discobots.frc.ascent.commands.CommandBase;

public class ShooterBang extends CommandBase {
    
    public ShooterBang() {
        requires(shooterSubsystem);
    }

    protected void initialize() {
    }

    protected void execute() {
        if (shooterSubsystem.getFrontRPM() < shooterSubsystem.getSetpointRPM()) {
            shooterSubsystem.setFrontPWM(1);
        } else if (shooterSubsystem.getFrontRPM() > shooterSubsystem.getSetpointRPM()) {
            shooterSubsystem.setFrontPWM(-1);
        } else {
            shooterSubsystem.setFrontPWM(0);
        }
        if (shooterSubsystem.getBackRPM() < shooterSubsystem.getSetpointRPM()) {
            shooterSubsystem.setBackPWM(1);
        } else if (shooterSubsystem.getBackRPM() > shooterSubsystem.getSetpointRPM()) {
            shooterSubsystem.setBackPWM(-1);
        } else {
            shooterSubsystem.setBackPWM(0);
        }
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