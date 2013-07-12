package org.discobots.frc.ascent.commands.drive;

import org.discobots.frc.ascent.commands.CommandBase;

public final class OpenTank extends CommandBase {
    
    private final double deadThreshold = 0.025;
    
    public OpenTank() {
        requires(drivetrainSubsystem);
    }
    
    protected void initialize() {
        drivetrainSubsystem.setInvert(true);
    }

    protected void execute() {
        double left = updateLeftInput();
        double right = updateRightInput();
        
        
        
        drivetrainSubsystem.tankDrive(left, right, true, true);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        drivetrainSubsystem.tankDrive(0, 0, false, false);
    }

    protected void interrupted() {
        end();
    }
    
    private double updateLeftInput() {
        double left = oi.getRawAnalogStickALY();
        return Math.abs(left) < deadThreshold ? 0 : left;
        
    }
    private double updateRightInput() {
        double right = oi.getRawAnalogStickARY();
        return Math.abs(right) < deadThreshold ? 0 : right;
    }
}