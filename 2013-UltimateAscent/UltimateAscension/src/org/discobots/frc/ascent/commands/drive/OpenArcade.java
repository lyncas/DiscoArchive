package org.discobots.frc.ascent.commands.drive;

import org.discobots.frc.ascent.commands.CommandBase;

public final class OpenArcade extends CommandBase {
    public OpenArcade() {
        requires(drivetrainSubsystem);
    }
    
    private final double deadThreshold = 0.05;
    
    protected void initialize() {
    }

    protected void execute() {
        double move = calculateMoveInput();
        double turn = calculateTurnInput();
        double left = calculateOutput(move, turn);
        double right =  calculateOutput(move, -turn);
        left = trimExtraOutput(left);
        right = trimExtraOutput(right);
        drivetrainSubsystem.tankDrive(left, right, false, true);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        drivetrainSubsystem.arcadeDrive(0, 0, false, false);
    }

    protected void interrupted() {
        end();
    }
    
    private double calculateMoveInput() {
        double move = oi.getRawAnalogStickALY() / 2;
        move += oi.getRawAnalogStickARY();
        move = Math.abs(move) < deadThreshold ? 0 : move;
        return Math.abs(move) > 1 ? 1 : move;
    }
    private double calculateTurnInput() {
        double turn = oi.getRawAnalogStickALX() / 2;
        turn += oi.getRawAnalogStickARX();
        turn = Math.abs(turn) < deadThreshold ? 0 : turn;
        return Math.abs(turn) > 1 ? 1 : turn;
    }
    private double calculateOutput(double move, double turn) {
        return turn + 0.12*move*turn + move*move*move + 0.33*move*turn*turn*Math.sin(turn) - move*move*turn - 0.39*move*move*move*turn*turn;
    }
    private double trimExtraOutput(double a) {
        if (a > 1) a = 1;
        if (a < -1) a = -1;
        return a;
    }
}