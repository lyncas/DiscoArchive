package lejos.disco;

import java.lejoslang.Math;
import disco.HW;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import lejos.util.Delay;

/**
 * FRC drivetrain side (left or right) abstraction.
 * speeds and degrees/sec are measured at motor output end.
 * @author Roger Glassey/Andy Shaw
 */
public class RegulatedDrivetrain implements RegulatedMotor
{

    protected static final int NO_LIMIT = 0x7fffffff;
    protected final Regulator reg;
    //protected TachoMotorPort tachoPort;
    protected boolean stalled = false;
    protected RegulatedMotorListener listener = null;
    protected float speed = 1200;//supposed to correspond to 3.5 feet/sec
    protected int acceleration = 800;//supposed to correspond to acceleration to 7 feet/sec in 3 sec
    protected int limitAngle = 0;
    protected int stallLimit = 50;
    protected int stallTime = 1000;
    protected static final Controller cont = new Controller();

    protected Victor motor1;
    protected double motor1_multiplier=1;
    protected Victor motor2;
    protected double motor2_multiplier=1;
    protected Encoder enc;

    static {
        // Start the single controller thread
        cont.setPriority(Thread.MAX_PRIORITY);
        //cont.setDaemon(true);
        cont.start();
    }

    /**
     * Use this constructor to assign a variable of type motor connected to a particular port.
     * @param port  to which this motor is connected
     */
//    public RegulatedDrivetrain(TachoMotorPort port)
//    {
//        tachoPort = port;
//        port.setPWMMode(TachoMotorPort.PWM_BRAKE);
//        reg = new Regulator();
//    }

    public RegulatedDrivetrain(Victor motor1, Victor motor2, boolean motor1Reversed, boolean motor2Reversed, Encoder enc){
	this.motor1=motor1;
	this.motor1_multiplier= motor1Reversed ? -1:1;
	this.motor2=motor2;
	this.motor2_multiplier= motor2Reversed ? -1:1;
	this.enc=enc;
	enc.start();
	reg = new Regulator();
    }

    /**
     * Removes this motor from the motor regulation system. After this call
     * the motor will be in float mode and will have stopped. Note calling any
     * of the high level move operations (forward, rotate etc.), will
     * automatically enable regulation.
     * @return true iff regulation has been suspended.
     */
    public boolean suspendRegulation()
    {
        // Putting the motor into float mode disables regulation. note
        // that we wait for the operation to complete.
        reg.newMove(0, acceleration, NO_LIMIT, false, true);
        // Now wait for the motor to become inactive
        while (reg.active)
            Delay.msDelay(1);
        return true;
    }

    /**
     * Update the internal state of the motor.
     * @param velocity
     * @param hold
     * @param stalled
     */
    void updateState(int velocity, boolean hold, boolean stalled)
    {
        synchronized (reg)
        {
            this.stalled = stalled;
            if (listener != null)
            {
                if (velocity == 0)
                    listener.rotationStopped(this, getTachoCount(), stalled, System.currentTimeMillis());
                else
                    listener.rotationStarted(this, getTachoCount(), false, System.currentTimeMillis());
            }
        }
    }

    /**
     * @return the current tachometer count.
     * Returns the degrees the wheels have turned
     * @see lejos.robotics.RegulatedMotor#getTachoCount()
     */
    public int getTachoCount()
    {
        return (int) (enc.get()/HW.encoderTicksPerRev*360);
    }

    /**
     * Returns the current position that the motor regulator is trying to
     * maintain. Normally this will be the actual position of the motor and will
     * be the same as the value returned by getTachoCount(). However in some
     * circumstances (activeMotors that are in the process of stalling, or activeMotors
     * that have been forced out of position), the two values may differ. Note
     * this value is not valid if regulation has been terminated.
     * @return the current position calculated by the regulator.
     */
    public int getPosition()
    {
        return Math.round(reg.curCnt);
    }

