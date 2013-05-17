package discobot;

import Utils.*;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Nelson Chen
 */
public class Chacha {

    static final double k_armDownSpeed = 1.0;
    static final double k_armUpSpeed = -0.3;
    static final double k_collectorInSpeed = -0.5;
    static final double k_collectorOutSpeed = 0.3;
    static double currentX = 0.0;
    static double currentY = 0.0;
    static boolean[] leftButtons = new boolean[12];
    static boolean[] rightButtons = new boolean[12];
    static boolean[] liftButtons = new boolean[12];
    static double rotation;
    static final double k_driveRotationThreshold = 0.5;
    static final double k_gyroRotationThreshold = 1.0;

    private static void slideLeft() {
        //DiscoUtils.debugPrintln("Slide to the left!");
        HW.drive.HolonomicDrive(-0.7, 0.0, 0.0);
        Timer.delay(0.75);
        HW.drive.HolonomicDrive(0.0, 0.0, 0.0);
    }

    private static void slideRight() {
        //DiscoUtils.debugPrintln("Slide to the right!");
        HW.drive.HolonomicDrive(0.7, 0.0, 0.0);
        Timer.delay(0.75);
        HW.drive.HolonomicDrive(0.0, 0.0, 0.0);
    }

    private static void takeItBack() {
        //DiscoUtils.debugPrintln("Take it back now y'all");
        HW.drive.HolonomicDrive(0.0, 0.7, 0.0);
        Timer.delay(0.75);
        HW.drive.HolonomicDrive(0.0, 0.0, 0.0);
    }

    private static void hop() {
        //DiscoUtils.debugPrintln("one hop this time");
        HW.liftMotor.set(0.5);
        Timer.delay(0.5);
        HW.liftMotor.set(-0.4);
        Timer.delay(0.3);
        HW.liftMotor.set(0.3);
        Timer.delay(0.2);
        HW.liftMotor.set(0.0);
    }

    private static void clap() {
        //DiscoUtils.debugPrintln("clap");
        HW.armMotor.set(k_armDownSpeed);
        Timer.delay(0.2);
        HW.armMotor.set(k_armUpSpeed);
        Timer.delay(0.05);
        HW.armMotor.set(0.0);
    }

    private static void stomp() {
        //DiscoUtils.debugPrintln("stomp");
        HW.armMotor.set(k_armDownSpeed);
        Timer.delay(0.75);
        HW.armMotor.set(k_armUpSpeed);
        Timer.delay(0.1);
        HW.armMotor.set(0.0);
    }

    public static void auton() {
    }

