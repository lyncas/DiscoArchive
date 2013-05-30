/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lejos.FRC;

import disco.utils.DiscoGyro;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Move;
import lejos.robotics.navigation.MoveProvider;
import lejos.robotics.navigation.Pose;

/**
 *
 * @author sam
 */
public class OdometryGyroPoseProvider extends OdometryPoseProvider {

    /*
     * Gyro datasheet: http://www.analog.com/static/imported-files/data_sheets/ADXRS652.pdf if that helps
     * Run the Kalman update in a thread to update more?
     */
    private DiscoGyro gyro;
    private double oldHeading;
    private double gyroAngle;
    private double gyroOffset = 0;//added to actual gyro reading to get logical heading.

    public OdometryGyroPoseProvider(MoveProvider mp, DiscoGyro gyro) {
	super(mp);
	this.gyro = gyro;
	gyro.reset();
	gyroAngle = 0;
    }

    public synchronized void moveStarted(Move move, MoveProvider mp) {
	super.moveStarted(move, mp);
	gyro.reset();
    }

    /*
     * Note how we update offset to compute logical heading when the move ends
     * but not when it begins. This means the gyro can drift for as long as it
     * wants between moves without corrupting heading. Genius, right?
     */
    public synchronized void moveStopped(Move move, MoveProvider mp) {
	super.moveStopped(move, mp);
	gyroOffset = gyro.getAngle() + gyroOffset;
	gyro.reset();
    }

    protected synchronized void updatePose(Move event) {
	float d_angle = (float) (gyro.getAngle() - angle0);
	float distance = event.getDistanceTraveled() - distance0;
	double dx = 0, dy = 0;
	double headingRad = (java.lejoslang.Math.toRadians(heading));

	if (event.getMoveType() == Move.MoveType_TRAVEL || java.lejoslang.Math.abs(d_angle) < 0.2f) {
	    dx = (distance) * (float) java.lejoslang.Math.cos(headingRad);
	    dy = (distance) * (float) java.lejoslang.Math.sin(headingRad);
	} else if (event.getMoveType() == Move.MoveType_ARC) {
	    double turnRad = java.lejoslang.Math.toRadians(d_angle);
	    double radius = distance / turnRad;
	    dy = radius * (java.lejoslang.Math.cos(headingRad) - java.lejoslang.Math.cos(headingRad + turnRad));
	    dx = radius * (java.lejoslang.Math.sin(headingRad + turnRad) - java.lejoslang.Math.sin(headingRad));
	}
	x += dx;
	y += dy;
	heading = normalize((float) (gyro.getAngle() + gyroOffset)); // keep angle between -180 and 180
	super.angle0 = (float) gyro.getAngle();
	super.distance0 = event.getDistanceTraveled();
	super.current = !event.isMoving();
    }

    public synchronized void setPose(Pose aPose) {
	super.setPose(aPose);
	gyro.reset();
	gyroOffset = aPose.getHeading();
    }
}
