/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.commands.drivetrain;

import disco.HW;
import disco.commands.CommandBase;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class DriveDistance extends CommandBase {
    private final double m_setpoint;
    private PIDController leftDistControl;
    private PIDController rightDistControl;
    private boolean finished=false;
    private double  m_kP=0.009,
		    m_kI=0.00003,
		    m_kD=0.008;
    
    private double m_leftDistCorrection=0;
    private double m_rightDistCorrection=0;
    private int m_leftInitial;
    private int m_rightInitial;
    
    private double left=0, right=0;
    private double leftLast=0, rightLast=0; 
    private final double m_rampLimit=0.01;
    
    private PIDOutput distOutputL = new PIDOutput() {

        public void pidWrite(double output) {
            m_leftDistCorrection=output;
        }
    };
    private PIDSource distSourceL = new PIDSource() {

        public double pidGet() {
            return offsetLeft();
        }
    };
    private PIDOutput distOutputR = new PIDOutput() {

        public void pidWrite(double output) {
            m_rightDistCorrection=output;
        }
    };
    private PIDSource distSourceR = new PIDSource() {

        public double pidGet() {
            return offsetRight();
        }
    };
    
    //give this the distance in inches
    public DriveDistance(double setpoint) {
        requires(drivetrain);
        m_setpoint=setpoint/HW.distancePerPulse;
        leftDistControl = new PIDController(m_kP, m_kI, m_kD, distSourceL, distOutputL);
        rightDistControl = new PIDController(m_kP, m_kI, m_kD, distSourceR, distOutputR);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        m_leftInitial = drivetrain.getLeftEncoder();
	m_rightInitial = drivetrain.getRightEncoder();
        
        leftLast=rightLast=0;
	
	leftDistControl.setSetpoint(m_setpoint);
        leftDistControl.enable();
	rightDistControl.setSetpoint(m_setpoint);
        rightDistControl.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        left=m_leftDistCorrection;
        right=m_rightDistCorrection;

        //ramp();
        
        double max = Math.max(Math.abs(left), Math.abs(right));
        if(max > 1){
            left = left / max;
            right = right / max;
        }
        drivetrain.tankDriveUnsmoothed(left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        double distRate=(drivetrain.getLeftRate()+drivetrain.getRightRate())/2;
        return Math.abs(leftDistControl.getError())<10 && Math.abs(rightDistControl.getError())<10 && Math.abs(distRate)<10;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.tankDrive(0,0);
        leftDistControl.disable();
        rightDistControl.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
    
    protected int offsetLeft(){
	return drivetrain.getLeftEncoder()-m_leftInitial;
    }
    protected int offsetRight(){
	return drivetrain.getRightEncoder()-m_rightInitial;
    }
    
    protected void ramp(){
        if(left-leftLast>m_rampLimit){
            left=leftLast+m_rampLimit;
        }
        else if(leftLast-left>m_rampLimit){
            left=leftLast-m_rampLimit;
        }
        if(right-rightLast>m_rampLimit){
            right=rightLast+m_rampLimit;
        }
        else if(rightLast-right>m_rampLimit){
            right=rightLast-m_rampLimit;
        }
        
        leftLast=left;
        rightLast=right;
    }
}
