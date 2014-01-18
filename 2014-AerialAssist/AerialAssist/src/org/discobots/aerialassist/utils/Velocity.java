/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.utils;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.Timer;
import org.discobots.aerialassist.commands.CommandBase;

/**
 *
 * @author Sam m
 */
public class Velocity {

    static Vaccumulator v;

    public Velocity() {
        v = new Vaccumulator();
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

    public Vaccumulator() {
        accelerometer = CommandBase.drivetrain.getAccelerometer();
    }

    /*
    * Take a few samples to find our offset
    */
    public void calibrate() {
        double sumX = 0, sumY = 0;
        int samples=100;
        double time=Timer.getFPGATimestamp();
        for (int count = 0; count < samples; count++) {
            sumX += accelerometer.getAcceleration(ADXL345_I2C.Axes.kX);
            sumY += accelerometer.getAcceleration(ADXL345_I2C.Axes.kY);
            //wait 500 microseconds between samples
            while(Timer.getFPGATimestamp()-time < (500/1000000.0)){
                yield();
            }
            time=Timer.getFPGATimestamp();
        }
        calX=sumX/samples;
        calY=sumY/samples;
    }


    public void run() {
        calibrate();
        xvelocity = yvelocity = t = 0;
        time = Timer.getFPGATimestamp();
        while (true) {
            t = Timer.getFPGATimestamp() - time;
            time = Timer.getFPGATimestamp();
            xvelocity += ((accelerometer.getAcceleration(ADXL345_I2C.Axes.kX) - calX) * 9.81) * t;
            yvelocity += ((accelerometer.getAcceleration(ADXL345_I2C.Axes.kY) - calY) * 9.81) * t;
            yield();
        }
    }

    public double getxvelocity() {
        return xvelocity;
    }

    public double getyvelocity() {
        return yvelocity;
    }
}
