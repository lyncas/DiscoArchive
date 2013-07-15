package org.discobots.frc.ascent.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.frc.ascent.HW;
import org.discobots.frc.ascent.commands.drive.OpenArcade;
import org.discobots.frc.ascent.commands.drive.OpenTank;
/*
 * 
 */
public class Drivetrain extends Subsystem {
    private Victor leftFrontVictor, leftBackVictor, rightFrontVictor, rightBackVictor;
    private Encoder leftEncoder, rightEncoder;
    private Gyro gyro; // UNUSED AND NOT IMPLEMENTED
    private DigitalInput switchLeft, switchRight;
    private RobotDrive robotDrive;
    private double rightPrev, leftPrev;
    private double movePrev, turnPrev;
    private boolean invert = false;
    private final double rampThreshold = 0.1;
    
    public Drivetrain() {
        super("Drivetrain");
        leftFrontVictor = new Victor(HW.motorDriveLeft1Slot, HW.motorDriveLeft1Channel);
        leftBackVictor = new Victor(HW.motorDriveLeft2Slot, HW.motorDriveLeft2Channel);
        rightFrontVictor = new Victor(HW.motorDriveRight1Slot, HW.motorDriveRight1Channel);
        rightBackVictor = new Victor(HW.motorDriveRight2Slot, HW.motorDriveRight2Channel);
        
        robotDrive = new RobotDrive(leftFrontVictor, leftBackVictor, rightFrontVictor, rightBackVictor);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true); // Should be false but nope.
	robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true); // Should be false but nope.
	robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
	robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        
        rightEncoder = new Encoder( HW.encoderDriveRightSlot, HW.encoderDriveRightAChannel,
                                    HW.encoderDriveLeftSlot, HW.encoderDriveRightBChannel,
                                    false, CounterBase.EncodingType.k1X);
        leftEncoder = new Encoder(  HW.encoderDriveLeftSlot, HW.encoderDriveLeftAChannel,
                                    HW.encoderDriveLeftSlot, HW.encoderDriveLeftBChannel,
                                    false, CounterBase.EncodingType.k1X);
        rightEncoder.setDistancePerPulse(HW.distancePerPulse);
        leftEncoder.setDistancePerPulse(HW.distancePerPulse);
        rightEncoder.start();
        leftEncoder.start();
        
        gyro = new Gyro(HW.gyroDriveSlot, HW.gyroDriveChannel);
        gyro.reset();
        
        switchLeft = new DigitalInput(HW.limitswitchDriveLeftSlot, HW.limitswitchDriveLeftChannel);
        switchRight = new DigitalInput(HW.limitswitchDriveRightSlot, HW.limitswitchDriveRightChannel);
        
        rightPrev = leftPrev = movePrev = turnPrev = 0.0; 
    }
    public void tankDrive(double left, double right, boolean scale, boolean ramp) {
        double leftOutput = left, rightOutput = right;
        if (invert) {
            leftOutput = -right;
            rightOutput = -left;
        }
        if (scale) {
            leftOutput = scaleInput(leftOutput);
            rightOutput = scaleInput(rightOutput);
        }
        if (ramp) { // Recommended to prevent driver from driving violently.
            if (leftPrev - leftOutput > rampThreshold) {
                leftOutput = leftPrev - rampThreshold;
            } else if (leftOutput - leftPrev > rampThreshold) {
                leftOutput = leftPrev + rampThreshold;
            }
            if (rightPrev - rightOutput > rampThreshold) {
                rightOutput = rightPrev - rampThreshold;
            } else if (rightOutput - rightPrev > rampThreshold) {
                rightOutput = rightPrev + rampThreshold;
            }
        }
        robotDrive.tankDrive(leftOutput, rightOutput);
        rightPrev = rightOutput;
        leftPrev = leftOutput;
        movePrev = (rightOutput + leftOutput) / 2;
        turnPrev = ((rightOutput + leftOutput) / 2) - leftOutput; // May be rightOutput? Test this soon.
    }
    public void arcadeDrive(double move, double turn, boolean scale, boolean ramp) {
        double moveOutput = move, turnOutput = turn;
        if (invert) {
            moveOutput = -move;
            turnOutput = -turn;
        }
        if (scale) {
            moveOutput = scaleInput(moveOutput);
            turnOutput = scaleInput(turnOutput);
        }
        if (ramp) { // Recommended to prevent driver from driving violently.
            if (movePrev - moveOutput > rampThreshold) {
                moveOutput = movePrev - rampThreshold;
            } else if (moveOutput - movePrev > rampThreshold) {
                moveOutput = movePrev + rampThreshold;
            }
            if (turnPrev - turnOutput > rampThreshold) {
                turnOutput = turnPrev - rampThreshold;
            } else if (turnOutput - turnPrev > rampThreshold) {
                turnOutput = turnPrev + rampThreshold;
            }
        }
        turnOutput = -turnOutput;
        robotDrive.arcadeDrive(moveOutput, turnOutput);
        movePrev = moveOutput;
        turnPrev = turnOutput;
        rightPrev = moveOutput + turnOutput;
        leftPrev = moveOutput - turnOutput;
    }
    public void setInvert(boolean val) {
        this.invert = val;
    }
    public boolean getInvert() {
        return invert;
    }
    public double scaleInput(double input) {
        double output = input;
        // Sam's Special Formula
        // 0.024 + 0.296*x - 0.085*x*x + 0.011*x*x*x - 0.000447*x*x*x*x
        // Special Sauce Formula
        // 0.308*x + 0.0327*x*x*x + 0.000113*x*x*x*x*x - 0.147*x*x - 0.00314*x*x*x*x
        double x = 10.0*input;
	output = 0.308*x + 0.0327*x*x*x + 0.000113*x*x*x*x*x - 0.147*x*x - 0.00314*x*x*x*x;
        return output;
        
    }
    public void initDefaultCommand() {
        setDefaultCommand(new OpenArcade());
    }
    public boolean isSwitchLeftPressed() {
        return switchLeft.get();
    }
    public boolean isSwitchRightPressed() {
        return switchRight.get();
    }
    public int getEncoderLeftValue() {
        return leftEncoder.get();
    }
    public int getEncoderRightValue() {
        return rightEncoder.get();
    }
    public double getEncoderLeftRate() {
        return leftEncoder.getRate() / 12;
    }
    public double getEncoderRightRate() {
        return rightEncoder.getRate() / 12;
    }
    public double getMotorPWMLeft() {
        return leftFrontVictor.getSpeed();
    }
    public double getMotorPWMRight() {
        return rightFrontVictor.getSpeed();
    }
    public double getGyroAngle() {
        return gyro.getAngle();
    }
}