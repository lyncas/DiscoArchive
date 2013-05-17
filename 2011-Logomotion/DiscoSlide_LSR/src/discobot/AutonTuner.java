package discobot;

import Utils.DiscoUtils;
import Utils.FileIO;
import com.sun.squawk.util.StringTokenizer;
import discobot.HW;

/**
 * Class PIDTuner
 *
 * gets PIDConstants from a file, PIDTuning.csv
 * File can be of any length as long as it follows this format:
 *
 * kP,kI,kD,[doesn't matter after this; label is recommended]
 * 
 * example:
 * 0.13,0.0052,0.021,examplePIDController
 * 1.2,0.0,0.0,examplePIDController2
 * 10.5,0.0,5.323,examplePIDController3
 *
 *
 * @author Nelson Chen
 */
public class AutonTuner {

    public static double[] readFile() {
        try {
            String[] rawData = FileIO.readFromFile("AutonTuning.csv");
            double[] autonConstants = new double[rawData.length];
            for (int k = 0; k < autonConstants.length; k++) {
                StringTokenizer buff = new StringTokenizer(rawData[k], ",");
                autonConstants[k] = Double.parseDouble(buff.nextToken());
            }
            return autonConstants;
        } catch (Exception e) {
            DiscoUtils.debugPrintln("AutonTuning.csv not found, please FTP it to the cRIO!");
            return null;
        }
    }

    public static void setValues() {
        HW.AutonConstants = AutonTuner.readFile();
        DoubleTubeAutonomous.k_maxSpeed = HW.AutonConstants[0];
        DoubleTubeAutonomous.k_safetySpeed = HW.AutonConstants[1];
        DoubleTubeAutonomous.k_collectionSpeed = HW.AutonConstants[2];
        DoubleTubeAutonomous.k_spinXtoMid = HW.AutonConstants[3];
        DoubleTubeAutonomous.k_spinYtoMid = HW.AutonConstants[4];
        DoubleTubeAutonomous.k_spinXTube2 = HW.AutonConstants[5];
        DoubleTubeAutonomous.k_spinYTube2 = HW.AutonConstants[6];
        DoubleTubeAutonomous.k_liftSafetyHeight = HW.AutonConstants[7];
        DoubleTubeAutonomous.k_strafeRightYoffset = HW.AutonConstants[8];
        DoubleTubeAutonomous.k_frontDistanceBeforeSpin = HW.AutonConstants[9];
        DoubleTubeAutonomous.k_frontSafetyDistance = HW.AutonConstants[10];
        DoubleTubeAutonomous.k_modeTimeout = HW.AutonConstants[11];
        DiscoUtils.debugPrintln("Autonomous Constants Set");
    }
}