    /**
     * @see lejos.nxt.BasicMotor#forward()
     */
    public void forward()
    {
        reg.newMove(speed, acceleration, +NO_LIMIT, true, false);
    }

    /**
     * @see lejos.nxt.BasicMotor#backward()
     */
    public void backward()
    {
        reg.newMove(speed, acceleration, -NO_LIMIT, true, false);
    }

    /**
     * Set the motor into float mode. This will stop the motor without braking
     * and the position of the motor will not be maintained.
     */
    public void flt()
    {
        reg.newMove(0, acceleration, NO_LIMIT, false, true);
    }

    /**
     * Set the motor into float mode. This will stop the motor without braking
     * and the position of the motor will not be maintained.
     * @param immediateReturn If true do not wait for the motor to actually stop
     */
    public void flt(boolean immediateReturn)
    {
        reg.newMove(0, acceleration, NO_LIMIT, false, !immediateReturn);
    }

    /**
     * Causes motor to stop, pretty much
     * instantaneously. In other words, the
     * motor doesn't just stop; it will resist
     * any further motion.
     * Cancels any rotate() orders in progress
     */
    public void stop()
    {
        reg.newMove(0, acceleration, NO_LIMIT, true, true);
    }

    /**
     * Causes motor to stop, pretty much
     * instantaneously. In other words, the
     * motor doesn't just stop; it will resist
     * any further motion.
     * Cancels any rotate() orders in progress
     * @param immediateReturn if true do not wait for the motor to actually stop
     */
    public void stop(boolean immediateReturn)
    {
        reg.newMove(0, acceleration, NO_LIMIT, true, !immediateReturn);
    }

    /**
     * This method returns <b>true </b> if the motor is attempting to rotate.
     * The return value may not correspond to the actual motor movement.<br>
     * For example,  If the motor is stalled, isMoving()  will return <b> true. </b><br>
     * After flt() is called, this method will return  <b>false</b> even though the motor
     * axle may continue to rotate by inertia.
     * If the motor is stalled, isMoving()  will return <b> true. </b> . A stall can
     * be detected  by calling {@link #isStalled()};
     * @return true iff the motor is attempting to rotate.<br>
     */
    public boolean isMoving()
    {
        return reg.moving;
    }

    /**
     * Wait until the current movement operation is complete (this can include
     * the motor stalling).
     */
    public void waitComplete()
    {
        synchronized(reg)
        {
            while(reg.moving)
                try {reg.wait();} catch(InterruptedException e){}
        }
    }

    public void rotateTo(int limitAngle, boolean immediateReturn)
    {
        reg.newMove(speed, acceleration, limitAngle, true, !immediateReturn);
    }

    /**
     * Sets desired motor speed , in degrees per second;
     * @param speed value in degrees/sec
     */
    public void setSpeed(int speed)
    {
        this.speed = Math.abs(speed);
        reg.adjustSpeed(this.speed);
    }

    /**
     * Sets desired motor speed , in degrees per second;
     * @param speed value in degrees/sec
     */
    public void setSpeed(float speed)
    {
        this.speed = Math.abs(speed);
        reg.adjustSpeed(this.speed);
    }

    /**
     * sets the acceleration rate of this motor in degrees/sec/sec <br>
     * The default value is 800; Smaller values will make speeding up or stopping
     * at the end of a rotate() task smoother;
     * @param acceleration
     */
    public void setAcceleration(int acceleration)
    {
        this.acceleration = Math.abs(acceleration);
        reg.adjustAcceleration(this.acceleration);
    }

    /**
     * returns acceleration in degrees/second/second
     * @return the value of acceleration
     */
    public int getAcceleration()
    {
        return acceleration;
    }

    /**
     * Return the angle that this Motor is rotating to.
     * @return angle in degrees
     */
    public int getLimitAngle()
    {
        return limitAngle; // set in reg
    }

