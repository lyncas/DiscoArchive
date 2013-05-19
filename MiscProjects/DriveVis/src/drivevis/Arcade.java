
package drivevis;

/**
 *
 * @author sam
 */
public class Arcade implements DriveMode {

    private double driveLeft = 0;
    private double driveRight = 0;

    @Override
    public Powers calcLR(double y, double x) {
	arcadeDrive(y, -x, false);
	return new Powers(driveLeft,driveRight);
    }

    @Override
    public String Name() {
	return "ARCADE";
    }

    //arcade drive from RobotDrive.java
    protected void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {

	double leftMotorSpeed;
	double rightMotorSpeed;

	if (squaredInputs) {
	    // square the inputs (while preserving the sign) to increase fine control while permitting full power
	    if (moveValue >= 0.0) {
		moveValue = (moveValue * moveValue);
	    } else {
		moveValue = -(moveValue * moveValue);
	    }
	    if (rotateValue >= 0.0) {
		rotateValue = (rotateValue * rotateValue);
	    } else {
		rotateValue = -(rotateValue * rotateValue);
	    }
	}

	if (moveValue > 0.0) {
	    if (rotateValue > 0.0) {
		leftMotorSpeed = moveValue - rotateValue;
		rightMotorSpeed = Math.max(moveValue, rotateValue);
	    } else {
		leftMotorSpeed = Math.max(moveValue, -rotateValue);
		rightMotorSpeed = moveValue + rotateValue;
	    }
	} else {
	    if (rotateValue > 0.0) {
		leftMotorSpeed = -Math.max(-moveValue, rotateValue);
		rightMotorSpeed = moveValue + rotateValue;
	    } else {
		leftMotorSpeed = moveValue - rotateValue;
		rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
	    }
	}

	setLeftRightMotorOutputs(leftMotorSpeed, rightMotorSpeed);
    }

    private void setLeftRightMotorOutputs(double leftMotorSpeed, double rightMotorSpeed) {
	driveLeft = leftMotorSpeed;
	driveRight = rightMotorSpeed;
    }
}
