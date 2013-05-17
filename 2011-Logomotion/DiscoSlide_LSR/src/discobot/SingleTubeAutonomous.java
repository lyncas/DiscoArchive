package discobot;

//import DriveControllers.SonarController;
import Utils.*;
import edu.wpi.first.wpilibj.Timer;

/**
 * @author Nelson
 * TODO: fix lift height variable
 *       
 */
public class SingleTubeAutonomous {

    /**
     * Distance constants; need to be changed to reflect actual distances
     */
    private static final double k_scoringDistance = 13.0;
    private static final double k_approachDistance = 50.0;
    private static final double k_leftDistance = 35.0;
    public static double k_maxSonarError = 5.0;
    private static final double k_maxLiftError = 15.0;
    private static final double k_scoreHeight = HW.lift.kLiftTopCircle;
    /**
     * Scoring modes
     */
    public static final int k_approachGridMode = 0;
    public static final int k_liftRaisingMode = 1;
    public static final int k_creepToGridMode = 2;
    public static final int k_hangTubeP1Mode = 3;
    public static final int k_hangTubeP2Mode = 4;
    public static final int k_takeItBackShortMode = 5;
    public static final int k_liftDropMode = 6;
    public static final int k_takeItBackLongMode = 7;
    public static final int k_reverseMode = 9;
    public static final int k_finishAutonMode = 10;
    public static int currentMode = k_approachGridMode;
    public static boolean tubeHung = false;
    private static double modeStartTime = 0.0;
    private static double k_startDistance;

    private static double k_liftHeight = k_scoreHeight;

    public static void init() {
        //currentMode = k_approachGridMode;
        initEncoders();
        initPIDs();
        HW.arm.updateArmSpeed();
        DiscoUtils.debugPrintln("AUTONOMOUS INIT COMPLETE");
        k_startDistance = 100.0;//HW.sonarFrontRight.getRangeInches();
        modeStartTime = Timer.getFPGATimestamp();
    }

