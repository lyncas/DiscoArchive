/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;
import org.discobots.aerialassist.commands.drive.MecanumDrive;
import org.discobots.aerialassist.utils.AngleController;
import org.discobots.aerialassist.utils.BetterRobotDrive;
import org.discobots.aerialassist.utils.DiscoGyro;
import org.discobots.aerialassist.utils.velocity.Velocity;

/**
 *
 * @author Sam
 */
public class Drivetrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private Talon leftFront;
    private Talon leftMiniFront;
    private Talon leftRear;
    private Talon leftMiniRear;
    private Talon rightFront;
    private Talon rightMiniFront;
    private Talon rightRear;
    private Talon rightMiniRear;
    private BetterRobotDrive drive;
    private BetterRobotDrive miniDrive;
    private DoubleSolenoid shifter;
    public DiscoGyro gyro;
    private ADXL345_I2C accelerometer;
    private AngleController angleCont;
    private Velocity velocityReporter;
    public static final boolean MECANUM = false;
    public static final boolean TRACTION = true;
    private boolean currentState = MECANUM;    //Why does it start out as mecanum if TRACTION is used as the default?

    public Drivetrain() {
        super("Drivetrain");
        leftFront = new Talon(HW.motorModule, HW.leftFrontMotor);
        leftMiniFront = new Talon(HW.motorModule, HW.leftFrontMiniMotor);
        leftRear = new Talon(HW.motorModule, HW.leftRearMotor);
        leftMiniRear = new Talon(HW.motorModule, HW.leftRearMiniMotor);
        rightFront = new Talon(HW.motorModule, HW.rightFrontMotor);
        rightMiniFront = new Talon(HW.motorModule, HW.rightFrontMiniMotor);
        rightRear = new Talon(HW.motorModule, HW.rightRearMotor);
        rightMiniRear = new Talon(HW.motorModule, HW.rightRearMiniMotor);
        drive = new BetterRobotDrive(leftFront, leftRear, rightFront, rightRear);
        miniDrive = new BetterRobotDrive(leftMiniFront, leftMiniRear, rightMiniFront, rightMiniRear);
        shifter = new DoubleSolenoid(HW.solonoidModule,HW.driveShiftSolenoidForward,HW.driveShiftSolenoidReverse);

        drive.setSafetyEnabled(false);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);//should be false
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);//should be false
        
        miniDrive.setSafetyEnabled(false);
        miniDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        miniDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        miniDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);//should be false
        miniDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);//should be false
        
        gyro = new DiscoGyro(HW.gyroChannel);
        accelerometer = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k4G);
        angleCont = new AngleController(.5,0,0,gyro);
        
        if (Velocity.ENABLE_VELOCITY) {
            try {
                velocityReporter = new Velocity(accelerometer);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
//        currentState=getShiftPosition();
        
        }
    }

    public void initDefaultCommand() {
        setDefaultCommand(new MecanumDrive());
        this.currentState = MECANUM;
        gyro.reset(0);
    }

    public void holonomicPolar(double mag, double dir, double rot) {
        drive.mecanumDrive_Polar(mag, dir, rot);
        miniDrive.mecanumDrive_Polar(mag, dir, rot);
    }

    public void tankDrive(double leftVal, double rightVal) {
        drive.tankDrive(leftVal, rightVal);
        miniDrive.tankDrive(leftVal, rightVal);
    }

    public void shiftTraction() {
        shifter.set(DoubleSolenoid.Value.kForward);
        currentState = TRACTION;
    }

    public void shiftMecanum() {
        shifter.set(DoubleSolenoid.Value.kReverse);
        currentState = MECANUM;
    }

    public boolean getShiftPosition() {
        return shifter.get() == DoubleSolenoid.Value.kForward;
    }

    public double getGyroAngle() {
        return gyro.getAngle();
    }
    
    public double getGyroRate() {
        return gyro.getRate();
    }

    public DiscoGyro getGyro() {
        return gyro;
    }

    public boolean getDriveState() {
        return currentState;
    }
    
    public ADXL345_I2C getAccelerometer() {
        return accelerometer;
    }
    
    public double getAngleError() {
        return angleCont.getOutput();
    }
    
    public void setSetpoint() {
        angleCont.setSetpoint();
    }

    public double getXVelocity() {
        return velocityReporter.getXVelocity();
    }

    public double getYVelocity() {
        return velocityReporter.getYVelocity();
    }
}
