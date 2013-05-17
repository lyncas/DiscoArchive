//====================================================================
// FILE NAME: HW.java (Team 2587 - Discobots)
//
// CREATED ON: Jan 23, 2010
// CREATED BY: Allen Gregory
// MODIFIED ON:
// MODIFIED BY:
// ABSTRACT:
//	 This file allows us to declare the hardware for the robot tubeIn one place
//
//====================================================================
package discobot;

import DiscoLift.*;
import DriveControllers.*;
import Sensors.*;
import Utils.PIDTuner;
import edu.wpi.first.wpilibj.*;
//import edu.wpi.first.addons.*;

/**
 * @purpose puts all of the hardware declarations into one
place.  In addition, it makes them available to
both autonomous and teleop.
 * @author Allen Gregory, pavchag and Nelson Chen
 */
public class HW {

//------------------------------------
//      Public Constants
//------------------------------------
    public static double[][] PIDConstants;
    public static double[] AutonConstants;
    public static double k_MaxVoltage = 12.4;
    public static double k_LEDRate = 0.5;
//------------------------------------
//       Module Locations
//------------------------------------
    /** -------------------------------------------------------
    The slot where the default analog Module is located
    ------------------------------------------------------- */
    public static int kAnalogModuleSlot = 0;
    /** -------------------------------------------------------
    The slot where the second analog Module is located
    ------------------------------------------------------- */
    public static int kSecondAnalogModuleSlot = 0;
    /** -------------------------------------------------------
    The slot where the default Digital Module is located
    ------------------------------------------------------- */
    public static int kDigitalModuleSlot = 4;
    /** -------------------------------------------------------
    The slot where the second Digital Module is located
    ------------------------------------------------------- */
    public static int kSecondDigitalModuleSlot = 6;
    /** -------------------------------------------------------
    The slot where the default Solenoid Module is located
    ------------------------------------------------------- */
    public static int kSolenoidModuleSlot = 8;
//------------------------------------
//           Jaguar class
//------------------------------------

    /*public static final int frontLeftDMAddress = 2;
    public static final int frontRightDMAddress = 3;
    public static final int rearRightDMAddress = 4;
    public static final int rearLeftDMAddress = 5;*/
    public static final int encoderCodesPerRev = 128;
    public static final int FrontLeftEncoderTicks = 360;
    public static final int FrontRightEncoderTicks = 360;
    public static final int RearRightEncoderTicks = 360;
    public static final int RearLeftEncoderTicks = 360;
    public static double maxWheelVelocity = 200;
//------------------------------------
//         Drive motors
//------------------------------------
    public static Jaguar DMFrontLeft = new Jaguar(1);
    public static Jaguar DMFrontRight = new Jaguar(2);
    public static Jaguar DMRearRight = new Jaguar(3);
    public static Jaguar DMRearLeft = new Jaguar(4);
    public static DiscoDriveStandard drive =
            new DiscoDriveStandard(DMFrontLeft, DMFrontRight, DMRearRight, DMRearLeft);
    public static DiscoDriveConverter driveConverter =
            new DiscoDriveConverter(0.0, 0.0, 0.0, DiscoDriveConverter.Output.kSpeed);
//------------------------------------
//         DriverStations class
//------------------------------------
    /**
     *  Get an instance of the driverstation to use tubeIn our code
     */
    public static DriverStation driverStation =
            DriverStation.getInstance();
    // public static TrackerDashboard trackerDash =
    //        new TrackerDashboard();
//------------------------------------
//          Joystick classes
//------------------------------------
    /**
     *  Defines Left-hand Drive Joystick
     */
    public static Joystick driveStickLeft = new Joystick(1);
    /**
     *  Defines Right-hand Drive Joystick
     */
    public static Joystick driveStickRight = new Joystick(2);
    /**
     *  Lift Joystick
     */
    public static Joystick liftHandle = new Joystick(3);
//-------------------------------------
//    DigitalInput (Switch) classes
//-------------------------------------
    public static DigitalInput liftLimitInnerUp = new DigitalInput(kSecondDigitalModuleSlot, 1);
    public static DigitalInput liftLimitInnerDown = new DigitalInput(kSecondDigitalModuleSlot, 2);
    public static DigitalInput liftLimitMiddleUp = new DigitalInput(kSecondDigitalModuleSlot, 11);
    public static DigitalInput liftLimitMiddleDown = new DigitalInput(kSecondDigitalModuleSlot, 12);
    public static DigitalInput armSwitchUp = new DigitalInput(kSecondDigitalModuleSlot, 3);
    public static DigitalInput armSwitchDown = new DigitalInput(kSecondDigitalModuleSlot, 4);
//-------------------------------------
//          Digital classes
//-------------------------------------
    public static DiscoEncoder encoderFrontLeft =
            new DiscoEncoder(new DigitalInput(5), new DigitalInput(6), false, Encoder.EncodingType.k2X);
    public static Encoder bad1 = new Encoder(liftLimitMiddleUp, liftLimitMiddleDown, false, Encoder.EncodingType.k2X);
    public static DiscoEncoder encoderFrontRight =
            new DiscoEncoder(new DigitalInput(7), new DigitalInput(8), true, Encoder.EncodingType.k2X);
    public static Encoder bad2 = new Encoder(liftLimitMiddleUp, liftLimitMiddleDown, false, Encoder.EncodingType.k2X);
    public static DiscoEncoder encoderRearRight =
            new DiscoEncoder(new DigitalInput(1), new DigitalInput(2), true, Encoder.EncodingType.k2X);
    public static Encoder bad3 = new Encoder(liftLimitMiddleUp, liftLimitMiddleDown, false, Encoder.EncodingType.k2X);
    public static DigitalInput liftEncoder1 = new DigitalInput(9);
    public static DigitalInput liftEncoder2 = new DigitalInput(10);
    public static DiscoEncoder liftEncoder =
            new DiscoEncoder(liftEncoder1, liftEncoder2, false, Encoder.EncodingType.k1X);
    public static DiscoEncoder encoderRearLeft =
            new DiscoEncoder(new DigitalInput(3), new DigitalInput(4), true, Encoder.EncodingType.k2X);
    //public static Encoder bad4 = new Encoder(liftLimitMiddleUp, liftLimitMiddleDown, false, Encoder.EncodingType.k2X);

//-----------------------------------
//  DistanceControllers for sonar-less autonomous
//-----------------------------------

