  package org.discobots.aerialassist.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.commands.CommandBase;

public class CheesyArcadeDrive extends CommandBase {

    private double movePrev, turnPrev;
    
    private double rampThreshold = .1;
    private double move;
    private double turn;
    private double driveLeft = 0, driveRight = 0;
    private double threshold = 0.05;
    private double a = 0.25;
    private double b = 1;
    private double oldTurn = 0.0;
    private double quickStopAccumulator;
    double negInertiaAccumulator = 0.0;

    private boolean isQuickTurn = true;

    public CheesyArcadeDrive() {
        requires(drivetrainSub);
    }

    protected void initialize() {
        SmartDashboard.putNumber("CheesyArcadeDrive a Constant", a);
        SmartDashboard.putNumber("CheesyArcadeDrive b Constant", b);
    }

    protected void execute() {
        a = SmartDashboard.getNumber("CheesyArcadeDrive a Constant", a);
        b = SmartDashboard.getNumber("CheesyArcadeDrive b Constant", b);

        move = turn = 0;
        calculateInput();

        double negInertia = turn - oldTurn;
        oldTurn = turn;

        //SINE SCALING OMITTED
        double sensitivity = 0.85;
        // Negative inertia!

        double negInertiaScalar = 5.0;

        if (turn * negInertia > 0) {//same direction: accelerating
            negInertiaScalar = 2.5;
        } else {//decelerating
            if (Math.abs(turn) > 0.65) {
                negInertiaScalar = 5.0;
            } else {
                negInertiaScalar = 3.0;
            }
        }
        double negInertiaPower = negInertia * negInertiaScalar;
        negInertiaAccumulator += negInertiaPower;

        turn = turn + negInertiaAccumulator;
        if (negInertiaAccumulator > 1) {
            negInertiaAccumulator -= 1;
        } else if (negInertiaAccumulator < -1) {
            negInertiaAccumulator += 1;
        } else {
            negInertiaAccumulator = 0;
        }

        // Quickturn!
        if (isQuickTurn) {
            if (Math.abs(move) < 0.2) {
                double alpha = 0.1;
                quickStopAccumulator = (1 - alpha) * quickStopAccumulator + alpha
                        * limit(turn, 1.0) * 5;
            }
            sensitivity = 1.0;
        }
        if (negInertia < -0.15) {//request decel a lot
            turn -= quickStopAccumulator;
            if (quickStopAccumulator > 1) {
                quickStopAccumulator -= 1;
            } else if (quickStopAccumulator < -1) {
                quickStopAccumulator += 1;
            } else {
                quickStopAccumulator = 0.0;
            }
        }

        //The important part
        if (move >= 0 && turn >= 0) {//Q1
            driveRight = turn * b + move * (1 - turn * b);
            driveLeft = -1 * turn * b + move * ((1 - turn * (a + 1)) + turn * b);
        } else if (move >= 0 && turn < 0) {//Q2
            driveRight = turn * b + move * (1 + turn * (1 + a) - turn * b);
            driveLeft = -1 * turn * b + move * (1 + turn * b);
        } else if (move < 0 && turn >= 0) {//Q4
            driveRight = turn * b - move * (turn * (a + 1) - 1 - turn * b);
            driveLeft = -1 * turn * b - move * (-1 + turn * b);
        } else if (move < 0 && turn < 0) {//Q3
            driveRight = turn * b - move * (-1 - turn * b);
            driveLeft = -1 * turn * b - move * (-1 - turn * (a + 1) + turn * b);
        }

        drivetrainSub.tankDrive(-driveLeft, -driveRight);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        drivetrainSub.tankDrive(0, 0);
    }

    protected void interrupted() {
        end();
    }

    protected void calculateInput() {

        move = -oi.getRawAnalogStickALX();
        move = Math.abs(move) > threshold ? move : 0;
        turn = oi.getRawAnalogStickALY();
        turn = Math.abs(turn) > threshold ? turn : 0;

        double moveR = -oi.getRawAnalogStickARX()* 3 / 4;    //I changed this because the right stick is too sluggish.
        moveR = Math.abs(moveR) > threshold ? moveR : 0;
        double turnR = oi.getRawAnalogStickARY()* 3 / 4;   //I changed this because the right stick is too sluggish.
        turnR = Math.abs(turnR) > threshold ? turnR : 0;

        move += moveR;
        turn += turnR;

        if (movePrev - move > rampThreshold) {
            move = movePrev - rampThreshold;
        } else if (move - movePrev > rampThreshold) {
            move = movePrev + rampThreshold;
        }
        if (turnPrev - turn > rampThreshold) {
            turn = turnPrev - rampThreshold;
        } else if (turn - turnPrev > rampThreshold) {
            turn = turnPrev + rampThreshold;
        }

        movePrev = move;
        turnPrev = turn;

    }

    public static double limit(double v, double limit) {
        return (Math.abs(v) < limit) ? v : limit * (v < 0 ? -1 : 1);
    }
}