    //static double[] encoder = new double[4];
    public static void periodic() {

        if (HW.driveStickLeft.getRawButton(4)) {
            slideLeft();
        } else if (HW.driveStickLeft.getRawButton(5)) {
            slideRight();
        } else if (HW.driveStickLeft.getRawButton(2)) {
            takeItBack();
        } else if (HW.liftHandle.getRawButton(2)) {
            clap();
        } else if (HW.liftHandle.getRawButton(3)) {
            hop();
        } else if (HW.liftHandle.getRawButton(7)) {
            stomp();
        } else if (HW.driveStickLeft.getRawButton(3)) {
            //chacha();
        } else {
            if (HW.driveStickRight.getRawButton(3)) {
                HW.turnController.turnToOrientation(0);
            } else if (HW.driveStickRight.getRawButton(2)) {
                HW.turnController.turnToOrientation(180);
            } else if (HW.driveStickRight.getRawButton(5)) {
                HW.turnController.turnToOrientation(90);
            } else if (HW.driveStickRight.getRawButton(4)) {
                HW.turnController.turnToOrientation(270);
            }

            if (HW.driveStickLeft.getRawButton(7)) {
                HW.turnController.reset(180);
            } else if (HW.driveStickLeft.getRawButton(11)) {
                HW.turnController.reset(0);
            }
            rotation = HW.turnController.getRotation();
            double out[] = rotateVector(HW.driveStickLeft.getX(), HW.driveStickLeft.getY(), -1 * HW.gyro.getAngle());
            HW.turnController.incrementSetpoint(HW.driveStickRight.getX());
            HW.drive.HolonomicDrive(out[0], out[1], rotation);
            HW.liftMotor.set(-HW.liftHandle.getY());

        }


        /*updateButtons();

        if (HW.sonarControllerLeft.isEnabled()) {
        currentX = HW.sonarControllerLeft.getSpeed();
        }

        //Drive Code
        if (leftButtons[5]) {
        HW.sonarControllerLeft.enable();
        } else if (leftButtons[2]) {
        HW.sonarControllerLeft.disable();
        }

        if (leftButtons[11]) {
        HW.turnController.reset(0);
        } else if (leftButtons[10]) {
        HW.turnController.reset(90);
        } else if (leftButtons[7]) {
        HW.turnController.reset(180);
        } else if (leftButtons[6]) {
        HW.turnController.reset(270);
        } else if (rightButtons[3]) {
        HW.turnController.turnToOrientation(0);
        } else if (rightButtons[5]) {
        HW.turnController.turnToOrientation(90);
        } else if (rightButtons[2]) {
        HW.turnController.turnToOrientation(180);
        } else if (rightButtons[4]) {
        HW.turnController.turnToOrientation(270);
        } else {
        HW.turnController.incrementSetpoint(HW.driveStickRight.getX());
        }


        //FC halo control
        rotation = HW.turnController.getRotation();
        double out[] = rotateVector(HW.driveStickLeft.getX(), HW.driveStickLeft.getY(), -1 * HW.gyro.getAngle());
        if (HW.sonarControllerLeft.isEnabled()) {
        HW.drive.HolonomicDrive(currentX, out[1], rotation);
        } else {
        HW.drive.HolonomicDrive(out[0], out[1], rotation);
        }

        if (liftButtons[6]) { //arm up
        HW.armMotor.set(k_armUpSpeed);
        if (HW.armMotor.get() == 0) {
        lastPressed[6] = currentTime;
        }
        } else if (liftButtons[7]) { // arm down
        HW.armMotor.set(k_armDownSpeed);
        if (HW.armMotor.get() == 0) {
        lastPressed[7] = currentTime;
        }
        } else {
        HW.armMotor.set(0.0);
        }

        HW.liftMotor.set(-HW.liftHandle.getY() * 0.75);

        if (liftButtons[2]) { //in
        HW.collectorMotor.set(k_collectorInSpeed);
        } else if (liftButtons[3]) { //out
        HW.collectorMotor.set(k_collectorOutSpeed);
        } else {
        HW.collectorMotor.set(0.0);
        }*/

        //delayLift6 = currentTime - lastPressed[3];
        //delayLift7 = currentTime - lastPressed[2];

        /*if (HW.armMotor.get() != 0 && delayLift6 > 0.5) {
        liftButtons[6] = false;
        }
        if (HW.armMotor.get() != 0 && delayLift7 > 0.5) {
        liftButtons[7] = false;
        }*/

        //HW.collector.periodic(liftHandleButtons);
        //HW.arm.periodic(liftHandleButtons);
        /*if (i > 50) {
        //DiscoUtils.debugPrintln("Gyro: " + HW.gyro.getAngle());
        //DiscoUtils.debugPrintln("L  sonar: " + HW.sonarLeft.getRangeCM());
        //DiscoUtils.debugPrintln("FL sonar: " + HW.sonarFrontLeft.getRangeInches());
        //DiscoUtils.debugPrintln("FR sonar: " + HW.sonarFrontRight.getRangeInches());
        //DiscoUtils.debugPrintln("Angle Setpoint: " + HW.turnController.getSetpoint());
        i = 0;
        } else {
        i++;
        }*/
        //verifyGyro(HW.gyro.getAngle());
        /*if (leftButtons[9]) {
        fieldCentricEnabled = true;
        DiscoUtils.debugPrintln("Field Centric Enabled");
        HW.lcd.println(DriverStationLCD.Line.kMain6, 0, "Field-Centric Enabled");
        HW.lcd.updateLCD();
        } else if (leftButtons[8]) {
        fieldCentricEnabled = false;
        DiscoUtils.debugPrintln("Field Centric Disabled");
        HW.lcd.println(DriverStationLCD.Line.kMain6, 0, "Field-Centric Disabled");
        HW.lcd.updateLCD();
        }*/
    }

    public static void continuous() {
    }

    /**
     * Rotate a vector in Cartesian space.
     */
    protected static double[] rotateVector(double x, double y, double angle) {
        double cosA = Math.cos(angle * (3.14159 / 180.0));
        double sinA = Math.sin(angle * (3.14159 / 180.0));
        double out[] = new double[2];
        out[0] = x * cosA - y * sinA;
        out[1] = x * sinA + y * cosA;
        return out;
    }

    private static void updateButtons() {
        for (int b = 1; b < 12; b++) {
            leftButtons[b] = HW.driveStickLeft.getRawButton(b);
            rightButtons[b] = HW.driveStickRight.getRawButton(b);
            liftButtons[b] = HW.liftHandle.getRawButton(b);
        }
    }
}
