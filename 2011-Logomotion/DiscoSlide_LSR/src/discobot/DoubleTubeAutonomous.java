package discobot;

import Utils.*;
import edu.wpi.first.wpilibj.Timer;

/**
 * @author Nelson
 *       
 */
public class DoubleTubeAutonomous {

    /**
     * Distance constants
     */
    public static final double k_frontDistanceToTube = 9.0;//TBD: distance to collected tube
    public static final double k_scoringDistance = 13.0;
    public static final double k_leftDistToLaneCircle = 35.0;
    public static final double k_leftDistToWallCircle = k_leftDistToLaneCircle + 51.0;//lane width = 51 inches
    public static final double k_distanceBetweenColumns = 30.0; //columns are 30" apart
    public static final double k_leftDistToLaneSquare = k_leftDistToLaneCircle + k_distanceBetweenColumns;
    public static final double k_leftDistToWallSquare = k_leftDistToWallCircle + k_distanceBetweenColumns;
    public static double leftDistToLane = k_leftDistToLaneCircle;
    public static double leftDistToWall = k_leftDistToWallCircle;
    /**
     * Maximum Error constants
     */
    public static double k_maxSonarError = 4.0;
    public static double k_maxHeadingError = 5.0;
    public static double k_maxLiftError = 11.0;
    /**
     * Variables that needs to be in PIDTuner for frequent testing
     */
    public static double k_maxSpeed = 1.0;
    public static double k_safetySpeed = 0.6;
    public static double k_collectionSpeed = 0.75;
    public static double k_spinXtoMid = 0.0;//x-velocity to maintain while spinning to midfield
    public static double k_spinYtoMid = 0.0;//y-velocity to maintain while spinning after picking up 2nd tube
    public static double k_spinXTube2 = 0.0;//x-velocity to maintain while spinning after picking up 2nd tube
    public static double k_spinYTube2 = 0.0;//y-velocity to maintain while spinning to midfield
    public static double k_liftSafetyHeight = HW.lift.kLiftTopCircle - 150;
    public static double k_strafeRightYoffset = 0.05;
    public static double k_frontDistanceBeforeSpin = 70.0;
    public static double k_frontSafetyDistance = 36.0;//forward clearance for raising lift
    public static double k_modeTimeout = 2.0;
    /**
     * Global variables changed within the program
     */
    public static double scoreHeight = HW.lift.kLiftTopCircle;
    public static double maxSpeed = k_maxSpeed;
    public static boolean tubeHung = false;
    public static boolean tube2Hung = false;
    private static double k_startDistance;
    private static double modeStartTime;
    /**
     * Scoring modes
     */
    //Some numbers deliberately skipped to accomodate addition of other cases
    public static final int k_startAutonMode = 0;
    public static final int k_approachGridMode = 1;
    public static final int k_pullTubeDownMode = 2;
    public static final int k_hangTubeMode = 3;
    public static final int k_backToMidMode = 5;
    public static final int k_turnToTubeMode = 6;
    public static final int k_moveToTubeMode = 7;
    public static final int k_collectTubeMode = 8;
    public static final int k_bringTubeBackMode = 9;
    public static final int k_turnToGridMode = 10;
    public static final int k_finishAutonMode = 12;
    public static int currentMode = k_approachGridMode;

    public static void init() {
        DataLogger.dataLogger.init();
        initEncoders();
        initPIDs();
        PIDTuner.setPIDs();
        AutonTuner.setValues();
        HW.arm.updateArmSpeed();
        k_startDistance = HW.sonarFrontRight.getRangeInches();
        DiscoUtils.debugPrintln("AUTONOMOUS INIT COMPLETE");
        initDataLogger();
    }

    public static void initDataLogger() {
        String[] header = {"L Sonar", "FR Sonar", "Lift"};
        DataLogger.dataLogger.setHeader(header);
        DataLogger.dataLogger.setTimeOffset(Timer.getFPGATimestamp());
        DataLogger.dataLogger.enable();
    }

    public static void continuous() {
        double[] data = {HW.sonarLeft.getRangeInches(), HW.sonarFrontRight.getRangeInches(),
            HW.lift.getPosition()};
        DataLogger.dataLogger.setEntryValue(data);
        //Disabled.continuous();
    }

