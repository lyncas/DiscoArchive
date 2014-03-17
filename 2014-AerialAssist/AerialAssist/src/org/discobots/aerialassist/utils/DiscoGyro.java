package org.discobots.aerialassist.utils;

import edu.wpi.first.wpilibj.Gyro;

/**
 * Extends gyro to allow us to reset the angle to what ever value we want.
 *
 * @author JAG
 */
public class DiscoGyro extends Gyro {

    private double m_resetAngleValue = 0;

    public DiscoGyro(int channel) {
        super(channel);
    }

    /**
     *
     * @return the value of the gyro plus our reset angle positions
     */
    public double getAngle() {
        return super.getAngle() + m_resetAngleValue;
    }

    public double getNormalizedAngle() {
        return normalize(getAngle());
    }

    public void setAngle(double angle) {
        m_resetAngleValue = angle;
        super.reset();
    }

    /**
     * Reset by setting th angle to zero and reseting the gyro accumulator
     */
    public void reset() {
        reset(0);
    }

    /**
     * Reset the gyro and set to what you want. Is just another name for
     * setAngle
     *
     * @param angle
     */
    public void reset(double angle) {
        setAngle(angle);
    }

    /**
     * Return the angle with our adjustments
     *
     * @return
     */
    public double PIDGet() {
        return getAngle();
    }

    public static double normalize(double angle) {
        System.out.println("Someone normalized the angle!");
        return Math.abs(angle % 360);
    }
}
