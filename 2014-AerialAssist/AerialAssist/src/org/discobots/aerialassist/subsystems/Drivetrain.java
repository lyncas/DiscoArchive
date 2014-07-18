package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;
import org.discobots.aerialassist.commands.drive.CheesyArcadeDrive;
import org.discobots.aerialassist.utils.UltrasonicSRF02_I2C;

public class Drivetrain extends Subsystem {

    private Talon leftFront;
    private Talon leftMiniFront;
    private Talon leftRear;
    private Talon leftMiniRear;
    private Talon rightFront;
    private Talon rightMiniFront;
    private Talon rightRear;
    private Talon rightMiniRear;
    private RobotDrive drive;
    private RobotDrive miniDrive;
    
    private Encoder forwardEncoder;
    private UltrasonicSRF02_I2C ultrasonicIntake;
    private UltrasonicSRF02_I2C ultrasonicShooter;
    
    private Relay leds;
    
    boolean useMini = true;

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
        drive = new RobotDrive(leftFront, leftRear, rightFront, rightRear);
        miniDrive = new RobotDrive(leftMiniFront, leftMiniRear, rightMiniFront, rightMiniRear);

        drive.setSafetyEnabled(false);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true); // Should be false
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true); // Should be false

        miniDrive.setSafetyEnabled(false);
        miniDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        miniDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        miniDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true); // Should be false
        miniDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true); // Should be false

        forwardEncoder = new Encoder(HW.forwardEncoderA, HW.forwardEncoderB);
        forwardEncoder.setDistancePerPulse(HW.distancePerPulse);
        forwardEncoder.start();
        
        ultrasonicIntake = new UltrasonicSRF02_I2C(224);
        ultrasonicShooter = new UltrasonicSRF02_I2C(242);
        
        leds = new Relay(1, HW.ledRelay);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new CheesyArcadeDrive());
    }

    public void tankDrive(double leftVal, double rightVal) {
        drive.tankDrive(leftVal, rightVal);
        if (this.useMini) {
            miniDrive.tankDrive(leftVal, rightVal);
        }
    }

    public double getEncoderForwardDistance() {
        return this.forwardEncoder.getDistance();
    }

    public void setMiniCimUsage(boolean a) {
        this.useMini = a;
    }
    
    public int getUltrasonicIntakeAverageValue() {
        return this.ultrasonicIntake.getAverageValue();
    }
    public int getUltrasonicShooterAverageValue() {
        return this.ultrasonicShooter.getAverageValue();
    }
    
    public boolean getMiniCimUsage() {
        return this.useMini;
    }
    boolean state;
    public void writeLEDState(boolean state) {
        if (state) {
            this.leds.set(Relay.Value.kOn);
        } else {
            this.leds.set(Relay.Value.kOff);
        }
        this.state = state;
    }
    
    public boolean getLEDState() {
        return this.state;
    }
}