    public static void periodic() {
        switch (currentMode) {
            case k_approachGridMode:
                sonarPositionFar(0.1);
                if (inPosition() && (Timer.getFPGATimestamp() - modeStartTime > 1.0)) {
                    //disableSonarPositioning();
                    HW.drive.HolonomicDrive(0.0, 0.0, 0.0);
                    currentMode = k_liftRaisingMode;
                    //Timer.delay(1.0);
                    k_maxSonarError = 2.5;
                }
                //DiscoUtils.debugPrintln("Sonar pos mode");
                break;
            case k_liftRaisingMode:
                HW.lift.setSetpoint(k_scoreHeight);
                if (Math.abs(HW.lift.pidGet()) > k_scoreHeight - k_maxLiftError) {
                    //enableSonarPositioning();
                    setDistanceFromGrid(k_scoringDistance);
                    HW.sonarControllerLeft.setOutputRange(-0.5, 0.5);
                    HW.sonarControllerFrontRight.setOutputRange(-0.5, 0.5);
                    //HW.sonarControllerFrontRight.setOutputRange(-0.4, 0.4);
                    currentMode = k_creepToGridMode;
                    modeStartTime = Timer.getFPGATimestamp();
                    //Timer.delay(1.0);
                }
                //DiscoUtils.debugPrintln("Lift raise");
                break;
            case k_creepToGridMode:
                //setDistanceFromGrid(k_scoringDistance);
                sonarPositionClose();
                if (inPosition() && (Timer.getFPGATimestamp() - modeStartTime > 0.5)) {
                    HW.drive.HolonomicDrive(0.0, 0.0, 0.0);
                    currentMode = k_hangTubeP1Mode;
                    //Timer.delay(1.0);
                }
                //DiscoUtils.debugPrintln("creeping to grid");
                break;
            case k_hangTubeP1Mode:
                disableSonarPositioning();
                HW.lift.setSetpoint(k_scoreHeight - 100);
                if (HW.lift.pidGet() < (k_scoreHeight - 90)) {
                    currentMode = k_hangTubeP2Mode;
                }
                //DiscoUtils.debugPrintln("Tube hang P1 mode");
                break;
            case k_hangTubeP2Mode:
                disableSonarPositioning();
                hangTube2();
                if (tubeHung) {
                    setDistanceFromGrid(40.0);
                    currentMode = k_takeItBackShortMode;
                    Timer.delay(0.3);
                    k_maxSonarError = 5.00;
                    modeStartTime = Timer.getFPGATimestamp();
                }
                //DiscoUtils.debugPrintln("Tube hang P2 mode");
                break;
            case k_takeItBackShortMode:
                setDistanceFromGrid(50.0);
                sonarPositionFar(-0.05);
                if (inPosition() &&  (Timer.getFPGATimestamp() - modeStartTime > 1.0)) {
                    HW.drive.HolonomicDrive(0.0, 0.0, 0.0);
                    currentMode = k_liftDropMode;
                    //Timer.delay(1.0);
                }
                //DiscoUtils.debugPrintln("Take it back now y'all");
                break;
            case k_liftDropMode:
                //HW.lift.setSetpoint(HW.lift.kLiftD);
                HW.lift.downToSwitch();
                if (HW.lift.isLiftDown()) {
                    HW.lift.setSetpoint(HW.lift.kLiftD);
                    setDistanceFromGrid(k_startDistance);
                    currentMode = k_finishAutonMode;
                    HW.sonarControllerFrontRight.setOutputRange(-0.75, 0.75);
                    //Timer.delay(1.0);
                    modeStartTime = Timer.getFPGATimestamp();
                }
                //DiscoUtils.debugPrintln("Lift down mode");
                break;
            case k_takeItBackLongMode:
                setDistanceFromGrid(k_startDistance);
                sonarPositionFar(-0.1);
                if (inPosition() &&  (Timer.getFPGATimestamp() - modeStartTime > 0.5)) {
                    HW.drive.HolonomicDrive(0.0, 0.0, 0.0);
                    currentMode = k_reverseMode;
                    //Timer.delay(1.0);
                }
                ;break;
            case k_reverseMode:
                //HW.turnController.turnToOrientation(180.0);
                HW.turnController.setSetpoint(0.0);
                HW.drive.HolonomicDrive(0.0, 0.0, HW.turnController.getRotation());
                if (HW.turnController.getError() < 5) {
                    currentMode = k_finishAutonMode;
                    //Timer.delay(1.0);
                }
                //DiscoUtils.debugPrintln("Reverse! Reverse");
                break;
            case k_finishAutonMode:
                Disabled.disablePIDs();
                stopAllMotors();
                break;
        }
        HW.arm.up();
    }

    public static void stopAllMotors() {
        HW.liftMotor.set(0.0);
        HW.DMFrontLeft.set(0.0);
        HW.DMFrontRight.set(0.0);
        HW.DMRearRight.set(0.0);
        HW.DMRearLeft.set(0.0);
    }

    public static void continuous() {
        //Disabled.continuous();
    }

    public static void disableSonarPositioning() {
        HW.sonarControllerFrontRight.disable();
        //HW.sonarControllerFrontRight.disable();
        HW.sonarControllerLeft.disable();
    }

    public static void enableSonarPositioning() {
        HW.sonarControllerFrontRight.enable();
        //HW.sonarControllerFrontRight.enable();
        HW.sonarControllerLeft.enable();
        HW.turnController.enable();
        HW.turnController.setSetpoint(180);
    }

    public static void setDistanceFromGrid(double dist) {
        HW.sonarControllerFrontRight.setDistance(dist);
        //HW.sonarControllerFrontRight.setDistance(dist);
        enableSonarPositioning();
    }

    public static void sonarPositionFar(double offset) {
        double x;
        if (HW.sonarFrontRight.getRangeInches() < 65) {
            HW.sonarControllerLeft.setOutputRange(-04, 0.4);
            HW.sonarControllerFrontRight.setOutputRange(-0.4, 0.4);
            x = HW.sonarControllerLeft.getSpeed();
        } else {
            x = offset; //trying to correct drive train deficiency
        }
        double y = HW.sonarControllerFrontRight.getSpeed();
                //+ HW.sonarControllerFrontRight.getSpeed())
                /// 2;
        HW.drive.HolonomicDrive(x, y, HW.turnController.getRotation());
        //DiscoUtils.debugPrintln("X: " + x);
        //DiscoUtils.debugPrintln("Y: " + y);
    }

