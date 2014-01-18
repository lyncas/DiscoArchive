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
 * @author Sam
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

    public Vaccumulator() {
    }

    public void run() {
        xvelocity = yvelocity = t = 0;
        time = Timer.getFPGATimestamp();
        while (true) {
            t = Timer.getFPGATimestamp() - time;
            time = Timer.getFPGATimestamp();
            xvelocity += (CommandBase.drivetrain.getAccelerometer().getAcceleration(ADXL345_I2C.Axes.kX) * 9.8) * t;
            yvelocity += (CommandBase.drivetrain.getAccelerometer().getAcceleration(ADXL345_I2C.Axes.kY) * 9.8) * t;
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
