/*
 * MaxbotixSonar class for reading values from the Maxbotix Sonars.
 * Will have functions for running them in daisy chain mode which will require
 * a digital output.
 *
 * Remember that Maxbotix sensors get connected to Analog inputs not digital
 * inputs like other ultrasonic sensors.
 */
package disco.utils;

import com.sun.squawk.util.Arrays;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.parsing.ISensor;

/**
 *
 * @author JAG
 */
/**
 * Maxbotix sonar MB 1240
 */
public class MaxbotixSonar extends SensorBase implements PIDSource, ISensor {
    protected static final double k_CentimeterFactor=204.8;
    protected static final double k_InchesFactor = k_CentimeterFactor*0.393;
    protected static final double k_MillimetersFactor = 10*k_CentimeterFactor;

    /**
     * The units to return when PIDGet is called
     */
    public static class Unit {

        /**
         * The integer value representing this enumeration
         */
        public final int value;
        static final int kInches_val = 0;
        static final int kMillimeters_val = 1;
        /**
         * Use inches for PIDGet
         */
        public static final Unit kInches = new Unit(kInches_val);
        /**
         * Use millimeters for PIDGet
         */
        public static final Unit kMillimeter = new Unit(kMillimeters_val);

        private Unit(int value) {
            this.value = value;
        }
    }
    
    //private static final double CM_TO_IN = 0.3937;
    private AnalogChannel m_inputChannel = null;
    private Unit m_units;
    private double m_previousReading = -1;
    private int m_nextValue = 0;
    private int m_numberOfReadings = 10;
    private double m_readings[] = new double[m_numberOfReadings];
    
    public MaxbotixSonar(int inputSlot, int inputChannel, Unit units) {
        m_inputChannel = new AnalogChannel(inputSlot, inputChannel);
        m_units = units;
    }

    /**
     * Create an instance of the MaxbotixSonar Sensor using the default modules.
     * This is designed to support the Maxbotix-XL  ultrasonic sonar sensors. This
     * constructor is for all sensors that are not the first sensor in a daisy chain. This
     * constructor assumes the analog I/O channel is in the default analog module
     * @param inputChannel The analog input channel has the analog voltage of the distance.
     * @param units The units returned in either kInches or kMilliMeters
     */
    public MaxbotixSonar(final int inputChannel, Unit units) {
        m_inputChannel = new AnalogChannel(inputChannel);
        m_units = units;
    }

    /**
     * Create an instance of the MaxbotixSonar Sensor using the default modules.
     * This is designed to support the Maxbotix-XL  ultrasonic sonar sensors. This
     * constructor is for all sensors that are not the first sensor in a daisy chain. This
     * constructor assumes the analog I/O channel is in the default analog module
     * Defaults to using inches
     * @param inputChannel The analog input channel has the analog voltage of the distance.
     * @param units The units returned in either kInches or kMilliMeters
     */
    public MaxbotixSonar(final int inputChannel) {
        this(inputChannel, Unit.kInches);
    }

    /**
     * Create an instance of the MaxbotixSonar Sensor from a AnalogChannel for the input channel
     * Used when not the first in a daisy chain.
     * @param inputChannel The AnalogChannel object that times the return pulse to determine the range.
     * @param units The units returned in either kInches or kMilliMeters
     */
    public MaxbotixSonar(AnalogChannel inputChannel, Unit units) {
        if (inputChannel == null) {
            throw new NullPointerException("Null Channel Provided");
        }
        m_inputChannel = inputChannel;
        m_units = units;
    }

    /**
     * Create an instance of the MaxbotixSonar Sensor from a AnalogChannel for the input channel.
     * Used when not the first in a daisy chain.
     * Defaults to inches
     * @param inputChannel The AnalogChannel object that times the return pulse to determine the range.
     * @param units The units returned in either kInches or kMilliMeters
     */
    public MaxbotixSonar(AnalogChannel inputChannel) {
        this(inputChannel, Unit.kInches);
    }

    /**
     * Return the voltage reading after applying a small mode filter
     * Check the reading against the last reading to see if they are with in a
     * given a range before outputing it, if not output the previous reading.
     * Return the Voltage reading directly form the analog module
     */
    public double getVoltage() {
        double newReading = m_inputChannel.getVoltage();
        double goodReading = m_previousReading;
        if (m_previousReading == -1 || Math.abs(newReading - m_previousReading) < .5) {
            goodReading = newReading;
        }
        m_previousReading = newReading;
        return goodReading;
    }

    public double getMedianVoltage() {
        m_readings[m_nextValue] = m_inputChannel.getVoltage();

        if (m_nextValue < m_numberOfReadings - 1) {
            m_nextValue++;
        } else {
            m_nextValue = 0;
        }

        double sorted[] = m_readings;
        Arrays.sort(sorted);
        double median = median(sorted);
        return median;
    }

    public static double median(double[] m) {
        int middle = m.length / 2;  // subscript of middle element
        if (m.length % 2 == 1) {
            // Odd number of elements -- return the middle one.
            return m[middle];
        } else {
            // Even number -- return average of middle two
            // Must cast the numbers to double before dividing.
            return (m[middle - 1] + m[middle]) / 2.0;

        }
    }

    /**
     * Get the range in inches from the MaxbotixSonar sensor.
     * @return double Range in inches of the target returned from the MaxbotixSonarsensor. If there is
     * no valid value yet then return 0.
     */
    public double getRange() {
        double result=0;
        if(m_units==Unit.kInches) {
            return getVoltage() * k_InchesFactor;
        }
        if(m_units==Unit.kMillimeter){
            return getVoltage() *k_MillimetersFactor;
        }
        return getVoltage() * k_InchesFactor;
    }
    
    public double getMedianRange() {
        double result=0;
        if(m_units==Unit.kInches) {
            return getMedianVoltage() * k_InchesFactor;
        }
        if(m_units==Unit.kMillimeter){
            return getMedianVoltage()*k_MillimetersFactor;
        }
        return getMedianVoltage() * k_InchesFactor;
    }

    /**
     * Get the range in the current DistanceUnit for the PIDSource base object.
     *
     * @return The range in DistanceUnit
     */
    public double pidGet() {
        return getRange();
    }
}
