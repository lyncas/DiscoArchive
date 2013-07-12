package org.discobots.frc.ascent.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.frc.ascent.HW;
import org.discobots.frc.ascent.framework.CounterEncoder;

public class Shooter extends Subsystem {
    private Talon frontVictor, backVictor;
    private CounterEncoder frontEncoder, backEncoder;
    private Solenoid mainShoot, clearShoot;
    double pwmSetpoint = 0.0; // For Open-Loop Shooter
    double rpmSetpoint = 0.0; // For Closed-Loop Shooter
    
    
    public Shooter() {
        frontVictor = new Talon(HW.encoderShooterFrontSlot, HW.encoderShooterFrontChannel);
        backVictor = new Talon(HW.encoderShooterBackSlot, HW.encoderShooterBackChannel);
        mainShoot = new Solenoid(HW.solenoidShootAChannel);
        clearShoot = new Solenoid(HW.solenoidShootBChannel);
        frontEncoder = new CounterEncoder(HW.encoderShooterFrontSlot, HW.encoderShooterFrontChannel, 2);
        backEncoder = new CounterEncoder(HW.encoderShooterBackSlot, HW.encoderShooterBackChannel, 2);
    }
    protected void initDefaultCommand() {
        
    }
    public double getFrontPWM() {
        return frontVictor.get();
    }
    public double getBackPWM() {
        return backVictor.get();
    }
    public void setFrontPWM(double speed) {
        frontVictor.set(speed);
    }
    public void setBackPWM(double speed) {
        backVictor.set(speed);
    }
    public double getFrontRPM() {
        return frontEncoder.getFilteredRPM();
    }
    public double getBackRPM() {
        return backEncoder.getFilteredRPM();
    }
    public void setSetpointRPM(double setpoint) {
        this.rpmSetpoint = setpoint;
    }
    public void setSetpointPWM(double setpoint) {
        this.pwmSetpoint = setpoint;
    }
    public double getSetpointRPM() {
        return this.rpmSetpoint;
    }
    public double getSetpointPWM() {
        return this.pwmSetpoint;
    }
    public boolean getMainShootPosition() {
        return mainShoot.get();
    } 
    public boolean getClearShootPosition() {
        return clearShoot.get();
    }
    public void setMainShootPosition(boolean pos) {
        mainShoot.set(pos);
    }
    public void setClearShootPosition(boolean pos) {
        clearShoot.set(pos);
    }
}