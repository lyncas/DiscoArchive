/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package disco.subsystems;

import disco.HW;
import disco.utils.MaxbotixSonar;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import lejos.disco.RegulatedDrivetrain;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;


public class AutoDrivetrain extends Subsystem {
    private Victor leftDrive1;
    private Victor leftDrive2;
    private Victor rightDrive1;
    private Victor rightDrive2;

    private MaxbotixSonar frontSonar1; // youredum.
    private MaxbotixSonar frontSonar2;
    private MaxbotixSonar leftSonar;
    private Encoder leftEncoder;
    private Encoder rightEncoder;

    private Gyro gyro;

    public RegulatedDrivetrain leftDrive,rightDrive;
    private DifferentialPilot pilot;
    private OdometryPoseProvider op;
    private Navigator nav;//Formerly NavPathController

    public AutoDrivetrain(){
	super("Drivetrain");
        leftDrive1=new Victor(HW.LeftDrive1Slot,HW.LeftDrive1Channel);
	leftDrive2=new Victor(HW.LeftDrive2Slot,HW.LeftDrive2Channel);
	rightDrive1=new Victor(HW.RightDrive1Slot,HW.RightDrive1Channel);
	rightDrive2=new Victor(HW.RightDrive2Slot,HW.RightDrive2Channel);

//        frontSonar1=new MaxbotixSonar(HW.maxbotixsonar1Slot,HW.maxbotixsonar1Channel,MaxbotixSonar.Unit.kInches);
//        frontSonar2=new MaxbotixSonar(HW.maxbotixsonar2Slot,HW.maxbotixsonar2Channel,MaxbotixSonar.Unit.kInches);
//        leftSonar=new MaxbotixSonar(HW.maxbotixsonar3Slot,HW.maxbotixsonar3Channel,MaxbotixSonar.Unit.kInches);

        leftEncoder=new Encoder(    HW.leftEncoderSlot,HW.leftEncoderAChannel,
                                    HW.leftEncoderSlot,HW.leftEncoderBChannel,false,EncodingType.k1X);
        rightEncoder=new Encoder(    HW.rightEncoderSlot,HW.rightEncoderAChannel,
                                    HW.rightEncoderSlot,HW.rightEncoderBChannel,false,EncodingType.k1X);
        leftEncoder.setDistancePerPulse(HW.distancePerTick);
        leftEncoder.setReverseDirection(true);
        rightEncoder.setDistancePerPulse(HW.distancePerTick);
	leftEncoder.start();
	rightEncoder.start();

	leftDrive=new RegulatedDrivetrain(leftDrive1,leftDrive2,true,true,leftEncoder);
	rightDrive=new RegulatedDrivetrain(rightDrive1,rightDrive2,false,false,rightEncoder);

	pilot=new DifferentialPilot(2*HW.wheelRadius,HW.wheelSeparation,leftDrive,rightDrive);
        pilot.setAcceleration(4);
        pilot.setTravelSpeed(36);
	op=new OdometryPoseProvider(pilot);
	//This ensures that the position is correct when we do moves not using the navigator
	pilot.addMoveListener(op);
	//Tell it that we are initially pointing in the positive Y direction, instead of positive X.
	op.setPose(new Pose(0,0,90));
	nav=new Navigator(pilot,op);

//        gyro = new Gyro(HW.gyroSlot, HW.gyroChannel);
//        gyro.setSensitivity(0.007);
    }

    public void initDefaultCommand() {}

    public void tankDrive(double left,double right){
	//Use DiferentialPilot or whatever it is
    }

    public void arcadeDrive(double move, double turn) {
	//drive.arcadeDrive(move, turn, true);
	//Use DiferentialPilot or whatever it is
    }

    public double getFrontSonar1(){
        return frontSonar1.getMedianRange();
    }
    public double getFrontSonar2(){
        return frontSonar2.getMedianRange();
    }
    public double getLeftSonar(){
        return leftSonar.getMedianRange();
    }

    public int getLeftEncoder() {
	return leftEncoder.get();
    }
    public double getLeftRate(){
        return leftEncoder.getRate()/12.0;
    }
    public int getRightEncoder(){
	return rightEncoder.get();
    }
    public double getRightRate(){
        return rightEncoder.getRate()/12.0;
    }
    public double getGyroAngle() {
        return gyro.getAngle();
    }

    public double getPWMLeft() {
        return leftDrive.getRawPWM();
    }
    public double getPWMRight() {
        return rightDrive.getRawPWM();
    }

    public DifferentialPilot getPilot(){
	return pilot;
    }

    public PoseProvider getPoseProvider(){
	return op;
    }

    public Navigator getNavigator(){
	return nav;
    }
}