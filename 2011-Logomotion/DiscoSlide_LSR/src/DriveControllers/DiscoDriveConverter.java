/*
 * 
 */
package DriveControllers;

import Utils.DiscoUtils;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * This class is an object that is used to store a drive value that will be
 * integrated with other values to determine the final drive output. This allows
 * us to do sensor integration and also have more control of the final drive output.
 *
 * Also allows you to feed the output of one PIDController into the input of another
 * @author JAG
 */
public class DiscoDriveConverter implements PIDOutput, PIDSource {

    /**
     * Class lets you select which output will be controlled by the PIDloop
     * Most of the time will be speed but may be the others
     */
    public static class Output {

        /**
         * The integer value representing this enumeration
         */
        public final int value;
        static final int kSpeed_val = 0;
        static final int kDirection_val = 1;
        static final int kTwist_val = 2;
        /**
         * Use Speed for PIDGet and PIDWrite
         */
        public static final Output kSpeed = new Output(kSpeed_val);
        /**
         * Use Direction for PIDGet and PIDWrite
         */
        public static final Output kDirection = new Output(kDirection_val);
        /**
         * Use Twist for PIDGet and PIDWrite
         */
        public static final Output kTwist = new Output(kTwist_val);

        private Output(int value) {
            this.value = value;
        }
    }
    private double m_inputSpeed = 0.0;
    private double m_inputDirection = 0.0;
    private double m_inputTwist = 0.0;
    private double m_pidValue = 0.0;
    private double m_minOutput = -1.0;
    private double m_maxOutput = 1.0;
    private double m_minInput = -1.0;
    private double m_maxInput = 1.0;
    private Output m_output;

    /**
     * Takes in the default values and the value that should be changed and outputed by the PID control loop
     * @param inputSpeed
     * @param inputDirection
     * @param inputTwist
     * @param output either Output.kSpeed, Output.kDirection, or Output.kTwist
     */
    public DiscoDriveConverter(double inputSpeed, double inputDirection, double inputTwist, Output output) {
        m_inputSpeed = inputSpeed;
        m_inputDirection = inputDirection;
        m_inputTwist = inputTwist;
        m_output = output;
    }

    public DiscoDriveConverter(double inputSpeed, double inputDirection, double inputTwist, Output output,
            double minOutput, double maxOutput) {
        this(inputSpeed, inputDirection, inputTwist, output);
        m_minOutput = minOutput;
        m_maxOutput = maxOutput;
    }

    public DiscoDriveConverter(double inputSpeed, double inputDirection, double inputTwist, Output output,
            double minOutput, double maxOutput, double minInput, double maxInput) {
        this(inputSpeed, inputDirection, inputTwist, output, minOutput, maxOutput);
        m_minInput = minInput;
        m_maxInput = maxInput;
    }

    public void pidWrite(double input) {
        switch (m_output.value) {
            case Output.kSpeed_val:
                setSpeed(input);
                break;
            case Output.kDirection_val:
                setDirection(input);
                break;
            case Output.kTwist_val:
                setTwist(input);
                break;
            default:
                DiscoUtils.debugPrintln("Output Type not matched in DiscoDriveConverter");
        }
    }

    public double pidGet() {
        switch (m_output.value) {
            case Output.kSpeed_val:
                return getSpeed();
            case Output.kDirection_val:
                return getDirection();
            case Output.kTwist_val:
                return getTwist();
            default:
                return 0.0;
        }
    }

    private void scaleOutput() {
        double scalingFactor = 1.0;//(-m_minInput - ((m_maxInput - m_minInput) / 2)) * (m_maxOutput - m_minOutput);
        //DiscoUtils.debugPrintln("Scaling factor: " + scalingFactor);
        switch (m_output.value) {
            case Output.kSpeed_val:
                setSpeed(m_inputSpeed * scalingFactor);
                break;
            case Output.kDirection_val:
                setDirection(m_inputDirection * scalingFactor);
                break;
            case Output.kTwist_val:
                setTwist(m_inputTwist * scalingFactor);
                break;
            default:
                DiscoUtils.debugPrintln("Output Type not matched in DiscoDriveConverter");
        }
    }

    public double getSpeed() {
        scaleOutput();
        return m_inputSpeed;
    }

    public double getDirection() {
        scaleOutput();
        return m_inputDirection;
    }

    public double getTwist() {
        scaleOutput();
        return m_inputTwist;
    }

    public void setSpeed(double speed) {
        m_inputSpeed = speed;
    }

    public void setDirection(double direction) {
        m_inputDirection = direction;
    }

    public void setTwist(double twist) {
        m_inputTwist = twist;
    }

    public void setOutput(Output output) {
        m_output = output;
    }

    public void setMaxInput(double maxInput) {
        m_maxInput = maxInput;
    }

    public void setMinInput(double minInput) {
        m_minInput = minInput;
    }

    public void setMaxOutput(double maxOutput) {
        m_maxOutput = maxOutput;
    }

    public void setMinOutput(double minOutput) {
        m_minOutput = minOutput;
    }
}
