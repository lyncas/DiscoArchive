package Sensors;

import Sensors.Jama.Matrix;

/**
 * @author pavchag
 */
public class VelocityMatrices {

    /*
     * this should run in its own thread eventually
     * -nelson
     */

    public static final double mass = 54.4310844;   //mass in kg
    public static final double alpha = 0.5; //weight distribution constanc
    public static final double thetaFR = Math.PI/4;
    public static final double thetaFL = 3*Math.PI/4;
    public static final double thetaBL = 5*Math.PI/4;
    public static final double thetaBR = 7*Math.PI/4;
    public static final double radius = 0.635; //radius (wheel-to-wheel) in meters

    public static double xVel;
    static Matrix wheelVelMat   = new Matrix(4, 1);
    static Matrix wheelForceMat = new Matrix(4, 1);
    static Matrix velCouplMat   = new Matrix(3, 4);
    static Matrix forceCouplMat = new Matrix(3, 4);
    static Matrix accelMatrix   = new Matrix(3, 1);
    static Matrix velocityMatrix= new Matrix(3, 1);
    static Matrix slipMatrix   = new Matrix(4, 4);
    static Matrix zeroMatrix = new Matrix(4, 1);
    static Matrix robotVelocityMatrix = new Matrix(3,1);
    static Matrix robotVelMat = new Matrix(3,1);

    /*
     *
     */
    public static void setRobotVelMat(double vx, double vy, double rot)
    {
        robotVelMat.set(0,0,vx);
        robotVelMat.set(1,0,vy);
        robotVelMat.set(2,0,rot);
    }

    public static void setWheelVelMat(double v1, double v2, double v3, double v4) {
        wheelVelMat.set(0, 0, v1);
        wheelVelMat.set(1, 0, v2);
        wheelVelMat.set(2, 0, v3);
        wheelVelMat.set(3, 0, v4);
    }

    /*
     *
     */
    public static Matrix calcWheelVelocity() {
        velocityMatrix = velCouplMat.times(robotVelMat);
        velocityMatrix = velocityMatrix.times(Math.sin(Math.PI/4)+Math.cos(Math.PI/4));
        return velocityMatrix;
    }

    public static Matrix calcVelocity() {
        /*System.out.println("VELOCITY COUPLING MATRIX, PSEUDOINVERTED");

        double[][] raw = velCouplMat.getArray();
        for(int r=0;r<raw.length;r++) {
            for(int c=0;c<raw[r].length;c++)
                System.out.print(raw[r][c] + "\t\t\t");
            System.out.println();
        }

        System.out.println("\n\nWHEEL VELOCITY MATRIX");

        raw = wheelVelMat.getArray();
        for(int r=0;r<raw.length;r++) {
            for(int c=0;c<raw[r].length;c++)
                System.out.print(raw[r][c]);
            System.out.println();
        }*/
        velocityMatrix = velCouplMat.times(wheelVelMat);
        return velocityMatrix;
    }
    /*
     *
     */
    public static void calcAccel() {
        accelMatrix = forceCouplMat.times(wheelForceMat).times(1/mass);
    }
    /*
     *
     */
    public static Matrix getAccelMat() {
        return accelMatrix;
    }

    // This code is for the acceleration of the robot.
    public static void setWheelForceMat(double f1, double f2, double f3, double f4) {
        wheelForceMat.set(0, 0, f1);
        wheelForceMat.set(1, 0, f2);
        wheelForceMat.set(2, 0, f3);
        wheelForceMat.set(3, 0, f4);
    }

    public static Matrix getVelCouplMat() {
        return velCouplMat;
    }

    public static Matrix getWheelVelMat() {
        return wheelVelMat;
    }
    public static Matrix getWheelForceMat() {
        return wheelForceMat;
    }
    public static Matrix getVelocityVector() {
        return velocityMatrix;
    }
    public static Matrix getSlipMat()  {
        return slipMatrix;
    }
    public static Matrix getZeroMat()  {
        return zeroMatrix;
    }

    public static void initVelCouplMat() {
        double[][] velCoupl = new double[4][3];
        velCoupl[0][0] = -Math.sin(thetaFR);
        velCoupl[0][1] = Math.cos(thetaFR);
        velCoupl[0][2] = 1;

        velCoupl[1][0] = -Math.sin(thetaFL);
        velCoupl[1][1] = Math.cos(thetaFL);
        velCoupl[1][2] = 1;

        velCoupl[2][0] = -Math.sin(thetaBL);
        velCoupl[2][1] = Math.cos(thetaBL);
        velCoupl[2][2] = 1;

        velCoupl[3][0] = -Math.sin(thetaBR);
        velCoupl[3][1] = Math.cos(thetaBR);
        velCoupl[3][2] = 1;
        velCouplMat = new Matrix(velCoupl);
        velCouplMat = velCouplMat.inverse();
    }

    public static void initForceCouplMat() {
        double[][] forceCoupl = new double[3][4];
        forceCoupl[0][0] = -Math.sin(thetaFR);
        forceCoupl[0][1] = -Math.sin(thetaFL);
        forceCoupl[0][2] = -Math.sin(thetaBL);
        forceCoupl[0][3] = -Math.sin(thetaBR);

        forceCoupl[1][0] = Math.cos(thetaFR);
        forceCoupl[1][1] = Math.cos(thetaFL);
        forceCoupl[1][2] = Math.cos(thetaBL);
        forceCoupl[1][3] = Math.cos(thetaBR);

        forceCoupl[2][0] = 1 / alpha;
        forceCoupl[2][1] = 1 / alpha;
        forceCoupl[2][2] = 1 / alpha;
        forceCoupl[2][3] = 1 / alpha;

        forceCouplMat = new Matrix(forceCoupl);
    }
    public static void setSlipMat() {
        double[][] slipMat = new double[4][4];
        slipMat[0][0] = 1/4;
        slipMat[0][1] = -1/4;
        slipMat[0][2] = 1/4;
        slipMat[0][3] = -1/4;

        slipMat[1][0] = -1/4;
        slipMat[1][1] = 1/4;
        slipMat[1][2] = -1/4;
        slipMat[1][3] = 1/4;

        slipMat[2][0] = 1/4;
        slipMat[2][1] = -1/4;
        slipMat[2][2] = 1/4;
        slipMat[2][3] = -1/4;

        slipMat[3][0] = -1/4;
        slipMat[3][1] = 1/4;
        slipMat[3][2] = -1/4;
        slipMat[3][3] = 1/4;
        slipMatrix = new Matrix(slipMat);
    }
    public static void setZeroMat()  {
        double[][] zeroMat = new double[4][1];
        zeroMat[0][0] = 0;
        zeroMat[1][0] = 0;
        zeroMat[2][0] = 0;
        zeroMat[3][0] = 0;
        zeroMatrix = new Matrix(zeroMat);
    }
    public static void setRobVelMat(double a, double b, double c)  {
        double[][] robVelMat = new double[3][1];
        robVelMat[0][0] = a;
        robVelMat[1][0] = b;
        robVelMat[2][0] = c;
        robotVelocityMatrix = new Matrix(robVelMat);
    }

}