    /**
     * Reset the tachometer associated with this motor. Note calling this method
     * will cause any current move operation to be halted.
     * PLEASE DON'T CALL THIS. It's not really synchronized with the drivetrain.
     */
    public void resetTachoCount()
    {
        synchronized(reg)
        {
            // Make sure we are stopped!
            reg.newMove(0, acceleration, NO_LIMIT, true, true);
	    enc.reset();
            reg.reset();
        }
    }

    /**
     * Add a motor listener. Move operations will be reported to this object.
     * @param listener
     */
    public void addListener(RegulatedMotorListener listener)
    {
        this.listener = listener;
    }

    public RegulatedMotorListener removeListener() {
		RegulatedMotorListener old = this.listener;
		this.listener = null;
		return old;
	}

    /**
     * Rotate by the request number of degrees.
     * @param angle number of degrees to rotate relative to the current position
     * @param immediateReturn if true do not wait for the move to complete
     */
    public void rotate(int angle, boolean immediateReturn)
    {
        synchronized(reg)
        {
            rotateTo(Math.round(reg.curCnt) + angle, immediateReturn);
        }
    }

    /**
     * Rotate by the requested number of degrees. Wait for the move to complete.
     * @param angle
     */
    public void rotate(int angle)
    {
        rotate(angle, false);
    }

    /**
     * Rotate to the target angle. Do not return until the move is complete.
     * @param limitAngle Angle to rotate to.
     */
    public void rotateTo(int limitAngle)
    {
        rotateTo(limitAngle, false);
    }

    /**
     * Return the current target speed.
     * @return the current target speed.
     */
    public int getSpeed()
    {
        return Math.round(speed);
    }

    /**
     * Return true if the motor is currently stalled.
     * @return true if the motor is stalled, else false
     */
    public boolean isStalled()
    {
        return stalled;
    }

    /**
     * Set the parameters for detecting a stalled motor. A motor will be recognised
     * as stalled if the movement error (the amount the motor lags the regulated
     * position) is greater than error for a period longer than time.
     * @param error The error threshold
     * @param time The time that the error threshold needs to be exceeded for.
     */
    public void setStallThreshold(int error, int time)
    {
        this.stallLimit = error;
        this.stallTime = time/Controller.UPDATE_PERIOD;
    }
    /**
     * Return the current velocity.
     * @return current velocity in degrees/s
     */
    public int getRotationSpeed()
    {
        return Math.round(reg.curVelocity);
    }

    protected void setMotors(double power){
	motor1.set(power*motor1_multiplier);
	motor2.set(power*motor2_multiplier);
    }

    public double getRawPWM(){
	return motor1.get();
    }


    /**
     * Inner class to regulate velocity; also stop motor at desired rotation angle.
     * This class uses a very simple movement model based on simple linear
     * acceleration. This model is used to generate ideal target positions which
     * are then used to generate error terms between the actual and target position
     * this error term is then used to drive a PID style motor controller to
     * regulate the power supplied to the motor.
     *
     * If new command are issued while a move is in progress, the new command
     * is blended with the current one to provide smooth movement.
     *
     * If the requested speed is not possible then the controller will simply
     * drop move cycles until the motor catches up with the ideal position. If
     * too many consecutive dropped moves are required then the motor is viewed
     * to have stalled and the move is terminated.
     *
     * Once the motor stops, the final position is held using the same PID control
     * mechanism (with slightly different parameters), as that used for movement.
     **/
    protected class Regulator
    {
        // PID constants for move and for hold
        // Old values
        //static final float MOVE_P = 4f;
        //static final float MOVE_I = 0.04f;
        //static final float MOVE_D = 32f;
        // New values
        //static final float MOVE_P = 7f;
	//TODO: FIND PARAMETERS IF NEEDED SMD 20130511
        static final float MOVE_P = 6f;
        static final float MOVE_I = 0.04f;
        static final float MOVE_D = 22f;
        static final float HOLD_P = 2f;
        static final float HOLD_I = 0.04f;
        static final float HOLD_D = 8f;
        float basePower = 0; //used to calculate power
        float err1 = 0; // used in smoothing
        float err2 = 0; // used in smoothing
        float curVelocity = 0;
        float baseVelocity = 0;
        float baseCnt = 0;
        float curCnt = 0;
        float curAcc = 0;
        float curStopAcc = 0;
        float curTargetVelocity = 0;
        int curLimit = NO_LIMIT;
        boolean curHold = true;
        float accCnt = 0;
        long baseTime = 0;
        long now = 0;
        long accTime = 0;
        boolean moving = false;
        boolean pending = false;
        boolean checkLimit = false;
        float newSpeed = 0;
        int newAcceleration = 0;
        int newLimit = 0;
        boolean newHold = true;
        int tachoCnt;
        public int power;
        int mode;
        boolean active = false;
        int stallCnt = 0;

