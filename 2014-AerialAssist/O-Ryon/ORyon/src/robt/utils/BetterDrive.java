package robt.utils;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class BetterDrive extends RobotDrive {
    double leftPrev=0;
    double rightPrev=0;
    double m_rampLimit=0.18;

    public BetterDrive(SpeedController a, SpeedController b,
            SpeedController c, SpeedController d) {
        super(a,b,c,d);

        leftPrev=0;
        rightPrev=0;
    }

    private double Map(double stickVal){
	double x=10.0*stickVal;
	//the secret formula
	//return 0.024 + 0.296*x - 0.085*x*x + 0.011*x*x*x - 0.000447*x*x*x*x;
	return 0.308*x + 0.0327*x*x*x + 0.000113*x*x*x*x*x - 0.147*x*x - 0.00314*x*x*x*x;
    }

    public void tankDrive(double leftValue, double rightValue, boolean smoothed){
        tankDrive(leftValue,rightValue,smoothed,false);
    }

    public void tankDrive(double leftValue, double rightValue, boolean smoothed, boolean ramp) {
	if(smoothed){
	    //what? no sign fuction? dumdum.
	    double leftSign= leftValue>=0 ? 1 : -1;
	    double rightSign= rightValue>=0 ? 1 : -1;

	    leftValue=leftSign*Map(Math.abs(leftValue));
	    rightValue=rightSign*Map(Math.abs(rightValue));
	}

        if(ramp){
            if(leftValue-leftPrev>m_rampLimit){
                leftValue=leftPrev+m_rampLimit;
            }
            else if(leftPrev-leftValue>m_rampLimit){
                leftValue=leftPrev-m_rampLimit;
            }
            if(rightValue-rightPrev>m_rampLimit){
                rightValue=rightPrev+m_rampLimit;
            }
            else if(rightPrev-rightValue>m_rampLimit){
                rightValue=rightPrev-m_rampLimit;
            }
        }

        leftPrev=leftValue;
        rightPrev=rightValue;
	super.tankDrive(leftValue, rightValue, false);
    }

    public void tankDrive(double leftValue,double rightValue){
        tankDrive(leftValue, rightValue,false,false);
    }

    public void arcadeDrive(double moveValue, double rotateValue,boolean smoothed) {
	leftPrev = moveValue - rotateValue;
        rightPrev = moveValue + rotateValue;
        if(smoothed){
	    //what? no sign fuction? dumdum.
	    double moveSign= moveValue>=0 ? 1 : -1;
	    double rotateSign= rotateValue>=0 ? 1 : -1;

	    moveValue=moveSign*Map(Math.abs(moveValue));
	    rotateValue=rotateSign*Map(Math.abs(rotateValue));
	}
	super.arcadeDrive(moveValue, rotateValue, false);//no thanks, I'll scale those myself. Reminds me of a story...
	/*Harold walks into the toilet paper shop, and the guy at the counter says
	 * "what color toilet paper would you like?"
	 * Harold says "white please. I'll color it myself."
	 * Adapted from Captain Underpants
	 */
    }

    public void arcadeDrive(double moveValue,double turnValue){
        leftPrev = moveValue - turnValue;
        rightPrev = moveValue + turnValue;
	super.arcadeDrive(moveValue, moveValue, false);
    }
    public double getLeftPrev() {
        return leftPrev;
    }
    public double getRightPrev() {
        return rightPrev;
    }
}
