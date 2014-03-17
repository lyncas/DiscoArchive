package org.discobots.aerialassist.utils;

import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;

public class ArduIMU extends SensorBase {

    float accX, accY, accZ, gyrYaw, gyrPit, gyrRol; // Raw
    float fAccX, fAccY, fAccZ; // Filtered
    float[][] pastReadings;
    static final float kGRAVITY = 9.8f;
    int currentSample;
    float velX, velZ; // Velocity
    public static final float kMetersToFt = 3.2808399f;
    I2C i2c;
    int deviceAddress = 2;
    Thread updateThread;
    
    public ArduIMU() {
        i2c = DigitalModule.getInstance(1).getI2C(deviceAddress);
        pastReadings = new float[3][3];

        updateThread = new Thread() {
            public void run() {
                while (true) {
                    update();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        updateThread.start();
    }

    private synchronized void update() {
        accX = readFloatFromI2C();
        accY = readFloatFromI2C();
        accZ = readFloatFromI2C();
        gyrYaw = readFloatFromI2C();
        gyrPit = readFloatFromI2C();
        gyrRol = readFloatFromI2C();

        adjustForGravity();
        integrateAccToVel();
    }

    private float readFloatFromI2C() {
        byte[] buffer = new byte[4];
        if (!i2c.read(0, 4, buffer)) {
            return 0.0f;
        }
        short a = (short) (buffer[0] & 0xff);
        short b = (short) ((buffer[1] << 8) & 0xff00);
        short c = (short) ((buffer[2] << 16) & 0xff00);
        short d = (short) ((buffer[3] << 24) & 0xff00);
        return (a | b | c | d);
    }

    private void adjustForGravity() {
        float[] vec = rotate3DVector(new float[]{accX, accY, accZ}, gyrPit, 1);
        vec = rotate3DVector(vec, gyrRol, 0);
        vec = rotate3DVector(vec, gyrPit, 2);
        this.fAccX = vec[0];
        this.fAccY = vec[1] - kGRAVITY;
        this.fAccZ = vec[2];
    }

    private float[] rotate3DVector(final float[] vector, final float angle, int axis) {
        float[] outVector = new float[3];

        float w = (float) Math.cos(angle / 2.0f);
        float x = (axis == 0) ? ((float) (Math.sin(angle))) : 0;
        float y = (axis == 1) ? ((float) (Math.sin(angle))) : 0;
        float z = (axis == 2) ? ((float) (Math.sin(angle))) : 0;

        outVector[0] = (float) ((1 - 2 * y * y - 2 * z * z) * vector[0]
                + (2 * x * y + 2 * w * z) * vector[1]
                + (2 * x * z - 2 * w * y) * vector[2]);
        outVector[1] = (float) ((2 * x * y - 2 * w * z) * vector[0]
                + (1 - 2 * x * x - 2 * z * z) * vector[1]
                + (2 * y * z + 2 * w * x) * vector[2]);
        outVector[2] = (float) ((2 * x * z + 2 * w * y) * vector[0]
                + (2 * y * z - 2 * w * x) * vector[1]
                + (1 - 2 * x * x - 2 * y * y) * vector[2]);

        return outVector;
    }

    public void integrateAccToVel() { // Uses Simpson
        pastReadings[currentSample] = new float[]{fAccX, fAccY, fAccZ};
        currentSample++;
        if (currentSample == 3) {
            velX += (pastReadings[0][0] + 4.0 * pastReadings[1][0] + pastReadings[2][0] - 6.0 * 0) * (20f / 1000f);
            velZ += (pastReadings[0][2] + 4.0 * pastReadings[1][2] + pastReadings[2][2] - 6.0 * 0) * (20f / 1000f);
            currentSample = 0;
        }
    }

    public synchronized float getAccelerationX() {
        return this.fAccX;
    }

    public synchronized float getAccelerationY() {
        return this.fAccY;
    }

    public synchronized float getAccelerationZ() {
        return this.fAccZ;
    }

    public synchronized float getGyroscopeYaw() {
        return this.gyrYaw;
    }

    public synchronized float getGyroscopePitch() {
        return this.gyrPit;
    }

    public synchronized float getGyroscopeRoll() {
        return this.gyrRol;
    }

    public synchronized float getVelocityX() {
        return this.velX * kMetersToFt;
    }

    public synchronized float getVelocityZ() {
        return this.velZ * kMetersToFt;
    }
}
