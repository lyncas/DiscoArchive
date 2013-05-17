/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors;

import discobot.HW;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.ADXL345_I2C.DataFormat_Range;
import java.util.TimerTask;

/**
 *
 * @author JAG
 */
public class DiscoAccelerometer extends ADXL345_I2C {

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

    private class DiscoAccelerometerTask extends TimerTask {

        private DiscoAccelerometer m_accelerometer;

        public DiscoAccelerometerTask(DiscoAccelerometer accelerometer) {
            if (accelerometer == null) {
                throw new NullPointerException("Given accelerometer was null");
            }
            m_accelerometer = accelerometer;
        }

        public void run() {
            m_accelerometer.integrate();
        }
    }

    public DiscoAccelerometer() {
        super(HW.kDigitalModuleSlot, DataFormat_Range.k4G);

        m_controlLoop = new java.util.Timer();
        m_controlLoop.scheduleAtFixedRate(new DiscoAccelerometerTask(this), 0L, (long) (m_period * 1000));
    }

    /**
     * Put this in disabled Continuous, it will average
     * the values of the accelerometer while at rest
     */
    public void calculateIntialAccel() {
        m_axes = getAccelerations();
        double x = m_axes.XAxis;
        double y = m_axes.YAxis;
        if (x + y < .3){
            m_sumIntialXAcc += x;
            m_sumIntialYAcc += y;
            m_numberOfReadings++;
            m_intialXAccel = m_sumIntialXAcc/m_numberOfReadings;
            m_intialYAccel = m_sumIntialYAcc/m_numberOfReadings;
        }
    }

    public void resetVelocities() {
        m_xVelocity = 0.0;
        m_yVelocity = 0.0;
    }

    private synchronized void integrate() {
        m_axes = getAccelerations();
        m_xVelocity += (m_axes.XAxis - m_intialXAccel) * m_period;
        m_yVelocity += (m_axes.YAxis - m_intialYAccel) * m_period;
    }

    public synchronized double getXVelocity() {
        return m_xVelocity * kMetersToFt;
    }

    public synchronized double getYVelocity() {
        return m_yVelocity * kMetersToFt;
    }
}
