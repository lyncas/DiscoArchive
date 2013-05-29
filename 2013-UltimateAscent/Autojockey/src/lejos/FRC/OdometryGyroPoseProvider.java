/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lejos.FRC;

import disco.utils.DiscoGyro;
import edu.wpi.first.wpilibj.Gyro;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Move;
import lejos.robotics.navigation.MoveProvider;
import lejos.robotics.navigation.Pose;
import lejos.util.KalmanFilter;
import lejos.util.Matrix;

/**
 *
 * @author sam
 */
public class OdometryGyroPoseProvider extends OdometryPoseProvider {

    /*Gyro datasheet: http://www.analog.com/static/imported-files/data_sheets/ADXRS652.pdf if that helps
     * Run the Kalman update in a thread to update more?
     */
    private DiscoGyro gyro;
    private double oldHeading;
    private double gyroAngle;

    public OdometryGyroPoseProvider(MoveProvider mp, DiscoGyro gyro) {
        super(mp);
        this.gyro = gyro;
        gyro.setOffset(gyro.getRawAngle());
        gyroAngle = gyro.getAngle();
    }

    public synchronized void moveStarted(Move move, MoveProvider mp) {
        super.moveStarted(move, mp);
        gyro.setOffset(gyro.getRawAngle());//this is 0
    }

    protected synchronized void updatePose(Move event) {
        float angle = (float) (gyro.getAngle() - angle0);
        float distance = event.getDistanceTraveled() - distance0;
        double dx = 0, dy = 0;
        double headingRad = (java.lejoslang.Math.toRadians(heading));

        if (event.getMoveType() == Move.MoveType_TRAVEL || java.lejoslang.Math.abs(angle) < 0.2f) {
            dx = (distance) * (float) java.lejoslang.Math.cos(headingRad);
            dy = (distance) * (float) java.lejoslang.Math.sin(headingRad);
        } else if (event.getMoveType() == Move.MoveType_ARC) {
            double turnRad = java.lejoslang.Math.toRadians(angle);
            double radius = distance / turnRad;
            dy = radius * (java.lejoslang.Math.cos(headingRad) - java.lejoslang.Math.cos(headingRad + turnRad));
            dx = radius * (java.lejoslang.Math.sin(headingRad + turnRad) - java.lejoslang.Math.sin(headingRad));
        }
        x += dx;
        y += dy;
        heading = normalize(heading + angle); // keep angle between -180 and 180
        super.angle0 = (float) gyro.getAngle();
        super.distance0 = event.getDistanceTraveled();
        super.current = !event.isMoving();
    }

    public synchronized void setPose(Pose aPose) {
        super.setPose(aPose);
        gyro.setOffset(aPose.getHeading() - gyro.getRawAngle());
    }

}
