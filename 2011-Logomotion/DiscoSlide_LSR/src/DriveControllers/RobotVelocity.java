package DriveControllers;

import Sensors.VelocityMatrices;
import edu.wpi.first.wpilibj.PIDController;

/**
 *
 * @author Nelson Chen
 */
public class RobotVelocity {

    private VelocityMatrices xVelocity;
    private VelocityMatrices yVelocity;
    private DiscoDriveConverter xVelocityOutput;
    private DiscoDriveConverter yVelocityOutput;
    private PIDController xVelocityController;
    private PIDController yVelocityController;

    public static RobotVelocity robotVelocity = new RobotVelocity();

    /**
     * singleton constructor
     * Initializes velocity matrices, DiscoDriveConverters, and PIDControllers
     * 2 objects of each type for each direction (x, y)
     */
    private RobotVelocity() {
        //xVelocity = new VelocityMatrices(VelocityMatrices.VelocityOutput.kXvelocity);
        //yVelocity = new VelocityMatrices(VelocityMatrices.VelocityOutput.kYvelocity);
        xVelocityOutput =
                new DiscoDriveConverter(0.0, 0.0, 0.0, DiscoDriveConverter.Output.kSpeed);
        yVelocityOutput =
                new DiscoDriveConverter(0.0, 0.0, 0.0, DiscoDriveConverter.Output.kSpeed);
        //xVelocityController = new PIDController(0.1, 0.0, 0.0, xVelocity, xVelocityOutput);
        //yVelocityController = new PIDController(0.1, 0.0, 0.0, yVelocity, yVelocityOutput);
    }

    /**
     * sets goal x-velocity for the x-velocity PIDController
     * @param xVel - new setpoint (goal velocity) for the x-velocity PIDController
     */
    public void setXvelocity(double xVel) {
        xVelocityController.setSetpoint(xVel);
    }

    /**
     * sets goal y-velocity for the y-velocity PIDController
     * @param yVel - new setpoint (goal velocity) for the y-velocity PIDController
     */
    public void setYvelocity(double yVel) {
        yVelocityController.setSetpoint(yVel);
    }

    /**
     * sets x-velocity PIDController constants/gains
     * @param kp - new Proportional gain
     * @param ki - new Integral gain
     * @param kd - new Derivative gain
     */
    public void setXvelocityPID(double kp, double ki, double kd) {
        xVelocityController.setPID(kp, ki, kd);
    }

    /**
     * sets y-velocity PIDController constants/gains
     * @param kp - new Proportional gain
     * @param ki - new Integral gain
     * @param kd - new Derivative gain
     */
    public void setYvelocityPID(double kp, double ki, double kd) {
        yVelocityController.setPID(kp, ki, kd);
    }

    /**
     * enables x-velocity PIDController
     */
    public void enableX() {
        xVelocityController.enable();
    }

    /**
     * enables y-velocity PIDController
     */
    public void enableY() {
        yVelocityController.enable();
    }

    /**
     * disables x-velocity PIDController
     */
    public void disableX() {
        xVelocityController.disable();
    }

    /**
     * disables y-velocity PIDController
     */
    public void disableY() {
        yVelocityController.disable();
    }

    /**
     * @return returns x-velocity according to output of x-velocity PIDController
     */
    public double getXvelocity() {
        return xVelocityOutput.getSpeed();
    }

    /**
     * 
     * @return returns y-velocity according to output of y-velocity PIDController
     */
    public double getYvelocity() {
        return yVelocityOutput.getSpeed();
    }
}