    public static void periodic() {
        switch (currentMode) {
            case k_startAutonMode://clear the minibot platform on left side
                HW.lift.setSetpoint(scoreHeight);
                sonarPositionFar(1.0);
                if (HW.sonarLeft.getRangeInches() > 20.0) {
                    currentMode = k_approachGridMode;
                }
                break;
            case k_approachGridMode:
                if (HW.sonarFrontRight.getRangeInches() <= k_frontSafetyDistance
                        && HW.lift.getPosition() < scoreHeight - k_maxLiftError) {
                    HW.drive.HolonomicDrive(HW.sonarControllerLeft.getSpeed(), 0.0,
                            HW.turnController.getRotation());
                } else {
                    if (HW.sonarLeft.getRangeInches() <= 50.0
                            && HW.sonarControllerLeft.getSetpoint() != leftDistToLane) {
                        HW.sonarControllerLeft.setDistance(leftDistToLane);
                    }
                    sonarPosition();
                }
                if (inPosition() && HW.lift.pidGet() > scoreHeight - k_maxLiftError) {
                    HW.drive.HolonomicDrive(0.0, 0.0, 0.0);
                    currentMode = k_pullTubeDownMode;
                }
                break;
            case k_pullTubeDownMode:
                HW.lift.setSetpoint(scoreHeight - 65);
                if (HW.lift.pidGet() < (scoreHeight - 55)) {
                    currentMode = k_hangTubeMode;
                }
                break;
            case k_hangTubeMode:
                hangTube();
                if (tubeHung) {
                    setFrontDistance(k_startDistance);
                    //k_maxSonarError = 5.0;
                    tubeHung = false;
                    scoreHeight = HW.lift.kLiftTopSquare;
                    currentMode = k_backToMidMode;
                }
                break;
            case k_backToMidMode:
                sonarPositionX(-1.0);
                //if lane divider is passed
                if (HW.sonarLeft.getRangeInches() >= 50.0
                        && HW.sonarControllerLeft.getSetpoint() != leftDistToWall) {
                    leftDistToLane = k_leftDistToLaneSquare;
                    leftDistToWall = k_leftDistToWallSquare;
                    HW.sonarControllerLeft.setDistance(leftDistToWall);
                }
                //if far enough from grid that lowering the lift is safe
                if (HW.sonarFrontRight.getRangeInches() >= k_frontSafetyDistance
                        && HW.lift.getSetpoint() != HW.lift.kLiftD) {
                    HW.lift.setSetpoint(HW.lift.kLiftD);
                }
                if (HW.sonarFrontRight.getRangeInches() >= k_frontDistanceBeforeSpin) {
                    HW.turnController.setSetpoint(270.0);
                    if (!tube2Hung) {
                        currentMode = k_turnToTubeMode;
                    } else {
                        currentMode = k_finishAutonMode;
                    }
                    HW.sonarControllerLeft.setDistance(k_startDistance);
                }
                if (tube2Hung && HW.lift.isLiftDown() && HW.sonarFrontRight.getRangeInches() > 70.0) {
                    currentMode = k_finishAutonMode;
                }
                break;
            case k_turnToTubeMode:
                if (Math.abs(HW.turnController.getAngle() - 270.0)
                        < k_maxHeadingError) {
                    HW.sonarControllerFrontRight.setDistance(k_frontDistanceToTube);
                    HW.sonarControllerLeft.setDistance(k_startDistance);
                    currentMode = k_moveToTubeMode;
                } else {
                    double out[] = rotateVector(k_spinXtoMid, k_spinYtoMid, -1 * HW.gyro.getAngle());
                    HW.drive.HolonomicDrive(out[0], out[1], HW.turnController.getRotation());
                }
                break;
            case k_moveToTubeMode:
                sonarPositionX(k_strafeRightYoffset);
                if (HW.sonarLeft.getRangeInches() > (k_startDistance - k_maxSonarError)
                        && HW.lift.isLiftDown()) {
                    k_maxSonarError = 3.0;
                    setFrontDistance(k_frontDistanceToTube);
                    currentMode = k_collectTubeMode;
                    modeStartTime = Timer.getFPGATimestamp();
                    maxSpeed = k_collectionSpeed;
                }
                break;
            case k_collectTubeMode:
                HW.arm.collect();
                sonarPosition();
                if (HW.sonarFrontRight.getRangeInches() < k_frontDistanceToTube + k_maxSonarError
                        || (Timer.getFPGATimestamp() - modeStartTime) > k_modeTimeout) {
                    currentMode = k_bringTubeBackMode;
                    maxSpeed = k_maxSpeed;
                }
                break;
            case k_bringTubeBackMode:
                HW.arm.tubeIn(HW.arm.k_collectorInSpeed - 0.2);
                //sonarPosition();
                HW.drive.HolonomicDrive(k_spinXTube2, k_spinYTube2, HW.turnController.getRotation());
                if (HW.arm.isArmUp()) {
                    HW.turnController.setSetpoint(180.0);
                    currentMode = k_turnToGridMode;
                    k_maxSonarError = 3.0;
                }
                break;
            case k_turnToGridMode:
                HW.arm.tubeIn(HW.arm.k_collectorInSpeed - 0.2);
                if (Math.abs(HW.turnController.getAngle() - 180.0)
                        < k_maxHeadingError) {
                    HW.sonarControllerFrontRight.setDistance(k_scoringDistance);
                    HW.sonarControllerLeft.setDistance(leftDistToWall);
                    currentMode = k_startAutonMode;
                } else {
                    double out[] = rotateVector(k_spinXtoMid, k_spinYtoMid, -1 * HW.gyro.getAngle());
                    HW.drive.HolonomicDrive(out[0], out[1], HW.turnController.getRotation());
                }
                break;
            case k_finishAutonMode:
                Disabled.disablePIDs();
                stopAllMotors();
                break;
        }
        if (currentMode != k_collectTubeMode) {
            HW.arm.up();
        }
    }

