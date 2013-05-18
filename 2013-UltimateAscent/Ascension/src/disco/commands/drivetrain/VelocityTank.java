/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import disco.HW;
import disco.utils.DiscoPIDController;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;


/**
 * In theory produces a completely linear drivetrain, but since we have no working
 * encoders we can't test this.
 * 
 * @author sam
 */
public class VelocityTank extends RawJoyTank {
    protected DiscoPIDController leftControl,rightControl;
    private double  m_kP=0, //TODO: test these
                    m_kI=0,
                    m_kD=0,
                    m_kF=1/HW.maxFPS;
    protected double m_leftOutput=0;
    protected double m_rightOutput=0;

    public VelocityTank() {
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        super.initialize();

        leftControl=new DiscoPIDController(m_kP,m_kI,m_kD,m_kF,leftSource,leftOutput,0.02);
        leftControl.setSetpoint(0);
        rightControl=new DiscoPIDController(m_kP,m_kI,m_kD,m_kF,rightSource,rightOutput,0.02);
        rightControl.setSetpoint(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!leftControl.isEnable()){    leftControl.enable();   }
        if(!rightControl.isEnable()){   rightControl.enable();  }
        left=right=0;
        calculateInput();
        scale();//Quadratic Should be sufficient for this
        ramp();

        leftControl.setSetpoint(left*HW.maxFPS);    //scale joystick values (0 to 1) to 0 to maxFPS
        rightControl.setSetpoint(right*HW.maxFPS);

        left=m_leftOutput;
        right=m_rightOutput;

        //I don't think we want this. both should be independent.
        //normalize();
        drivetrain.tankDriveUnsmoothed(left, right);    //already took care of scaling and ramping
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        leftControl.disable();
        rightControl.disable();
        drivetrain.tankDriveUnsmoothed(0, 0);
    }


    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }

    protected void scale(){
        double leftSign= left>=0 ? 1 : -1;
	double rightSign= right>=0 ? 1 : -1;

	left=leftSign*left*left;
	right=rightSign*right*right;
    }

    protected void ramp(){
        //right now does nothing. May want to add.
        left=left;
        right=right;
    }

    protected void normalize(){
        //normalize if we are out of range (based on RobotDrive, which only does this for mecanum)
	    double max = Math.max(Math.abs(left), Math.abs(right));
	    if(max > 1){
                left = left / max;
                right = right / max;
	    }
    }


    public DiscoPIDController getLeftConrol(){
        return leftControl;
    }
    public DiscoPIDController getRightConrol(){
        return rightControl;
    }

    private PIDOutput leftOutput = new PIDOutput() {
        public void pidWrite(double output) {
            m_leftOutput=output;
        }
    };
    private PIDSource leftSource = new PIDSource() {
        public double pidGet() {
            return drivetrain.getLeftRate();
        }
    };
    private PIDOutput rightOutput = new PIDOutput() {
        public void pidWrite(double output) {
            m_rightOutput=output;
        }
    };
    private PIDSource rightSource = new PIDSource() {
        public double pidGet() {
            return drivetrain.getRightRate();
        }
    };
}
