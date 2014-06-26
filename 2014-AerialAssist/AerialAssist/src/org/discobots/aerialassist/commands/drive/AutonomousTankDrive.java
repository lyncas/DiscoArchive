package org.discobots.aerialassist.commands.drive;

import org.discobots.aerialassist.commands.CommandBase;

public class AutonomousTankDrive extends CommandBase {

    private final long maxRunTime;
    private long startTime;
    double magnitude;
    double direction;
    double rotation;
    double left;
    double right;
    double power = 0;

    public AutonomousTankDrive(double l, double r, int time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrainSub);
        requires(rollerSub);
        maxRunTime = time;
        left = -l;
        right = r;
    }

    public AutonomousTankDrive(double l, double r, double p, int time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrainSub);
        requires(rollerSub);
        maxRunTime = time;
        left = l;
        right = -r;
        power = p;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrainSub.tankDrive(0, 0);
        rollerSub.setIntakeSpeed(0);
        startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrainSub.tankDrive(left, right);
        rollerSub.setIntakeSpeed(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (System.currentTimeMillis() - startTime > maxRunTime) {
            System.out.println("Killing AutonomousTankDrive");
        }
        return System.currentTimeMillis() - startTime > maxRunTime;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrainSub.tankDrive(0, 0);
        rollerSub.setIntakeSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
