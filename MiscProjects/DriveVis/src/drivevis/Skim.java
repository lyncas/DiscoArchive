package drivevis;

/**
 *
 * @author sam
 */
public class Skim implements DriveMode {

    private double skimGain = 0.5;

    @Override
    public Powers calcLR(double moveValue, double rotateValue) {
        double leftRaw = moveValue + rotateValue;
        double rightRaw = moveValue - rotateValue;

        double leftPwm = leftRaw + skim(rightRaw);
        double rightPwm = rightRaw + skim(leftRaw);

        //Stay in bounds
        leftPwm = leftPwm > 1.0 ? 1.0 : leftPwm;
        leftPwm = leftPwm < -1.0 ? -1.0 : leftPwm;
        rightPwm = rightPwm > 1.0 ? 1.0 : rightPwm;
        rightPwm = rightPwm < -1.0 ? -1.0 : rightPwm;

        return new Powers(leftPwm, rightPwm);

    }

    @Override
    public String Name() {
        return "SKIM (GAIN 0.5)";
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
