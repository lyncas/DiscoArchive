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
	double move = moveValue;
	double turn = rotateValue;

	if (Math.abs(move) > threshold) {
	    turn = turn * (turnGain * Math.abs(move));
	}

	double tempLeft = move + turn;
	double tempRight = move - turn;

	tempLeft = tempLeft + skim(tempRight);
	tempRight = tempRight - skim(tempLeft);

	//remove this to get real version
	double max = Math.max(Math.abs(tempLeft), Math.abs(tempRight));
	if (max > 1) {
	    tempLeft = tempLeft / max;
	    tempRight = tempRight / max;
	}
	//end remove

	driveLeft = tempLeft;
	driveRight = tempRight;

	return new Powers(driveLeft,driveRight);
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
