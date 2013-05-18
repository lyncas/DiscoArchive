/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;


public class AssistedTank extends RawJoyTank {
    protected PIDController turnControl;
    private double  m_kP=0.001,
		    m_kI=0.00005,
		    m_kD=0;
    //joystick differences below this number are assumed to be intended to be identical.
    private double m_correctionThreshold=0.3;

    protected double m_correction=0;
    protected int m_leftInitial=0;
    protected int m_rightInitial=0;

    private PIDOutput turnOutput = new PIDOutput() {

        public void pidWrite(double output) {
            usePIDOutput(output);
        }
    };
    private PIDSource turnSource = new PIDSource() {

        public double pidGet() {
            return returnPIDInput();
        }
    };


    public AssistedTank() {
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	super.initialize();
	m_leftInitial = drivetrain.getLeftEncoder();
	m_rightInitial = drivetrain.getRightEncoder();

	turnControl = new PIDController(m_kP, m_kI, m_kD, turnSource, turnOutput);
	//turnControl.enable();
	turnControl.setSetpoint(0); //minimize error
        //SmartDashboard.putData("Encoder PID", turnControl);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        left=right=0;
	calculateInput();
	if(Math.abs(left-right)<=m_correctionThreshold && left!=0 && right!=0){
            //we should correct
            turnControl.enable();
	    left += m_correction;
	    right -= m_correction;
	    //normalize if we are out of range (based on RobotDrive, which only does this for mecanum)
	    double max = Math.max(Math.abs(left), Math.abs(right));
	    if(max > 1){
                left = left / max;
                right = right / max;
	    }
	}
        else {
	    //driver is doing something else. start over.
	    if(turnControl.isEnable()){
		turnControl.disable();
	    }
	    m_leftInitial=drivetrain.getLeftEncoder();
	    m_rightInitial=drivetrain.getRightEncoder();
	}
        //don't drive forwards if we are touching the pyramid and not pushing the right bumper
//        if(!gp.getRawButton(gp.BTN_B)){
//            if(drivetrain.getLeftPyramid()){
//                left = left>0 ? 0 : left;  
//            }
//            if(drivetrain.getRightPyramid()){
//                right = right>0 ? 0 : right;  
//            }
//        }
        
        //finally drive
        drivetrain.tankDrive(left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
	turnControl.disable();
        drivetrain.tankDrive(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }

    private double returnPIDInput(){
	return offsetLeft()-offsetRight();
    }

    private void usePIDOutput(double output){
	m_correction=output;
    }

    protected int offsetLeft(){
	return drivetrain.getLeftEncoder()-m_leftInitial;
    }
    protected int offsetRight(){
	return drivetrain.getRightEncoder()-m_rightInitial;
    }
    
    public PIDController getController(){
        return turnControl;
    }

}