        /**
         * Reset the tachometer readings
         */
        synchronized void reset()
        {
            curCnt = tachoCnt = getTachoCount();
            now = System.currentTimeMillis();
        }

        /**
         * Helper method. Start a sub move operation. A sub move consists
         * of acceleration/deceleration to a set velocity and then holding that
         * velocity up to an optional limit point. If a limit point is set this
         * method will be called again to initiate a controlled deceleration
         * to that point
         * @param speed
         * @param acceleration
         * @param limit
         * @param hold
         */
        synchronized private void startSubMove(float speed, float acceleration, int limit, boolean hold)
        {
            float absAcc = Math.abs(acceleration);
            checkLimit = Math.abs(limit) != NO_LIMIT;
            baseTime = now;
            curTargetVelocity = (limit - curCnt >= 0 ? speed : -speed);
            curAcc = curTargetVelocity - curVelocity >= 0 ? absAcc : -absAcc;
            curStopAcc = curTargetVelocity >= 0 ? absAcc : -absAcc;
            accTime = Math.round(((curTargetVelocity - curVelocity) / curAcc) * 1000);
            accCnt = (curVelocity + curTargetVelocity) * accTime / (2 * 1000);
            baseCnt = curCnt;
            baseVelocity = curVelocity;
            curHold = hold;
            curLimit = limit;
            limitAngle = curLimit; // limitAngle is in outer. KPT 5/13/2011 06:42
            moving = curTargetVelocity != 0 || baseVelocity != 0;
        }

        /**
         * Helper method, if move is currently active wait for it to be
         * completed
         */
        private void waitStop()
        {
            if (moving)
                try
                {
                    wait();
                } catch (Exception e)
                {
                }
        }

        /**
         * Initiate a new move and optionally wait for it to complete.
         * If some other move is currently executing then ensure that this move
         * is terminated correctly and then start the new move operation.
         * @param speed
         * @param acceleration
         * @param limit
         * @param hold
         * @param waitComplete
         */
        synchronized public void newMove(float speed, int acceleration, int limit, boolean hold, boolean waitComplete)
        {
            if (!active)
            {
                cont.addMotor(RegulatedDrivetrain.this);
                active = true;
            }
            // ditch any existing pending command
            pending = false;
            // Stop moves always happen now
            if (speed == 0)
                startSubMove(0, acceleration, NO_LIMIT, hold);
            else if (!moving)
            {
                // not moving so we start a new move
                startSubMove(speed, acceleration, limit, hold);
                updateState(Math.round(curTargetVelocity), hold, false);
            }
            else
            {
                // we already have a move in progress can we modify it to match
                // the new request? We must ensure that the new move is in the
                // same direction and that any stop will not exceed the current
                // acceleration request.
                float moveLen = limit - curCnt;
                float acc = (curVelocity*curVelocity)/(2*(moveLen));
                if (moveLen*curVelocity >= 0 && Math.abs(acc) <= acceleration)
                    startSubMove(speed, acceleration, limit, hold);
                else
                {
                    // Save the requested move
                    newSpeed = speed;
                    newAcceleration = acceleration;
                    newLimit = limit;
                    newHold = hold;
                    pending = true;
                    // stop the current move
                    startSubMove(0, acceleration, NO_LIMIT, true);
                    // If we need to wait for the existing command to end
                    if (waitComplete)
                        waitStop();
                }
            }
            if (waitComplete)
                waitStop();
        }

