package org.discobots.aerialassist.controllers;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import org.discobots.aerialassist.utils.DiscoGyro;

/**
 *
 * @author Patrick
 */
public class AngleController implements PIDSource, PIDOutput{
    
    private final PIDController angleController;
    private final DiscoGyro gyro;
    private double output;
    
    public AngleController(double kP,double kI,double kD,DiscoGyro g)
    {
        angleController = new PIDController(kP,kI,kD,this,this);
        angleController.setSetpoint(0.0);
        gyro=g;
    }
    
    public double pidGet() {
        return gyro.getAngle() % 360.0;
    }
    
    public void pidWrite(double pidOut) {
        output = pidOut;
    }
    
    public void setSetpoint() {
        angleController.setSetpoint(gyro.getAngle());
    }
    public void setSetpoint(double value) {
        angleController.setSetpoint(value);
    }
    
    public double getOutput() {
        return output;
    }
    
    public void enable() {
        if (!angleController.isEnable()) {
            angleController.enable(); }} 
    
    public void disable() {
        if (angleController.isEnable()) {
            angleController.disable(); }}
    
    
    
    //Debug Stuff I Guess From Here Down
    public void setPID(double newP,double newI,double newD) {
        angleController.setPID(newP, newI, newD);
    }
    
      public double getP() {
        return angleController.getP();
    } public double getI() {
        return angleController.getI();
    } public double getD() {
        return angleController.getD();
    }
}
