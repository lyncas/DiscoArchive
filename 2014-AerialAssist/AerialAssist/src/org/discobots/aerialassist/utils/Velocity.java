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
    private ADXL345_I2C accelerometer;

    public Vaccumulator() {
        accelerometer=CommandBase.drivetrain.getAccelerometer();
    }
    
    public double calibrateX(){
    double sum=0;
    int count;
    for(count=0;count<500;count++)
        sum+=accelerometer.getAcceleration(ADXL345_I2C.Axes.kX);
    return sum/count;
    }
    public double calibrateY(){
    double sum=0;
    int count;
    for(count=0;count<500;count++)
        sum+=accelerometer.getAcceleration(ADXL345_I2C.Axes.kY);
    return sum/count;
    }

    public void run() {
        xvelocity = yvelocity = t = 0;
        double cx=calibrateX();
        double cy=calibrateY();
        time = Timer.getFPGATimestamp();
        while (true) {
            t = Timer.getFPGATimestamp() - time;
            time = Timer.getFPGATimestamp();
            xvelocity += ((accelerometer.getAcceleration(ADXL345_I2C.Axes.kX)+cx) * 9.8) * t;
            yvelocity += ((accelerometer.getAcceleration(ADXL345_I2C.Axes.kY)+cy) * 9.8) * t;
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
