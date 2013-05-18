package disco.commands.drivetrain;

import disco.commands.CommandBase;
import disco.utils.GamePad;
import edu.wpi.first.wpilibj.Joystick;

public class RawJoyTank extends CommandBase {
    protected double left=0;
    protected double right=0;
    protected Joystick joy1;
    protected GamePad gp;
    protected double threshold=0.05;

    public RawJoyTank() {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	joy1=oi.getJoy1();
	if(joy1 instanceof GamePad){
	    gp=(GamePad) joy1;
	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute(){
	calculateInput();
	drivetrain.tankDrive(left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
	drivetrain.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }

    protected void calculateInput(){
	if(gp != null){
            //System.out.println(gp.getLY() + " " + gp.getRY());
	    left=gp.getLY();
	    left=Math.abs(left)>threshold ? left : 0;
	    right=gp.getRY();
	    right=Math.abs(right)>threshold ? right : 0;
	}
	else{
	    //left=joy1.getAxis(Joystick.AxisType.kY);
	    //right=joy1.getAxis(Joystick.AxisType.kX);
	    left=0;
	    right=0;
	    throw new IllegalStateException("Tank drive only works with game pads for now.");
	    //drivetrain.driveArcade(left,right);
	}
    }
}