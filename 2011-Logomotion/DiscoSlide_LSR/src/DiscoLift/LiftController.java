package DiscoLift;

import DriveControllers.PositionController;
import Utils.DiscoUtils;
import discobot.HW;
import edu.wpi.first.wpilibj.Encoder.PIDSourceParameter;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import java.util.TimerTask;

/**
 *
 * @author JAG
 */
public class LiftController implements PIDOutput, PIDSource {

    public static final double kDefaultPeriod = .1;
    public static final double m_speedUpScaleFactor = 23;
    public static final double m_speedDownScaleFactor = 19;
    //What are the max speeds for the lift
    public static final double kLiftMaxSpeedDown = -.2;
    public static final double kLiftSpeedMaxUp = 1;
    //Heights to reset too for the limit switches
    private static final int kLiftUp = 637;
    private static final int kLiftMiddle = 295;
    private static final int kLiftDown = 0;
    //Heights of Each Peg
    public static final int kLiftD = 0;
    public static final int kLiftMidCircle = 293;
    public static final int kLiftMidSquare = 278;
    public static final int kLiftTopCircle = 593;
    public static final int kLiftTopSquare = 578;
    private double output = 0.0;
    protected java.util.Timer m_controlLoop;
    protected double m_period = kDefaultPeriod;
    private static PositionController positionController;

    private class LiftControllerTask extends TimerTask {

        private LiftController m_controller;

        public LiftControllerTask(LiftController controller) {
            if (controller == null) {
                throw new NullPointerException("Given LiftController was null");
            }
            m_controller = controller;
        }

        public void run() {
            m_controller.checkForLimits();
        }
    }

    public LiftController() {
        HW.liftEncoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
        HW.liftEncoder.start();
        positionController = new PositionController(0.0, 0.0, 0.0, this, this, kDefaultPeriod, false);
        setPID(0.03, 0.0, 0.0001, 0.01, 0.0, 0.01);
        positionController.disable();
        positionController.setSetpoint(0.0);
        positionController.setOutputRange(kLiftMaxSpeedDown, kLiftSpeedMaxUp);
        positionController.setInputRange(kLiftDown, kLiftUp);
        m_controlLoop = new java.util.Timer();
        m_controlLoop.scheduleAtFixedRate(new LiftControllerTask(this), 0L, (long) (m_period * 1000));
    }

    public double pidGet() {
        return getPosition();
    }

    public void pidWrite(double pidOut) {
        output = pidOut;
        //Ensure we are not driving it to far
        if ((output > .05 && isLiftUp()) || (output < .05 && isLiftDown())) {
            HW.liftMotor.set(0.0);

        } else {
            HW.liftMotor.set(output);
        }

    }

    public void enablePIDControl() {
        if (!positionController.isEnable()) {
            positionController.enable();
        }
    }

    public void disablePIDControl() {
        if (positionController.isEnable()) {
            positionController.disable();
        }
    }

    public void setOutputRange(double min, double max) {
        positionController.setOutputRange(min, max);
    }

    public double getSetpoint() {
        return positionController.getSetpoint();
    }

    public double getPosition() {
        return HW.liftEncoder.getRawPosition();
    }

    public double getOutput() {
        return output;
    }

    public void setPID(double UpP, double UpI, double UpD, double DownP, double DownI, double DownD) {
        positionController.setPID(UpP, UpI, UpD);
        positionController.setDownPID(DownP, DownI, DownD);
    }

    public void setPID(double P, double I, double D) {
        setPID(P, I, D, P, I, D);
    }

    private void checkForLimits() {
        if (getPosition() <= kLiftDown && !isLiftDown()) {
            resetPosition(kLiftDown + 2);
        } else if (getPosition() > kLiftUp && !isLiftUp()) {
            resetPosition(kLiftUp - 1);
        }

        if (isLiftDown()) {
            resetPosition(kLiftDown);
            //DiscoUtils.debugPrintln("LIFT DOWN");
        } else if (isLiftMiddle()) {
            resetPosition(kLiftMiddle);
            //DiscoUtils.debugPrintln("LIFT IN MIDDLE");
        } else if (isLiftUp()) {
            resetPosition(kLiftTopCircle);
            //DiscoUtils.debugPrintln("LIFT IS UP");
        }
    }

    public void resetPosition(double position) {
        HW.liftEncoder.setPosition(position);
    }

    public void setSetpoint(double position) {
        if (positionController.isEnable()) {
            if (position < kLiftDown) {
                position = kLiftDown;
            } else if (position > kLiftUp) {
                position = kLiftUp;
            } else {
                positionController.setSetpoint(position);
            }
        } else {
            DiscoUtils.errorPrintln("Position Control is not enabled for the Lift");
        }
    }

    public void setLiftSpeed(double speed) {
        if (positionController.isEnable()) {
            if (speed < 0) {
                positionController.setSetpoint(HW.liftEncoder.getRawPosition() + speed * m_speedDownScaleFactor);
            } else {
                positionController.setSetpoint(HW.liftEncoder.getRawPosition() + speed * m_speedUpScaleFactor);
            }
        } else {
            output = speed;
            if (speed < 0 && !isLiftDown()) {
                HW.liftMotor.set(output);
            } else if (speed > 0 && !isLiftUp()) {
                HW.liftMotor.set(output);
            } else {
                output = 0.0;
                HW.liftMotor.set(output);
            }

        }
    }

    /**
     * Called in TelopInit or AutonInit to make sure the lift is down before we start running
     * Should always be down during matches but during practice it's very helpful
     */
    public void downToSwitch() {
        if (!isLiftDown()) {
            this.disablePIDControl();
            double startTime = Timer.getFPGATimestamp();
            double newTime = Timer.getFPGATimestamp();
            DiscoUtils.debugPrintln("Dropping the Lift to Start Telop");

            while (!isLiftDown() && (newTime - startTime) < 4) {
                newTime = Timer.getFPGATimestamp();
                this.setLiftSpeed(-0.1);
            }

            if (!isLiftDown()){
                //We went longer than 4 seconds so disable PID Control
                this.disablePIDControl();
            } else {
                this.enablePIDControl();
            }
        }
        this.resetPosition(kLiftD);
    }

    public boolean isLiftDown() {
        return !HW.liftLimitInnerDown.get() && !HW.liftLimitMiddleDown.get();
    }

    public boolean isLiftMiddle() {
        return (!HW.liftLimitInnerUp.get() && !HW.liftLimitMiddleDown.get())
                || (!HW.liftLimitInnerDown.get() && !HW.liftLimitMiddleUp.get());
    }

    public boolean isLiftUp() {
        return !HW.liftLimitInnerUp.get() && !HW.liftLimitMiddleUp.get();
    }

    public double getLiftSpeed() {
        return HW.liftMotor.getSpeed();
    }

    public double getError() {
        return positionController.getError();
    }

    public double getP() {
        return positionController.getP();
    }

    public double getI() {
        return positionController.getI();
    }

    public double getD() {
        return positionController.getD();
    }
}
