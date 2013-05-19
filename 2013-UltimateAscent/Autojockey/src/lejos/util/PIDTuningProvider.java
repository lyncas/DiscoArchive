package lejos.util;

/**
 * An interface used for creating a PID tuning provider through <code>NXTDataLogger</code> passthrough messaging 
 * via a <code>PIDTuner</code> instance.
 * <p>
 * The methods are based on what <code>lejos.util.PIDController</code> implements and what fields the NXT Charting 
 * Logger PID tuning 
 * <code>lejos.pc.charting.PanelPIDTune</code> displays. Your implementation may differ so code
 * accordingly.
 * <p>
 * Example showing how to use <code>PIDTuner</code> with a <code>PIDTuningProvider</code> 
 * implementation that you provide: <pre>
 *    NXTDataLogger dlog = new NXTDataLogger();
 *    NXTConnection conn = Bluetooth.waitForConnection(5000, NXTConnection.PACKET);
 *    try {
 *        dlog.startRealtimeLog(conn);
 *    } catch (IOException e) {
 *        // Do nothing
 *    }
 *       
 *    // Test passthrough message management and PID tuning framework
 *    LogMessageManager lmm = LogMessageManager.getLogMessageManager(dlog);
 *    PIDTuningProvider myPIDTuner = new &lt;your class that implements PIDTuningProvider&gt;();
 *    PIDTuner pidTuner = new PIDTuner(myPIDTuner, lmm);
 *    pidTuner.setDisplayName("Test of my PID tuner implementation!");
 *    ...
 * </pre>
 * 
 * @author Kirk P. Thompson
 * @see PIDTuner
 * @see PIDController
 * @see NXTDataLogger
 */
public interface PIDTuningProvider {

	/**
	 * Get the Kp value from the PID controller implementation.
	 * @return The current Kp value
	 */
	public float getKp();

	/**
	 * Set the Kp value for the PID controller implementation.
	 * @param kp The Kp value to set
	 */
	public void setKp(float kp);

	/**
	 * Get the Ki value from the PID controller implementation.
	 * @return The current Ki value
	 */
	public float getKi();

	/**
	 * Set the Ki value for the PID controller implementation.
	 * @param ki The Ki value to set
	 */
	public void setKi(float ki);

	/**
	 * Get the Kd value from the PID controller implementation.
	 * @return The current Kd value
	 */
	public float getKd();

	/**
	 * Set the Kd value for the PID controller implementation.
	 * @param kd The Kd value to set
	 */
	public void setKd(float kd);

	/**
	 * Get the ramping exponent value from the PID controller implementation.
	 * @return The current ramping exponent value
	 */
	public float getRampExponent();

	/**
	 * Set the ramping exponent value for the PID controller implementation.
	 * @param rampExponent The rampExponent value to set
	 */
	public void setRampExponent(float rampExponent);

	/**
	 * Get the ramping on-trigger threshold value from the PID controller implementation.
	 * @return The current ramping on-trigger threshold value
	 */
	public float getRampTriggerThreshold();

	/**
	 * Set the ramping on-trigger threshold value for the PID controller implementation.
	 * @param rampTriggerThreshold The ramping on-trigger threshold value to set
	 */
	public void setRampTriggerThreshold(float rampTriggerThreshold);

	/**
	 * Get the Manipulated Variable (MV) deadband value from the PID controller implementation.
	 * @return The current deadband value
	 */
	public float getMVDeadband();

	/**
	 * Set the Manipulated Variable (MV) deadband value for the PID controller implementation.
	 * @param mVDeadband The deadband value to set
	 */
	public void setMVDeadband(float mVDeadband);

	/**
	 * Get the Manipulated Variable (MV) high limit threshold value from the PID controller implementation.
	 * @return The current high limit threshold value
	 */
	public float getMVHighLimit();

	/**
	 * Set the Manipulated Variable (MV) high limit threshold value for the PID controller implementation.
	 * @param mVHighLimit The high limit threshold value to set
	 */
	public void setMVHighLimit(float mVHighLimit);

	/**
	 * Get the Manipulated Variable (MV) low limit threshold value from the PID controller implementation.
	 * @return The current low limit threshold value
	 */
	public float getMVLowLimit();

	/**
	 * Set the Manipulated Variable (MV) low limit threshold value for the PID controller implementation.
	 * @param mVLowLimit The low limit threshold value to set
	 */
	public void setMVLowLimit(float mVLowLimit);

	/**
	 * Get the integral wind-up low limit threshold value from the PID controller implementation.
	 * @return The current low limit threshold value
	 */
	public float getIntegralWindupLowLimit();

	/**
	 * Set the integral wind-up low limit threshold value for the PID controller implementation.
	 * @param integralWindupLowLimit The low limit threshold value to set
	 */
	public void setIntegralWindupLowLimit(float integralWindupLowLimit);

	/**
	 * Get the integral wind-up high limit threshold value from the PID controller implementation.
	 * @return The current high limit threshold value
	 */
	public float getIntegralWindupHighLimit();

	/**
	 * Set the integral wind-up high limit threshold value for the PID controller implementation.
	 * @param integralWindupHighLimit The high limit threshold value to set
	 */
	public void setIntegralWindupHighLimit(float integralWindupHighLimit);

	/**
	 * Get the Setpoint (SP) value from the PID controller implementation.
	 * @return The current SP value 
	 */
	public float getSP();

	/**
	 * Set the Setpoint (SP) value for the PID controller implementation.
	 * @param sP The SP value to set
	 */
	public void setSP(float sP);

	/**
	 * Get the target timing loop delay value (milliseconds) from the PID controller implementation.
	 * @return The current delay value
	 */
	public int getDelay();

	/**
	 * Set the target timing loop delay value for the PID controller implementation.
	 * @param delay The delay value to set in milliseconds
	 */
	public void setDelay(int delay);

	/**
	 * Get if the integral accumulation from the PID controller implementation is frozen.
	 * @return <code>true</code> if the the integral accumulation is currently frozen
	 */
	public boolean isIntegralFrozen();

	/**
	 * Set the integral accumulation status for the PID controller implementation.
	 * @param integralFrozen <code>true</code> to freeze integral accumulation
	 */
	public void setIntegralFrozen(boolean integralFrozen);

}