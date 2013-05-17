/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package discobot;

/**
 *
 * @author JAG
 */
public class AutoScore {

    private static final double k_scoringDistance = 10.0;
    private static double k_maxSonarError = 2.0;

    private static double kHangDistance = 30;
    private static boolean enabled = false;


    /*this is old and very broken... I'll fix this eventually
     public static void sonarPositionClose() {
        enableSonarPositioning();
        HW.sonarControllerFrontLeft.setDistance(k_scoringDistance);
        //HW.sonarControllerFrontRight.setDistance(k_scoringDistance);
        HW.turnController.sonarAngleCorrect(HW.sonarFrontLeft.getRangeInches(), HW.sonarFrontRight.getRangeInches());
        double x = HW.sonarControllerLeft.getSpeed();
        double y = HW.sonarControllerFrontLeft.getSpeed();// + HW.sonarControllerFrontRight.getSpeed())
                // 2;
        HW.drive.HolonomicDrive(x, y, HW.turnController.getRotation());
    }

    public static void hangTube(){
        if (!isAutoScore()){
            enable();
        }
        HW.lift.setSetpoint(HW.lift.getPosition() - kHangDistance);
    }

    public static boolean inPosition() {
        if (Math.abs(HW.sonarControllerLeft.getError()) < k_maxSonarError
                && Math.abs(HW.sonarControllerFrontLeft.getError()) < k_maxSonarError) {
                //&& Math.abs(HW.sonarControllerFrontRight.getError()) < k_maxSonarError) {
            return true;
        } else {
            return false;
        }
    }

    public static void enableSonarPositioning() {
        HW.sonarControllerFrontLeft.enable();
        //HW.sonarControllerFrontRight.enable();
        HW.sonarControllerLeft.enable();
        HW.turnController.enable();
        HW.turnController.setSetpoint(180);
    }

    private static void enable(){
        enabled = true;
    }

    private static void disable(){
        enabled = false;
    }
    public static boolean isAutoScore(){
        return enabled;
    }*/
}
