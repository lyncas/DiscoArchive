/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lejos.FRC;

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
    private Gyro gyro;
    private double oldHeading;
    private double gyroAngle;
    private double gyroAngleOffset=0;
    protected KalmanFilter headingFilter;

    public OdometryGyroPoseProvider(MoveProvider mp, Gyro gyro) {
	super(mp);
	this.gyro = gyro;
	gyroAngleOffset = -getRangedGyro();//start at 0
	gyroAngle=getRangedGyro();
	Matrix F = new Matrix(new double[][]{{1}});//Identity: State transforms to itself
	Matrix B = new Matrix(new double[][]{{1}});//Identity: Gyro give degrees, we use degrees.
	Matrix H = new Matrix(new double[][]{{1}});//Identity: Still degrees.
	Matrix R = new Matrix(new double[][]{{2}});//TODO: Observation noise? (gyro)
	Matrix Q = new Matrix(new double[][]{{5}});//TODO: Process noise? (wheel slip)
	Matrix state = new Matrix(new double[][]{{0}}); //Start at heading 0
	Matrix covariance = new Matrix(new double[][]{{0}}); //Certain about starting point. We told it to be there.
	headingFilter = new KalmanFilter(F, B, H, R, Q);
	headingFilter.setState(state, covariance);
    }

    protected synchronized void updatePose(Move event) {
	super.updatePose(event);//among other things, find encoder based heading
	Matrix Control = new Matrix(new double[][]{{this.heading-oldHeading}});//Heading update from encoders
	Matrix Measurement = new Matrix(new double[][]{{getRangedGyro()}});//Heading measurement from gyro
	headingFilter.update(Control, Measurement);//Predict the real heading
	this.heading=(float) headingFilter.getMean().get(0, 0);
	oldHeading=heading;
    }

    public synchronized void setPose(Pose aPose) {
	super.setPose(aPose);
	Matrix newState = new Matrix(new double[][]{{aPose.getHeading()}});
	Matrix newCovariance = new Matrix(new double[][]{{0}});//Certain about this heading. We told it to be there.
	headingFilter.setState(newState, newCovariance);
	gyroAngleOffset=aPose.getHeading()-getRangedGyro();
    }

    /*
     * Return the gyro value, scaled to [-180,180] and offset to agree with set location
     */
    private double getRangedGyro(){
	//make [-180,180]
	double gyroTemp=gyro.getAngle()+gyroAngleOffset;
	//offset
	return normalize((float)gyroTemp);
    }
}