        /**
         * The target speed has been changed. Reflect this change in the
         * regulator.
         * @param newSpeed new target speed.
         */
        synchronized void adjustSpeed(float newSpeed)
        {
            if (curTargetVelocity != 0)
            {
                startSubMove(newSpeed, curAcc, curLimit, curHold);
            }
            if (pending)
                this.newSpeed = newSpeed;
        }

        /**
         * The target acceleration has been changed. Updated the regulator.
         * @param newAcc
         */
        synchronized void adjustAcceleration(int newAcc)
        {
            if (curTargetVelocity != 0)
            {
                startSubMove(Math.abs(curTargetVelocity), newAcc, curLimit, curHold);
            }
            if (pending)
                newAcceleration = newAcc;
        }

        /**
         * The move has completed either by the motor stopping or by it stalling
         * @param stalled
         */
        synchronized private void endMove(boolean stalled)
        {
            moving = pending;
            updateState(0, curHold, stalled);
            if (stalled)
            {
                // stalled try and maintain current position
                reset();
                curVelocity = 0;
                stallCnt = 0;
                startSubMove(0, 0, NO_LIMIT, curHold);
            }
            // if we have a new move, go start it
            if (pending)
            {
                pending = false;
                startSubMove(newSpeed, newAcceleration, newLimit, newHold);
                updateState(Math.round(curTargetVelocity), curHold, false);
            }
            notifyAll();
        }

        /**
         * Monitors time and tachoCount to regulate velocity and stop motor rotation at limit angle
         */
        synchronized void regulateMotor(long delta)
        {
            float error;
            now += delta;
            long elapsed = now - baseTime;
            if (moving)
            {
                if (elapsed < accTime)
                {
                    // We are still acclerating, calculate new position
                    curVelocity = baseVelocity + curAcc * elapsed / (1000);
                    curCnt = baseCnt + (baseVelocity + curVelocity) * elapsed / (2 * 1000);
                    error = curCnt - tachoCnt;
                } else
                {
                    // no longer acclerating, calculate new position
                    curVelocity = curTargetVelocity;
                    curCnt = baseCnt + accCnt + curVelocity * (elapsed - accTime) / 1000;
                    error = curCnt - tachoCnt;
                    // Check to see if the move is complete
                    if (curTargetVelocity == 0 && (pending || (Math.abs(error) < 2 && elapsed > accTime + 100) || elapsed > accTime + 500))
                    {
                        endMove(false);
                    }
                }
                // check for stall
                if (Math.abs(error) > stallLimit)
                {
                    baseTime += delta;
                    if (stallCnt++ > stallTime) endMove(true);
                }
                else
                {
                    stallCnt /= 2;
                }
                calcPower(error, MOVE_P, MOVE_I, MOVE_D);
                // If we have a move limit, check for time to start the deceleration stage
                if (checkLimit)
                {
                    float acc = (curVelocity*curVelocity)/(2*(curLimit - curCnt));
                    if (curStopAcc/acc < 1.0)
                        startSubMove(0, acc, NO_LIMIT, curHold);
                }
            } else if (curHold)
            {
                // not moving, hold position
                error = curCnt - tachoCnt;
                calcPower(error, HOLD_P, HOLD_I, HOLD_D);
            }
            else
            {
                // Allow the motor to move freely
                curCnt = tachoCnt;
                power = 0;
                //mode = TachoMotorPort.FLOAT;
                active = false;
                cont.removeMotor(RegulatedDrivetrain.this);
            }
        }// end run

