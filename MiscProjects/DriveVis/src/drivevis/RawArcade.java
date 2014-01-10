package drivevis;

/**
 *
 * @author sam
 */
public class RawArcade implements DriveMode {

    @Override
    public Powers calcLR(double moveValue, double rotateValue) {
	double leftPwm=moveValue+rotateValue;
        double rightPwm=moveValue-rotateValue;
        
        //Stay in bounds
        leftPwm = leftPwm > 1.0 ? 1.0 : leftPwm;
        leftPwm = leftPwm < -1.0 ? -1.0 : leftPwm;
        rightPwm = rightPwm > 1.0 ? 1.0 : rightPwm;
        rightPwm = rightPwm < -1.0 ? -1.0 : rightPwm;

    return new Powers(leftPwm, rightPwm);

    }

    @Override
    public String Name() {
	return "RAW ARCADE";
    }

}
