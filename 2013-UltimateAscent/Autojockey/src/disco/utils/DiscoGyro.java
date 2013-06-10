/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.utils;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import lejos.robotics.DirectionFinder;

/**
 *
 * @author sam
 */
public class DiscoGyro extends Gyro implements DirectionFinder {

    boolean reversed = true;
    double cartesianZeroOffset = 0;

    public DiscoGyro(int channel) {
        super(channel);
    }

    public DiscoGyro(int slot, int channel) {
        this(slot, channel, true);
    }

    public DiscoGyro(int slot, int channel, boolean reversed) {
        super(slot, channel);
        this.reversed = reversed;
    }

    public DiscoGyro(AnalogChannel channel) {
        super(channel);
    }

    public double getAngle() {
        return (reversed ? -1 : 1) * super.getAngle();
    }

    public void reset() {
        super.reset();
    }

    protected double normalize(double in) {
        /*
         * returns equivalent angle between -180 and +180
         */
        double a = in;
        while (a > 180) {
            a -= 360;
        }
        while (a < -180) {
            a += 360;
        }
        return a;
    }

    public float getDegreesCartesian() {
        return (float) ((getAngle() - cartesianZeroOffset) % 360);
    }

    public void startCalibration() {
        //do nothing
        stopCalibration();
    }

    public void stopCalibration() {
        //do nothing
    }

    public void resetCartesianZero() {
        cartesianZeroOffset = getAngle();
    }
}
