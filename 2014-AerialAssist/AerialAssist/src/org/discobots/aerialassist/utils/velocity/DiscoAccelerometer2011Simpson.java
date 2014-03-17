package org.discobots.aerialassist.utils.velocity;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.ADXL345_I2C.DataFormat_Range;
import java.util.TimerTask;

public class DiscoAccelerometer2011Simpson extends ADXL345_I2C {

    public static final double kDefaultPeriod = .1;
    public static final double kMetersToFt = 3.2808399;
    protected java.util.Timer m_controlLoop;
    protected double m_period = kDefaultPeriod;
    protected AllAxes m_axes = new AllAxes();
    protected double m_xVelocity = 0.0;
    protected double m_yVelocity = 0.0;
    protected double m_intialXAccel = 0.0;
    protected double m_intialYAccel = 0.0;
    protected double m_sumIntialXAcc = 0.0;
    protected double m_sumIntialYAcc = 0.0;
    protected int m_numberOfReadings = 0;
    protected final int kIntegrationSamples = 3; //Simpson's Rule
    protected AllAxes[] m_lastReadings = new AllAxes[kIntegrationSamples];
    protected int m_currentSample = 0;

    private class DiscoAccelerometerTask extends TimerTask {

        private DiscoAccelerometer2011Simpson m_accelerometer;

        public DiscoAccelerometerTask(DiscoAccelerometer2011Simpson accelerometer) {
            if (accelerometer == null) {
                throw new NullPointerException("Given accelerometer was null");
            }
            m_accelerometer = accelerometer;
        }

        public void run() {
            m_accelerometer.integrate();
        }
    }

    public DiscoAccelerometer2011Simpson() {
        super(1, DataFormat_Range.k4G);

        m_controlLoop = new java.util.Timer();
        m_controlLoop.scheduleAtFixedRate(new DiscoAccelerometerTask(this), 0L, (long) (m_period * 1000));
    }

    /**
     * Put this in disabled Continuous, it will average the values of the
     * accelerometer while at rest
     */
    public void calculateIntialAccel() {
        m_axes = getAccelerations();
        double x = m_axes.XAxis;
        double y = m_axes.YAxis;
        if (x + y < .3) {
            m_sumIntialXAcc += x;
            m_sumIntialYAcc += y;
            m_numberOfReadings++;
            m_intialXAccel = m_sumIntialXAcc / m_numberOfReadings;
            m_intialYAccel = m_sumIntialYAcc / m_numberOfReadings;
        }
    }

    public void resetVelocities() {
        m_xVelocity = 0.0;
        m_yVelocity = 0.0;
    }

    //Integrate using Simpson's rule. Subtract 1 m_inital(X|Y)accel per reading
    private synchronized void integrate() {
        m_lastReadings[m_currentSample] = getAccelerations();
        m_currentSample++;
        if (m_currentSample == kIntegrationSamples) {
            m_xVelocity += (m_lastReadings[0].XAxis + 4.0 * m_lastReadings[1].XAxis + m_lastReadings[2].XAxis - 6.0 * m_intialXAccel) * m_period / 3.0;
            m_yVelocity += (m_lastReadings[0].YAxis + 4.0 * m_lastReadings[1].YAxis + m_lastReadings[2].YAxis - 6.0 * m_intialYAccel) * m_period / 3.0;
            m_currentSample = 0;
        }
    }

    //CORRECT G TO M/S^2 FIRST?
    public synchronized double getXVelocity() {
        return m_xVelocity * kMetersToFt;
    }

    public synchronized double getYVelocity() {
        return m_yVelocity * kMetersToFt;
    }
}
