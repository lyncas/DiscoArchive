package DriveControllers;

import Sensors.DiscoEncoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author Nelson
 */
public class DistanceController implements PIDSource, PIDOutput {

    public static final double kDefaultPeriod = .1;
    protected double m_period = kDefaultPeriod;
    private PIDController distController;
    private double kDefaultMinSpeed = -0.5;
    private double kDefaultMaxSpeed = 0.5;
    private double output = 0.0;
    private DiscoEncoder encoder;

    public DistanceController(double kP, double kI, double kD, DiscoEncoder enc) {
        this(kP, kI, kD, enc, false);
    }

    public DistanceController(double kP, double kI, double kD, DiscoEncoder enc, boolean invert) {
        encoder = enc;
        encoder.setDistancePerPulse(5.235987755983e-02);    //6pi inches / 360 ticks
        encoder.init();
        distController = new PIDController(kP, kI, kD, this, this, kDefaultPeriod);//, invert);
        distController.disable();
        distController.setSetpoint(0.0);
        distController.setOutputRange(kDefaultMinSpeed, kDefaultMaxSpeed);
    }

    public void setDistance(double dist) {
        distController.setSetpoint(dist);
        distController.enable();
    }

    public void init() {
        encoder.init();
        distController.enable();
    }

    public double pidGet() {
        return encoder.getDistance();
    }

    public void pidWrite(double pidOut) {
        output = pidOut;
    }

    public void setOutputRange(double min, double max) {
        distController.setOutputRange(min, max);
    }

    public double getSetpoint() {
        return distController.getSetpoint();
    }

    public double getOutput() {
        return output;
    }

    public void setPID(double P, double I, double D) {
        distController.setPID(P, I, D);
    }

    public void enable() {
        if (!distController.isEnable()) {
            distController.enable();
        }
    }

    public void disable() {
        if (distController.isEnable()) {
            distController.disable();
        }
    }

    public double getError() {
        return distController.getError();
    }

    public double getP() {
        return distController.getP();
    }

    public double getI() {
        return distController.getI();
    }

    public double getD() {
        return distController.getD();
    }
}