        /**
         * helper method for velocity regulation.
         * calculates power from error using double smoothing and PID like
         * control
         * @param error
         */
        private void calcPower(float error, float P, float I, float D)
        {
            // use smoothing to reduce the noise in frequent tacho count readings
            // New values
            err1 = 0.375f * err1 + 0.625f * error;  // fast smoothing
            err2 = 0.75f * err2 + 0.25f * error; // slow smoothing
            // Original values
            //err1 = 0.5f * err1 + 0.5f * error;  // fast smoothing
            //err2 = 0.8f * err2 + 0.2f * error; // slow smoothing
            float newPower = basePower + P * err1 + D * (err1 - err2);
            basePower = basePower + I * (newPower - basePower);
            if (basePower > 100)
                basePower = 100;
            else if (basePower < -100)
                basePower = -100;
            power = (newPower > 100 ? 100 : newPower < -100 ? -100 : Math.round(newPower));

            //mode = (power == 0 ? TachoMotorPort.STOP : TachoMotorPort.FORWARD);
        }
    }

    /**
     * This class provides a single thread that drives all of the motor regulation
     * process. Only active motors will be regulated. To try and keep motors
     * as closely synchronized as possible. tach counts for all motors are gathered
     * as close as possible to the same time. Similarly new power levels for each
     * motor are also set at the same time.
     */
    protected static class Controller extends Thread
    {
        static final int UPDATE_PERIOD = 4;
        RegulatedDrivetrain [] activeMotors = new RegulatedDrivetrain[0];
        boolean running = false;

        /**
         * Add a motor to the set of active motors.
         * @param m
         */
        synchronized void addMotor(RegulatedDrivetrain m)
        {
            RegulatedDrivetrain [] newMotors = new RegulatedDrivetrain[activeMotors.length+1];
            System.arraycopy(activeMotors, 0, newMotors, 0, activeMotors.length);
            newMotors[activeMotors.length] = m;
            m.reg.reset();
            activeMotors = newMotors;
        }

        /**
         * Remove a motor from the set of active motors.
         * @param m
         */
        synchronized void removeMotor(RegulatedDrivetrain m)
        {
            RegulatedDrivetrain [] newMotors = new RegulatedDrivetrain[activeMotors.length-1];
            int j = 0;
            for(int i = 0; i < activeMotors.length; i++)
                if (activeMotors[i] != m)
                    newMotors[j++] = activeMotors[i];
            activeMotors = newMotors;
        }

        synchronized void shutdown()
        {
            // Shutdown all of the motors and prevent them from running
            running = false;
            for(int i=0;i<activeMotors.length;i++){
		RegulatedDrivetrain m=activeMotors[i];
                m.setSpeed(0);
	    }
            activeMotors = new RegulatedDrivetrain[0];
        }


        public void run()
        {
            running = true;
            long now = System.currentTimeMillis();
            while(running)
            {
                long delta;
                synchronized (this)
                {
                    delta = System.currentTimeMillis() - now;
                    RegulatedDrivetrain [] motors = activeMotors;
                    now += delta;
                    for(int i=0;i<motors.length;i++) {
			RegulatedDrivetrain m=motors[i];
			m.reg.tachoCnt = m.getTachoCount();
		    }
                    for(int i=0;i<motors.length;i++) {
			RegulatedDrivetrain m=motors[i];
			m.reg.regulateMotor(delta);
		    }
                    for(int i=0;i<motors.length;i++) {
			RegulatedDrivetrain m=motors[i];
			//m.tachoPort.controlMotor(m.reg.power, m.reg.mode);
			m.setMotors(m.reg.power/100.0);//They calibrated this with power from 0-100
		    }
                }
                Delay.msDelay(now + UPDATE_PERIOD - System.currentTimeMillis());
            }	// end keep going loop
        }
    }

	public float getMaxSpeed() {
	    return (float) (HW.maxFPS*12*360/HW.distancePerRev);
	}
}