    public static void initPIDs() {
        HW.turnController.reset(180.0);//also enables
        HW.turnController.setOutputRange(-0.75, 0.75);
        HW.turnController.setSetpoint(180.0);
        HW.sonarControllerLeft.setDistance(leftDistToWall);
        HW.sonarControllerFrontRight.setDistance(k_scoringDistance);
        limitSonarControllers(maxSpeed);
        enableSonarPositioning();
        HW.lift.enablePIDControl();
        HW.lift.setOutputRange(-0.2, 1.0);
        //HW.lift.setSetpoint(HW.lift.kLiftD);
        /*DiscoUtils.debugPrintln("PIDs and Autonomous Constants Set");
        DiscoUtils.debugPrintln("L  PIDs: P=" + HW.sonarControllerLeft.getP() +
        "\tD=" + HW.sonarControllerLeft.getD());
        //DiscoUtils.debugPrintln("FR PIDs: P=" + HW.sonarControllerFrontRight.getP() +
        "\tD=" + HW.sonarControllerFrontRight.getD());
        DiscoUtils.debugPrintln("lift PIDs: P=" + HW.lift.getP() + "\tD=" + HW.lift.getD());
        DiscoUtils.debugPrintln("turnC PIDs: P=" + HW.turnController.getP() +
        "\tD=" + HW.turnController.getD());*/
    }

    public static void initEncoders() {
        /*HW.encoderFrontLeft.setCodesPerRev(HW.FrontLeftEncoderTicks);
        HW.encoderFrontRight.setCodesPerRev(HW.FrontRightEncoderTicks);
        HW.encoderRearRight.setCodesPerRev(HW.RearRightEncoderTicks);
        HW.encoderRearLeft.setCodesPerRev(HW.RearLeftEncoderTicks);
        HW.encoderFrontLeft.init();
        HW.encoderFrontRight.init();
        HW.encoderRearRight.init();
        HW.encoderRearLeft.init();*/
        HW.liftEncoder.init();
    }

    public static void sonarPosition() {
        if (HW.lift.getPosition() > k_liftSafetyHeight) {
            limitSonarControllers(k_safetySpeed);
        } else {
            limitSonarControllers(maxSpeed);
        }
        //OR limitSonarControllers(HW.lift.getPosition());
        HW.drive.HolonomicDrive(HW.sonarControllerLeft.getSpeed(),
                HW.sonarControllerFrontRight.getSpeed(),
                HW.turnController.getRotation());
        //DiscoUtils.debugPrintln("X: " + x);
        //DiscoUtils.debugPrintln("Y: " + y);
    }

