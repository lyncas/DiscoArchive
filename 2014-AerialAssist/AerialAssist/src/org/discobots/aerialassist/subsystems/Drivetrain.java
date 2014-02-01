/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;
import org.discobots.aerialassist.commands.CommandBase;
import org.discobots.aerialassist.commands.drive.MecanumDrive;
import org.discobots.aerialassist.commands.drive.TankDrive;
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
    private Talon leftRear;
    private Talon rightFront;
    private Talon rightRear;
    private BetterRobotDrive drive;
    private DoubleSolenoid shifter;
    private DiscoGyro gyro;
    private ADXL345_I2C accelerometer;
    private Velocity velocityReporter;
    public static final boolean MECANUM = false;
    public static final boolean TRACTION = true;
    private boolean currentState = MECANUM;

    public Drivetrain() {
        super("Drivetrain");
        leftFront = new Talon(1, HW.leftFrontMotor);
        leftRear = new Talon(1, HW.leftRearMotor);
        rightFront = new Talon(1, HW.rightFrontMotor);
        rightRear = new Talon(1, HW.rightRearMotor);
        drive = new BetterRobotDrive(leftFront, leftRear, rightFront, rightRear);
        shifter = new DoubleSolenoid(HW.driveShiftASolenoid, HW.driveShiftBSolenoid);

        drive.setSafetyEnabled(false);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
        
        gyro = new DiscoGyro(HW.gyroChannel);
        accelerometer = new ADXL345_I2C(HW.accelModule, ADXL345_I2C.DataFormat_Range.k4G);

        if (Velocity.ENABLE_VELOCITY) {
            try {
                velocityReporter = new Velocity(accelerometer);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }

    public void holonomicPolar(double mag, double dir, double rot) {
        drive.mecanumDrive_Polar(mag, dir, rot);
    }

    public void tankDrive(double leftVal, double rightVal) {
        drive.tankDrive(leftVal, rightVal);
    }

    public void shiftTraction() {
        shifter.set(DoubleSolenoid.Value.kForward);
        currentState = TRACTION;
    }

    public void shiftMecanum() {
        shifter.set(DoubleSolenoid.Value.kReverse);
        currentState = MECANUM;
    }

    public DoubleSolenoid.Value getShiftPosition() {
        return shifter.get();
    }

    public double getGyroAngle() {
        return gyro.getAngle();
    }

    public DiscoGyro getGyro() {
        return gyro;
    }

    public boolean getDriveState() {
        return this.currentState;
    }
    
    public ADXL345_I2C getAccelerometer() {
        return accelerometer;
    }

    public double getXVelocity() {
        return velocityReporter.getXVelocity();
    }

    public double getYVelocity() {
        return velocityReporter.getYVelocity();
    }
}