    public static void sonarPositionClose() {
        /*HW.turnController.setAngle(
        MathUtils.atan(HW.sonarFrontLeft.getRangeInches() - HW.sonarFrontRight.getRangeInches() / 21.625));
        HW.turnController.setSetpoint(180.0);*/
        double x = HW.sonarControllerLeft.getSpeed();
        double y = HW.sonarControllerFrontRight.getSpeed();
                //+ HW.sonarControllerFrontRight.getSpeed())
                /// 2;
        HW.drive.HolonomicDrive(x, y, HW.turnController.getRotation());
        //DiscoUtils.debugPrintln("X: " + x);
        //DiscoUtils.debugPrintln("Y: " + y);
    }

    public static boolean inPosition() {
        if (Math.abs(HW.sonarControllerLeft.getError()) < k_maxSonarError
                && Math.abs(HW.sonarControllerFrontRight.getError()) < k_maxSonarError) {
                //&& Math.abs(HW.sonarControllerFrontRight.getError()) < k_maxSonarError) {
            return true;
        } else {
            return false;
        }
    }

    public static void hangTube1() {
    }

    public static void hangTube2() {
        HW.lift.setSetpoint(k_scoreHeight - 110);
        if (HW.lift.pidGet() < (k_scoreHeight - 100)) {
            HW.arm.stopCollector();
            HW.lift.setSetpoint(HW.lift.pidGet());
            tubeHung = true;
        } else {
            HW.arm.tubeOut();
        }
    }

    public static void initPIDs() {
        HW.turnController.setPID(0.02, 0.0, 0.05);
        HW.turnController.reset(180.0);//also enables
        HW.turnController.setOutputRange(-0.5, 0.5);
        HW.turnController.setSetpoint(180.0);
        HW.sonarControllerLeft.setPID(0.05, 0.0, 0.035);
        HW.sonarControllerLeft.setDistance(k_leftDistance);
        HW.sonarControllerLeft.setOutputRange(-0.75, 0.75);
        HW.sonarControllerLeft.enable();
        HW.sonarControllerFrontRight.setPID(0.05, 0.0, 0.035);
        HW.sonarControllerFrontRight.setDistance(k_approachDistance);
        HW.sonarControllerFrontRight.setOutputRange(-0.75, 0.75);
        HW.sonarControllerFrontRight.enable();
        //HW.sonarControllerFrontRight.setDistance(k_approachDistance);
        //HW.sonarControllerFrontRight.setOutputRange(-0.5, 0.5);
        //HW.sonarControllerFrontRight.enable();
        HW.lift.enablePIDControl();
        HW.lift.setOutputRange(-0.2, 0.75);
        //PIDTuner.setPIDs();
        HW.lift.setSetpoint(HW.lift.kLiftD);
        /*DiscoUtils.debugPrintln("PIDS ENABLED");
        DiscoUtils.debugPrintln("L  PIDs: P=" + HW.sonarControllerLeft.getP() + "\tD=" + HW.sonarControllerLeft.getD());
        DiscoUtils.debugPrintln("FL PIDs: P=" + HW.sonarControllerFrontRight.getP() + "\tD=" + HW.sonarControllerFrontRight.getD());
        //DiscoUtils.debugPrintln("FR PIDs: P=" + HW.sonarControllerFrontRight.getP() + "\tD=" + HW.sonarControllerFrontRight.getD());
        DiscoUtils.debugPrintln("lift PIDs: P=" + HW.lift.getP() + "\tD=" + HW.lift.getD());
        DiscoUtils.debugPrintln("turnC PIDs: P=" + HW.turnController.getP() + "\tD=" + HW.turnController.getD());*/
    }

    public static void initEncoders() {
        HW.encoderFrontLeft.setCodesPerRev(HW.FrontLeftEncoderTicks);
        HW.encoderFrontRight.setCodesPerRev(HW.FrontRightEncoderTicks);
        HW.encoderRearRight.setCodesPerRev(HW.RearRightEncoderTicks);
        HW.encoderRearLeft.setCodesPerRev(HW.RearLeftEncoderTicks);
        HW.encoderFrontLeft.init();
        HW.encoderFrontRight.init();
        HW.encoderRearRight.init();
        HW.encoderRearLeft.init();
        HW.liftEncoder.init();
    }
}
