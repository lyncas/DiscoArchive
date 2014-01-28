/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.utils.velocity;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.fpga.tAccumulator;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Sam M
 */
public class Velocity {

    public static boolean ENABLE_VELOCITY = false;
    
    static Vaccumulator v;

    public Velocity(ADXL345_I2C acc) throws Exception {
        if (!ENABLE_VELOCITY){
            throw new Exception();
        }
        v = new Vaccumulator(acc);
        v.start();
    }

    public double getXVelocity() {
        return v.getxvelocity();
    }

    public double getYVelocity() {
        return v.getyvelocity();
    }
}

class Vaccumulator extends Thread {

    private double xvelocity, yvelocity, time, t;
    private double calX = 0, calY = 0;
    private ADXL345_I2C accelerometer;

    public Vaccumulator(ADXL345_I2C acc) {
        accelerometer = acc;
    }

    /*
     * Take a few samples to find our offset
     */
    public void calibrate() {
        double sumX = 0, sumY = 0;
        int samples = 100;
        double time = Timer.getFPGATimestamp();
        for (int count = 0; count < samples; count++) {
            sumX += accelerometer.getAcceleration(ADXL345_I2C.Axes.kX);
            sumY += accelerometer.getAcceleration(ADXL345_I2C.Axes.kY);
            //wait 500 microseconds between samples
            while (Timer.getFPGATimestamp() - time < (500 / 1000000.0)) {
                yield();
            }
            time = Timer.getFPGATimestamp();
        }

        calX = sumX / samples;
        calY = sumY / samples;
    }

    public void run() {
        calibrate();
        System.out.println("Calibration done!");
        xvelocity = yvelocity = t = 0;

        //acceleration ring buffer for smoothing/averaging
        int bufferlength = 50;
        double[][] accbuffer = new double[2][bufferlength];
        int bufferindex = 0;

        time = Timer.getFPGATimestamp();
        double masterTime = time;
        while (true) {
            time = Timer.getFPGATimestamp();
            
            for(int x=0;x<bufferlength;x++){
            //put data into the buffer
            double t=Timer.getFPGATimestamp();
            /*while (Timer.getFPGATimestamp() - t < (100 / 1000000.0)) {
                yield();
            }*/
            accbuffer[0][bufferindex] = accelerometer.getAcceleration(ADXL345_I2C.Axes.kX);
            accbuffer[1][bufferindex] = accelerometer.getAcceleration(ADXL345_I2C.Axes.kY);
            bufferindex++;
            //after we fill it up, average and add to to the velocity.
            if (bufferindex == bufferlength) {
                double delta = Timer.getFPGATimestamp() - masterTime;
                xvelocity += (average(accbuffer[0]) - calX) * 9.81 * delta;
                yvelocity += (average(accbuffer[1]) - calY) * 9.81 * delta;
                masterTime=Timer.getFPGATimestamp();
                //System.out.println("avg: "+xvelocity+" "+yvelocity);
            }
            bufferindex %= bufferlength;
            }
            t=Timer.getFPGATimestamp();
            while (Timer.getFPGATimestamp() - t < (50000 / 1000000.0)) {
                yield();
            }
        }
    }

    private double average(double[] a) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        return sum / (double) a.length;
    }

    public double getxvelocity() {
        return xvelocity;
    }

    public double getyvelocity() {
        return yvelocity;
    }
}
