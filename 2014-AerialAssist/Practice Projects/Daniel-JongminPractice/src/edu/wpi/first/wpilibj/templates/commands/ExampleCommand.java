package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.GamePad;

/**
 *
 * @author bradmiller
 */
public class ExampleCommand extends CommandBase {
    GamePad j1;

    public ExampleCommand(GamePad j) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(exampleSubsystem);
        j1 = j;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        exampleSubsystem.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(j1.getLY()>0.1&&j1.getRY()>0.1)
            exampleSubsystem.forward();
        else if(j1.getLY()>0.1&&(j1.getRY()>-0.1&&j1.getRY()<0.1))
            exampleSubsystem.leftTurn();
        else if(j1.getRY()>0.1&&(j1.getLY()>-0.1&&j1.getLY()<0.1))
            exampleSubsystem.rightTurn();
        else
            exampleSubsystem.stop();
            
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        exampleSubsystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        exampleSubsystem.stop();
    }
}