    public static DistanceController distanceControllerFrontLeft =
            new DistanceController(0.1, 0.0, 0.0, encoderFrontLeft);
    public static DistanceController distanceControllerFrontRight =
            new DistanceController(0.1, 0.0, 0.0, encoderFrontRight);
    public static DistanceController distanceControllerRearRight =
            new DistanceController(0.1, 0.0, 0.0, encoderRearRight);
    public static DistanceController distanceControllerRearLeft =
            new DistanceController(0.1, 0.0, 0.0, encoderRearLeft);
//------------------------------------
//      LIft, Arm, and collector
//------------------------------------
    public static Jaguar liftMotor = new Jaguar(5);
    public static Victor armMotor = new Victor(6);
    public static Victor collectorMotor = new Victor(7);

            //new DiscoEncoder(9, 10, false, Encoder.EncodingType.k1X);
    public static Arm arm = new Arm();
    public static LiftController lift = new LiftController();
//-------------------------------------
//             Gyro class
//-------------------------------------
    public static DiscoGyro gyro = new DiscoGyro(1);
    public static TurnController turnController = new TurnController(gyro);
//-------------------------------------
//         Accelerometer class
//-------------------------------------
    public static ADXL345_I2C accelerometer = new ADXL345_I2C(4, ADXL345_I2C.DataFormat_Range.k4G);
//-------------------------------------
//         Spike Relay class
//-------------------------------------
    public static Relay LEDminibot = new Relay(1);
    public static Relay LEDfeederSignal = new Relay(2);
//-------------------------------------
//         Servo class (for minibot deploymsne)
//-------------------------------------
    public static Servo minibotDeployer = new Servo(8);
//-------------------------------------
//          Ultrasonic class
//-------------------------------------
    public static MaxbotixSonar sonarLeft =
            new MaxbotixSonar(2);//, 11);
    public static MaxbotixSonar sonarFrontLeft =
            new MaxbotixSonar(4);
    public static MaxbotixSonar sonarFrontRight =
            new MaxbotixSonar(3);
    public static MaxbotixSonar sonarRight =
            new MaxbotixSonar(5);
    //public static SonarController sonarControllerFrontLeft =
      //      new SonarController(sonarFrontLeft, 0.05, 0.0, 0.035);
    public static SonarController sonarControllerFrontRight =
            new SonarController(sonarFrontRight, 0.05, 0.0, 0.035);
    public static SonarController sonarControllerLeft =
            new SonarController(sonarLeft, 0.05, 0.0, 0.035);
    //public static SonarController sonarControllerRight =
      //      new SonarController(sonarRight, 0.05, 0.0, 0.035);
    //-------------------------------------
    //       DriverStationLCD class
    //-------------------------------------
    /**
     *  The instance of the driverStationLCD that we will use
     */
    public static DriverStationLCD lcd =
            DriverStationLCD.getInstance();
}
