package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;
import org.discobots.aerialassist.commands.drive.CheesyArcadeDrive;
import org.discobots.aerialassist.utils.AngleController;
import org.discobots.aerialassist.utils.BetterRobotDrive;
import org.discobots.aerialassist.utils.DiscoGyro;
import org.discobots.aerialassist.utils.UltrasonicSRF02_I2C;
import org.discobots.aerialassist.utils.velocity.Velocity;

public class Drivetrain extends Subsystem {

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
    private Encoder forwardEncoder;
    private AngleController angleCont;
    private Velocity velocityReporter;
    private UltrasonicSRF02_I2C ultrasonicIntake;
    private UltrasonicSRF02_I2C ultrasonicShooter;
    private Relay leds;
  
    
    public boolean fieldCentricEnabled = true;
    public static final boolean OMNI = false;
    public static final boolean TRACTION = true;
    private boolean currentState = OMNI;
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
        drive = new BetterRobotDrive(leftFront, leftRear, rightFront, rightRear);
        miniDrive = new BetterRobotDrive(leftMiniFront, leftMiniRear, rightMiniFront, rightMiniRear);
        shifter = new DoubleSolenoid(HW.solonoidModule, HW.driveShiftSolenoidForward, HW.driveShiftSolenoidReverse);

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

        gyro = new DiscoGyro(HW.gyroChannel);
        accelerometer = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k4G);

        forwardEncoder = new Encoder(HW.forwardEncoderA, HW.forwardEncoderB);
        forwardEncoder.setDistancePerPulse(HW.distancePerPulse);
        forwardEncoder.start();
        
        ultrasonicIntake = new UltrasonicSRF02_I2C(224);
        ultrasonicShooter = new UltrasonicSRF02_I2C(242);
        
        angleCont = new AngleController(-0.025, 0, 0, gyro);
        angleCont.setEnabled(true);
        
        leds = new Relay(1, HW.ledRelay);
        
        if (Velocity.ENABLE_VELOCITY) {
            try {
                velocityReporter = new Velocity(accelerometer);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void initDefaultCommand() {
        setDefaultCommand(new CheesyArcadeDrive());
        this.currentState = OMNI;
        gyro.reset(0);
    }

    public void holonomicPolar(double mag, double dir, double rot) {
        drive.mecanumDrive_Polar(mag, dir, rot);
        if (this.useMini) {
            miniDrive.mecanumDrive_Polar(mag, dir, rot);
        }
    }

    public void tankDrive(double leftVal, double rightVal) {
        drive.tankDrive(leftVal, rightVal);
        if (this.useMini) {
            miniDrive.tankDrive(leftVal, rightVal);
        }
    }

    public void shiftTraction() {
        shifter.set(DoubleSolenoid.Value.kForward);
        currentState = TRACTION;
    }

    public void shiftOmni() {
        shifter.set(DoubleSolenoid.Value.kReverse);
        currentState = OMNI;
    }

    public boolean getShiftPosition() {
        return shifter.get() == DoubleSolenoid.Value.kForward;
    }

    public double getGyroAngle() {
        return gyro.getAngle();
    }

    public double getGyroNormalizedAngle() {
        return gyro.getNormalizedAngle();
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

    public double getAngleControllerOutput() {
        return angleCont.getOutput();
    }

    public void setAngleControllerSetpoint(double a) {
        angleCont.setSetpoint(a);
    }

    private static final double kINCREMENT = 30.0;

    public void incrementSetpoint(double a) {
        angleCont.setSetpoint(angleCont.getSetpoint() + a * kINCREMENT);
    }

    public double getXVelocity() {
        return velocityReporter.getXVelocity();
    }

    public double getYVelocity() {
        return velocityReporter.getYVelocity();
    }

    public boolean isFieldCentricEnabled() {
        return this.fieldCentricEnabled;
    }

    public void setFieldCentricEnabled(boolean a) {
        this.fieldCentricEnabled = a;
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
