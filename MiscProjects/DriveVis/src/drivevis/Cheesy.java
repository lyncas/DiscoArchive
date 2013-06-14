package drivevis;

/**
 *
 * @author sam
 */
public class Cheesy implements DriveMode {

    private double driveLeft = 0;
    private double driveRight = 0;
    private double skimGain = 0.2;
    private double turnGain = 1.3;
    private double threshold = 0.2;

    @Override
    public Powers calcLR(double moveValue, double rotateValue) {
	double throttle = moveValue;
	double wheel = rotateValue;

	 double wheelNonLinearity = 0.6;
      // Apply a sin function that's scaled to make it feel better.
//      wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) /
//          Math.sin(Math.PI / 2.0 * wheelNonLinearity);
//      wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) /
//          Math.sin(Math.PI / 2.0 * wheelNonLinearity);
      
      double sensitivity = 1.7;

    double angularPower;
    double linearPower=throttle;
      angularPower = Math.abs(throttle) * wheel * sensitivity;

      double leftPwm, rightPwm, overPower=0;
      rightPwm = leftPwm = linearPower;
    leftPwm += angularPower;
    rightPwm -= angularPower;

    if (leftPwm > 1.0) {
      rightPwm -= overPower * (leftPwm - 1.0);
      leftPwm = 1.0;
    } else if (rightPwm > 1.0) {
      leftPwm -= overPower * (rightPwm - 1.0);
      rightPwm = 1.0;
    } else if (leftPwm < -1.0) {
      rightPwm += overPower * (-1.0 - leftPwm);
      leftPwm = -1.0;
    } else if (rightPwm < -1.0) {
      leftPwm += overPower * (-1.0 - rightPwm);
      rightPwm = -1.0;
    }

    return new Powers(leftPwm, rightPwm);

    }

    @Override
    public String Name() {
	return "CHEESY";
    }

    private double skim(double v) {
	if (v > 1.0) {
	    return -((v - 1.0) * skimGain);
	} else if (v < -1.0) {
	    return -((v + 1.0) * skimGain);
	} else {
	    return 0;
	}
    }
}
