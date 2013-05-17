package Utils;

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
public class PIDTuner {

    private static double[][] PIDkonstants;

    /**
     * returns PID constants read from a file (PIDTuning.csv)
     * @return double matrix of PID constants in order according to file
     */
    public static double[][] getPIDConstants() {
        return PIDkonstants;
    }

    public static double[] getPIDConstants(int row) {
        return PIDkonstants[row];
    }

    public static double[][] readFile() {
        try {
            String[] rawData = FileIO.readFromFile("PIDTuning.csv");
            PIDkonstants = new double[rawData.length][3];
            for (int r = 0; r < PIDkonstants.length; r++) {
                StringTokenizer buff = new StringTokenizer(rawData[r], ",");
                for (int c = 0; c < 3; c++) {
                    PIDkonstants[r][c] = Double.parseDouble(buff.nextToken());
                }
            }
            //PIDkonstants matrix should now be initialized with values from the file
            return PIDkonstants;
        } catch (Exception e) {
            DiscoUtils.debugPrintln("PIDTuning.csv not found, please FTP it to the cRIO!");
            return new double[100][4];
        }
    }

    public static void setPIDs() {
        HW.PIDConstants = PIDTuner.readFile();
        HW.sonarControllerLeft.setPID(
                HW.PIDConstants[0][0],
                HW.PIDConstants[0][1],
                HW.PIDConstants[0][2]);
        HW.sonarControllerFrontRight.setPID(
                HW.PIDConstants[4][0],
                HW.PIDConstants[4][1],
                HW.PIDConstants[4][2]);
        HW.lift.setPID(
                HW.PIDConstants[2][0],
                HW.PIDConstants[2][1],
                HW.PIDConstants[2][2],
                HW.PIDConstants[3][0],
                HW.PIDConstants[3][1],
                HW.PIDConstants[3][2]);
        HW.turnController.setPID(
                HW.PIDConstants[4][0],
                HW.PIDConstants[4][1],
                HW.PIDConstants[4][2]);
        DiscoUtils.debugPrintln("PID Values Set");
    }
}
