package DriveControllers;

import Utils.DiscoUtils;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Nelson
 */
public class VelocityController {

    final double ki = 0.02;
    double iterm = 0.0;
    final double itermMax = 20;
    public double goalVelocity;
    //final double[] velocityOutput =
    //int resolution = 20;
    Encoder encoder;
    Jaguar motor;
    double oldDist;
    double oldTime;
    boolean reversed = false;
    double output = 0.0;
    double distTraveled;
    double goalDistance = 0.0;
    double velocity = 0.0;

    public VelocityController(Encoder e, Jaguar m) {
        encoder = e;
        motor = m;
    }

    public VelocityController(Encoder e, Jaguar m, boolean r) {
        encoder = e;
        motor = m;
        reversed = r;
    }

    /*the following two methods are for collecting data for setting up velocityToOutput
    public void initOutputData() {
    for (int x = 0; x <= resolution; x++) {
    velocityOutput[x] = test((x * 200.0 / resolution) - 100.0);
    }
    }

    private double test(double speed) {
    double initDist = encoder.getDistance();
    motor.set(speed / 200);
    Timer.delay(1.0);
    double finalDist = encoder.getDistance();
    motor.set(0.0);
    double distance = finalDist - initDist;  //94 = scaling facto ticks-->inches
    DiscoUtils.debugPrintln((speed) + "\t-->\t" + distance);
    return distance;    //also velocity in inches/sec because sampling period was 1 sec
    }*/
    private double velocityToOutput(double velocity) {
        double ffterm = 0.0;
        if (velocity <= -100) {
            ffterm = -0.80;
        } else if (velocity <= -75) {
            ffterm = -0.60;
        } else if (velocity <= -40) {
            ffterm = -0.40;
        } else if (velocity <= -10) {
            ffterm = -0.20;
        } else if (velocity == 0.0) {
            ffterm = 0.0;
        } else if (velocity <= 8) {
            ffterm = 0.20;
        } else if (velocity <= 40) {
            ffterm = 0.40;
        } else if (velocity <= 75) {
            ffterm = 0.60;
        } else if (velocity <= 100) {
            ffterm = 0.80;
        }
        //DiscoUtils.debugPrintln("Feed-forward term: " + ffterm);
        return ffterm;
    }

    private double adjustVelocity(double goalVelocity, double oldDist, double oldTime) {
        
        double distDiff = (encoder.getDistance() - oldDist);
        double timeDiff = (Timer.getFPGATimestamp() - oldTime);

        velocity = distDiff / timeDiff;
        //DiscoUtils.debugPrintln("adjustVelocity velocity: " + velocity);
        
        double error = goalVelocity - velocity;
        //DiscoUtils.debugPrintln("error: " + error);

        if (Math.abs(iterm) < itermMax) {
            iterm += (timeDiff * error);
            //DiscoUtils.debugPrintln("iterm: " + iterm);
        }

        distTraveled += distDiff;

        double result = velocityToOutput(goalVelocity) + (ki * iterm);
        return result;
    }

    public double controller() {  //distance in inches
        if ((Timer.getFPGATimestamp() - oldTime) > 0.03) {
            output = adjustVelocity(goalVelocity, oldDist, oldTime);
            motor.set(output);
            oldTime = Timer.getFPGATimestamp();
            oldDist = encoder.getDistance();
            return output;
        }
        else {
            motor.set(output);
            return output;
        }
    }

    public double getOutput() {
        return output;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getGoalVelocity() {
        return goalVelocity;
    }

    public double setGoalVelocity(double newVelocity) {
        if (reversed) {
            goalVelocity = -newVelocity;
        } else {
            goalVelocity = newVelocity;
        }
        iterm = 0.0;
        oldTime = Timer.getFPGATimestamp() + 0.2;
        output = velocityToOutput(goalVelocity);
        return velocityToOutput(goalVelocity);
    }

    public void setGoalDistance(double newGoalDistance) {
        goalDistance = newGoalDistance;
        distTraveled = 0.0;
    }

    public void initialize() {
        oldDist = encoder.getDistance();
        oldTime = Timer.getFPGATimestamp();
        goalVelocity = 0.0;
        goalDistance = 0.0;
        distTraveled = 0.0;
        iterm = 0.0;
    }

    public void setReversed(boolean r) {
        reversed = r;
    }
}