    /**
     * Sonar positioning based off ONLY Left sonar
     * @param y y-speed; may also be the offset
     */
    public static void sonarPositionX(double y) {
        if (HW.lift.getPosition() > k_liftSafetyHeight) {
            limitSonarControllers(k_safetySpeed);
        } else {
            limitSonarControllers(maxSpeed);
        }
        HW.drive.HolonomicDrive(HW.sonarControllerLeft.getSpeed(), y,
                HW.turnController.getRotation());
        //DiscoUtils.debugPrintln("X: " + x);
        //DiscoUtils.debugPrintln("Y: " + y);
    }

    public static boolean inPosition() {
        if (Math.abs(HW.sonarLeft.getRangeInches() - HW.sonarControllerLeft.getSetpoint()) < k_maxSonarError
                && Math.abs(HW.sonarFrontRight.getRangeInches() - HW.sonarControllerFrontRight.getSetpoint()) < k_maxSonarError) {
            return true;
        } else {
            return false;
        }
    }

    public static void hangTube() {
        HW.lift.setSetpoint(scoreHeight - 80);
        if (HW.lift.pidGet() < (scoreHeight - 70)) {
            HW.arm.stopCollector();
            HW.lift.setSetpoint(HW.lift.pidGet());
            tubeHung = true;
        } else {
            HW.arm.tubeOut();
        }
    }

    protected static double[] rotateVector(double x, double y, double angle) {
        double cosA = Math.cos(angle * (3.14159 / 180.0));
        double sinA = Math.sin(angle * (3.14159 / 180.0));
        double out[] = new double[2];
        out[0] = x * cosA - y * sinA;
        out[1] = x * sinA + y * cosA;
        return out;
    }

    public static void limitSonarControllers(double limit) {
        HW.sonarControllerFrontRight.setOutputRange(-limit, limit);
        HW.sonarControllerLeft.setOutputRange(-limit, limit);
    }

    public static void stopAllMotors() {
        HW.liftMotor.set(0.0);
        HW.DMFrontLeft.set(0.0);
        HW.DMFrontRight.set(0.0);
        HW.DMRearRight.set(0.0);
        HW.DMRearLeft.set(0.0);
    }

    public static void disableSonarPositioning() {
        HW.sonarControllerFrontRight.disable();
        HW.sonarControllerLeft.disable();
        HW.drive.HolonomicDrive(0.0, 0.0, 0.0);
    }

    public static void enableSonarPositioning() {
        HW.sonarControllerFrontRight.enable();
        //HW.sonarControllerFrontRight.enable();
        HW.sonarControllerLeft.enable();
        //HW.turnController.enable();
        //HW.turnController.setSetpoint(180);
    }

    public static void setFrontDistance(double dist) {
        HW.sonarControllerFrontRight.setDistance(dist);
        //HW.sonarControllerFrontRight.setDistance(dist);
        enableSonarPositioning();
    }

    public static void setLeftDistance(double dist) {
        HW.sonarControllerLeft.setDistance(dist);
        //HW.sonarControllerFrontRight.setDistance(dist);
        enableSonarPositioning();
    }

    /**
     * @deprecated sonarPositioningFar based on a hard-coded offset
     * to correct mechanical imbalance in drive train
     */
    public static void sonarPositionFar(double offset) {
        if (HW.lift.getPosition() > HW.lift.kLiftTopCircle - 100) {
            limitSonarControllers(0.5);
        } else {
            limitSonarControllers(0.75);
        }
        //limitSonarControllers(HW.lift.getPosition());
        double x;
        if (HW.sonarFrontRight.getRangeInches() < 65) {
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

    /**
     * @deprecated  sonarPositionClose
     */
    public static void sonarPositionClose() {
        double x = HW.sonarControllerLeft.getSpeed();
        double y = HW.sonarControllerFrontRight.getSpeed();
        HW.drive.HolonomicDrive(x, y, HW.turnController.getRotation());
        //DiscoUtils.debugPrintln("X: " + x);
        //DiscoUtils.debugPrintln("Y: " + y);
    }
}
